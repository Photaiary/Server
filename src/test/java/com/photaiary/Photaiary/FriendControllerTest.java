package com.photaiary.Photaiary;

import com.photaiary.Photaiary.friend.dto.FriendFollowRequestDto;
import com.photaiary.Photaiary.friend.entity.Friend;
import com.photaiary.Photaiary.friend.entity.FriendRepository;
import com.photaiary.Photaiary.user.entity.User;
import com.photaiary.Photaiary.user.entity.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FriendControllerTest {

    //    @LocalServerPort
//    private int port;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRepository friendRepository;
    //    @Autowired
//    private TestRestTemplate testRestTemplate;
    @Test
    public void 팔로우_테스트() throws Exception{
        //given
        User fromUser = User.builder()
                .email("a")
                .nickname("a")
                .build();

        User toUser = User.builder()
                .email("b")
                .nickname("b")
                .build();

        userRepository.save(fromUser);
        userRepository.save(toUser);

//        FriendFollowRequestDto followRequestDto = FriendFollowRequestDto.builder()
//                .toUserId(toUser.getUserIndex())
//                .fromUserId(fromUser.getUserIndex())
//                .build();

        Friend friend = Friend.builder()
                .toUser(toUser)
                .fromUser(fromUser)
                .build();

        friendRepository.save(friend);

//        String url = "http://localhost:"+port+"/friend/follow";

        Friend expectedFriend = Friend.builder()
                .toUser(User.builder()
                        .email("b")
                        .nickname("b")
                        .build())
                .fromUser(User.builder()
                        .email("a")
                        .nickname("a")
                        .build())
                .build();

        //when
        //[⚠️Error Point!!!!!: 트랙잭션 범위에서 영속성 컨텍스트로 LAZY데이터 받으면, 500에러가 뜬다. 왜?⚠️
        // 방법1. application.properties 조작
        // 방법2. Eager로 변경 -> 좋은 방법이 아님.
        // 방법3. Transactional을 이용해서 영속성 컨텍스트 접근. -> BUT, postForEntity에서 500에러를 반환하게 만드는 원인이 된다.
//        ResponseEntity<Friend> responseEntity = testRestTemplate.postForEntity(url, followRequestDto, Friend.class);


        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Friend> friends = friendRepository.findAll();
        assertThat(friends.get(0).getToUser().getEmail()).isEqualTo(expectedFriend.getToUser().getEmail());
        assertThat(friends.get(0).getFromUser().getEmail()).isEqualTo(expectedFriend.getFromUser().getEmail());

        assertThat(friends.get(0).getFromUser().getNickname()).isEqualTo(expectedFriend.getFromUser().getNickname());
        assertThat(friends.get(0).getFromUser().getNickname()).isEqualTo(expectedFriend.getFromUser().getNickname());
    }
    @Test
    public void 언팔로우_테스트(){
        //given
        User fromUser = User.builder()
                .nickname("a")
                .email("a")
                .build();
        User toUser = User.builder()
                .nickname("b")
                .email("b")
                .build();

        userRepository.save(fromUser);
        userRepository.save(toUser);
        friendRepository.save(Friend.builder()
                .toUser(toUser)
                .fromUser(fromUser)
                .build());

        Friend deletedFriend = Friend.builder()
                .fromUser(User.builder()
                        .email("a")
                        .nickname("a")
                        .build())
                .toUser(User.builder()
                        .email("b")
                        .nickname("b")
                        .build())
                .build();


        //when
        friendRepository.delete(deletedFriend);
        //then
        List<Friend> foundFriends = friendRepository.findAll();

        assertThat(foundFriends).doesNotContain(deletedFriend);
    }

//    @Test
//    public void 친구목록조회_테스트(){
//        //given
//        String arbitraryValue = "abcd";
//        List<User> users = new ArrayList<>();
//        List<Friend> savedFriends = new ArrayList<>();
//        Map<User, User> expectedMapping = new HashMap<>();
//        List<Map<User, User>> expectedResult = new ArrayList<>(); // 굳이?
//
//        for(int i=0; i<4; ++i){
//            users.add(userRepository.save(User.builder()
//                    .nickname(String.valueOf(arbitraryValue.charAt(i)))
//                    .email(String.valueOf(arbitraryValue.charAt(i)))
//                    .build()));
//        }
//
//        for (int i=1; i<4; ++i) {
//            savedFriends.add(friendRepository.save(Friend.builder()
//                    .fromUser(users.get(0))
//                    .toUser(users.get(i))
//                    .build()));
//        }
//        expectedResult.add(expectedMapping.put(users.get(0), users.get(1)));
//        expectedMapping.put(users.get(0), users.get(2));
//        expectedMapping.put(users.get(0), users.get(3));
//
//        //when
//
//        //then
//    }



    //팔로우 나머지 케이스도 작성
    //언팔 테스트(3 케이스 작성)
    //친구 목록 조회 테스트 작성
    //복합키에 대해서 공부하자.
}
