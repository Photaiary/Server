package com.photaiary.Photaiary.user.entity;

import com.photaiary.Photaiary.friend.entity.Friend;
import com.photaiary.Photaiary.friend.entity.StatusType;
import com.photaiary.Photaiary.post.photo.entity.Photo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "userIndex")
    private Long userIndex;

    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String nickname;
    @Column(length = 8)
    private String birthday;
    @Column(nullable = true)
    private String profileImage;

    @Column
    LocalDateTime deletedAt;
    @Column
    @ColumnDefault("active")
    private StatusType status;
    @ColumnDefault("blue")
    @Enumerated(EnumType.STRING)
    private ThemeType theme;

//    @OneToMany(mappedBy = "user")
//    private List<Friend> friends = new ArrayList<>();
}
