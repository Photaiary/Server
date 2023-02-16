package com.photaiary.Photaiary.user.entity;

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
}
