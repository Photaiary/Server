package com.photaiary.Photaiary.friend.service;

import com.photaiary.Photaiary.friend.dto.FriendFollowRequestDto;
import com.photaiary.Photaiary.friend.entity.Friend;
import com.photaiary.Photaiary.friend.entity.FriendRepository;
import com.photaiary.Photaiary.user.dto.SignResponseDto;
import com.photaiary.Photaiary.user.entity.User;
import com.photaiary.Photaiary.user.entity.UserRepository;
import com.photaiary.Photaiary.user.security.JwtProvider;
import com.photaiary.Photaiary.user.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final SignService signService;
    /**
     * #Issue[Friend About addFollow Method]:
     * 1.해결
     * 2.service에서 controller로 반환할 때, 데이터를 옮기는 Dto 클래스를 사용해야 좋은 코드가 아닐까? (현재는 상태코드를 반환해주었음)
     * 3.해결
     * 4.Friend Entity에서 fromUser와 toUser의 fetch=LAZY 일때, JPA를 통한 조회 변경이 안됨.
     */
    @Transactional
    public HttpStatus makeFriend(FriendFollowRequestDto requestDto) throws Exception { //😊
        // 상대방&내 회원 정보 존재 확인 In DB
        String fromUserEmail = jwtProvider.getEmail("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxQG5hdmVyLmNvbSIsInJvbGVzIjpbeyJuYW1lIjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE2NzY4MTAxODIsImV4cCI6MTY3NjgxMTk4Mn0.6OUA3p_E6fCTlP7bJYjTHdKNBfZLzAMgHLSNAiU90hc");
        Optional<User> fromUser = userRepository.findByEmail(fromUserEmail);

        //내 회원 정보 여부 확인은 필요 없지 않을까?
        boolean cantContinue = requestDto.getToUserEmail().equals(fromUserEmail);
        if(cantContinue==false){

            String toUserEmail = requestDto.getToUserEmail();
            Optional<User> toUser = userRepository.findByEmail(toUserEmail);

            boolean isFriend;

            if (toUser.isPresent()) { // 상대가 회원인가?
                // YES
                Friend requestedFriend = Friend.builder()
                        .toUser(toUser.get())
                        .fromUser(fromUser.get())
                        .build();

                List<Friend> friends = friendRepository.findAll();
                Iterator<Friend> iterFriends = friends.iterator();

                while (iterFriends.hasNext()) {

                    Friend iterFriend = iterFriends.next();

                    isFriend = (
                            (toUserEmail.equals(iterFriend.getToUser().getEmail()))
                                    &&(fromUserEmail.equals(iterFriend.getFromUser().getEmail()))
                    );

                    if (isFriend) { // 이미 친구?
                        // YES
                        return HttpStatus.BAD_REQUEST;
                    }
                }

                //생성
                friendRepository.save(Friend.builder()
                        .toUser(toUser.get())
                        .fromUser(fromUser.get())
                        .build());

                return HttpStatus.OK;
            }
            // CASE: 존재하지 않는 회원일 경우 (UserNotFoundException)
            return HttpStatus.NOT_FOUND;
        }
        return null;
    }

    @Transactional
    public HttpStatus unFollow(FriendFollowRequestDto requestDto) {
        // 상대방&내 회원 정보 존재 확인 In DB (If not exist, then impossible!)
        User toUser = userRepository.findById(requestDto.getToUserId()).get();
        User fromUser = userRepository.findById(requestDto.getFromUserId()).get();

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
    public List<String> readFriends(Long id){ //Long 에서 String(토큰)으로 변경
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