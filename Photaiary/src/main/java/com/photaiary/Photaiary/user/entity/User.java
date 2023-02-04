package com.photaiary.Photaiary.user.entity;

import com.photaiary.Photaiary.friend.entity.Friend;
import com.photaiary.Photaiary.friend.entity.StatusType;
import com.photaiary.Photaiary.post.daily.Daily;
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
    @Column(name = "user_index", insertable = false, updatable = false)
    private Long userIndex;

    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String nickname;
    @Column(length = 8)
    private String birthday;
    @Column
    private String profileImage;

    @Column
    LocalDateTime deletedAt;
    @Column(name = "ROLE", columnDefinition = "varchar(8) default 'active'")
    private StatusType status;
    @Column(name = "theme", columnDefinition = "varchar(8) default 'blue'")
    @Enumerated(EnumType.STRING)
    private ThemeType theme;

    @OneToMany(mappedBy = "user")
    private List<Daily> dailies = new ArrayList<>();
}
