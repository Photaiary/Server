package com.photaiary.Photaiary.friend.service;

import com.photaiary.Photaiary.friend.dto.FriendFollowRequestDto;
import com.photaiary.Photaiary.friend.entity.Friend;
import com.photaiary.Photaiary.friend.entity.FriendRepository;
import com.photaiary.Photaiary.friend.exception.custom.VoException;
import com.photaiary.Photaiary.user.entity.User;
import com.photaiary.Photaiary.user.entity.UserRepository;
import com.photaiary.Photaiary.user.security.JwtProvider;
import com.photaiary.Photaiary.user.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        String fromUserEmail = jwtProvider.getEmail(requestDto.getFromUserToken());
        Optional<User> fromUser = userRepository.findByEmail(fromUserEmail);

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

    @Transactional
    public HttpStatus unFollow(FriendFollowRequestDto requestDto) throws Exception{// 😊
        // 상대방&내 회원 정보 존재 확인 In DB (If not exist, then impossible!)
        String fromUserEmail = jwtProvider.getEmail(requestDto.getFromUserToken());
        Optional<User> fromUser = userRepository.findByEmail(fromUserEmail);

        String toUserEmail = requestDto.getToUserEmail();
        Optional<User> toUser = userRepository.findByEmail(toUserEmail);

        boolean isFriend;

        if (toUser.isPresent()) { // 상대가 회원인가? (차후: 로그인 개발하고 token을 통한 구현으로 refactoring)
            // 친구가 없으면 절교도 할 수 없다
            // YES

            List<Friend> friends = friendRepository.findAll();
            Iterator<Friend> iterFriends = friends.iterator();


            //이 반복문 stream() 구조 탐색해도 되려나?
            while (iterFriends.hasNext()) {

                Friend iterFriend = iterFriends.next();

                // 친구관계 확인 (리팩토링 0219 07:24)
                isFriend = ((toUserEmail.equals(iterFriend.getToUser().getEmail()))
                        &&(fromUserEmail.equals(iterFriend.getFromUser().getEmail())));


                if (isFriend) { // Already friend?
                    // YES!(possible to deleting)
                    friendRepository.delete(iterFriend);

                    return HttpStatus.OK;
                }
                //⚠️[ISSUE: O(N) -> 정보가 많을 수록 느려진다. 어떻게 할 것 인가?]
            }

            // unfollow is impossible. cuz the relationship between fromUser and toUser is not friend.
            return HttpStatus.BAD_REQUEST;
        }
        // CASE: this user is not exist (UserNotFoundException)
        return HttpStatus.NOT_FOUND;
    }

    @Transactional
    public List<String> readFriends(String token){ //Long 에서 String(토큰)으로 변경(리팩토링 0219 07:26) 😊
        // Check myUserId(fromUser) exist in useDB. (If not exist, then impossible!) (second develop -> using user token)
        List<String> myFriends= new ArrayList<>();
        String fromUserEmail = jwtProvider.getEmail(token);
        Optional<User> fromUser = userRepository.findByEmail(fromUserEmail);

        List<Friend> friends = friendRepository.findAll();
        Iterator<Friend> iterFriends = friends.iterator();

        while (iterFriends.hasNext()) {
            Friend iterFriend = iterFriends.next();

            //Find a friend of the myUser.
            //필요한 것 : iterFriend의 토큰 값 -> 대신 토큰으로 이메일을 찾아서 이메일 비교
            if (iterFriend.getFromUser().getEmail().equals(fromUserEmail)) { //yes( unique case )
                myFriends.add(iterFriend.getToUser().getNickname());
            }
        }
        return myFriends; // the friends of the myUser (LIST TYPE)
    }
}