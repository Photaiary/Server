package com.photaiary.Photaiary.user.entity;

<<<<<<< HEAD
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
=======
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    // AUTO_INCREMENT
    @Column(name = "userIndex", nullable = false)
    private Long userIndex;

    @Column(unique = true, nullable = false)  // not null
    private String email;

    private String password;

    private String nickname;
    private String name;

    @Column(length = 8)
    private String birthdate;

    private String profileImage;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

>>>>>>> user
}
