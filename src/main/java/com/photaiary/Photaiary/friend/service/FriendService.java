package com.photaiary.Photaiary.friend.service;

import com.photaiary.Photaiary.friend.dto.FriendFollowRequestDto;
import com.photaiary.Photaiary.friend.entity.Friend;
import com.photaiary.Photaiary.friend.entity.FriendRepository;
import com.photaiary.Photaiary.user.entity.User;
import com.photaiary.Photaiary.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    /**
     * #Issue[Friend About addFollow Method]:
     * 1.해결
     * 2.service에서 controller로 반환할 때, 데이터를 옮기는 Dto 클래스를 사용해야 좋은 코드가 아닐까? (현재는 상태코드를 반환해주었음)
     * 3.해결
     * 4.Friend Entity에서 fromUser와 toUser의 fetch=LAZY 일때, JPA를 통한 조회 변경이 안됨.
     */
    @Transactional
    public HttpStatus makeFriend(FriendFollowRequestDto requestDto) {
        // 상대방&내 회원 정보 존재 확인 In DB
        User toUser = userRepository.findById(requestDto.getToUserId()).get();
        User fromUser = userRepository.findById(requestDto.getFromUserId()).get();

        if (toUser != null && fromUser != null) { // 상대와 내가 회원인가? (차후: 로그인 개발하고 token을 통한 구현으로 refactoring)

            // YES
            List<Friend> isFriend = friendRepository.findAll();
            Iterator<Friend> iterFriends = isFriend.iterator();

            while (iterFriends.hasNext()) {

                Friend friend = iterFriends.next();

                if (friend.equals(requestDto) ) { // 이미 친구?
                    // YES
                    return HttpStatus.BAD_REQUEST;
                }
            }

            //생성
            friendRepository.save(Friend.builder()
                    .toUser(toUser)  //반대로 빌딩했음. 수정완료
                    .fromUser(fromUser)
                    .build());

            return HttpStatus.OK;
        }
        // CASE: 존재하지 않는 회원일 경우 (UserNotFoundException)
        return HttpStatus.NOT_FOUND;
    }

    @Transactional
    public HttpStatus unFollow(FriendFollowRequestDto requestDto) {
        // 상대방&내 회원 정보 존재 확인 In DB (If not exist, then impossible!)
        User fromUser = userRepository.findById(requestDto.getFromUserId()).get();
        User toUser = userRepository.findById(requestDto.getToUserId()).get();
        boolean isFriend;

        if ((toUser != null) && (fromUser != null)) { // 상대와 내가 회원인가? (차후: 로그인 개발하고 token을 통한 구현으로 refactoring)
            // 친구가 없으면 절교도 할 수 없다
            // YES

            List<Friend> friends = friendRepository.findAll();
            Iterator<Friend> iterFriends = friends.iterator();


            //이 반복문 stream() 구조 탐색해도 괜찮을듯?
            //자료구조는 적용이 될까? 되면 어떤식으로 코드가 작성될까?
            while (iterFriends.hasNext()) {

                Friend iterFriend = iterFriends.next();

                FriendFollowRequestDto iterDto = FriendFollowRequestDto.builder()
                        .toUserId(iterFriend.getToUser().getUserIndex())
                        .fromUserId(iterFriend.getFromUser().getUserIndex())
                        .build();


                isFriend = (iterDto.getToUserId() == requestDto.getToUserId())
                        && (iterDto.getFromUserId() == requestDto.getFromUserId());

                if (isFriend) { // Already friend?
                    // YES(possible to deleting)
                    // ISSUE: 삭제 로직이 정상적으로 작동하지 않는다.
                    friendRepository.delete(iterFriend);

                    return HttpStatus.OK;
                }
            }

            // unfollow is impossible. cuz the relationship between fromUser and toUser is not friend.
            return HttpStatus.BAD_REQUEST;
        }
        // CASE: this user is not exist (UserNotFoundException)
        return HttpStatus.NOT_FOUND;
    }

    @Transactional
    public List<String> readFriends(Long id){
        // Check myUserId(fromUser) exist in useDB. (If not exist, then impossible!) (second develop -> using user token)
        List<String> myFriends= new ArrayList<>();
        User fromUser = userRepository.findById(id).get();

        //Is exist fromUSer information in user DataBase?
        if (fromUser != null){ //yes(In userDB: myUserId)

            List<Friend> friends = friendRepository.findAll();
            Iterator<Friend> iterFriends = friends.iterator();

            while (iterFriends.hasNext()) {
                Friend iterFriend = iterFriends.next();

                //Find a friend of the myUser.
                if (iterFriend.getFromUser().getUserIndex() == id) { //yes( unique case )
                    myFriends.add(iterFriend.getToUser().getNickname());
                }
            }
            return myFriends; // the friends of the myUser (LIST TYPE)
        }

        return null; // myUserId is null
    }
}
// 예외 핸들링 잊지 말고 리팩토링 하자.