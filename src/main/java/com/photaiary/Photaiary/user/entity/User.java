package com.photaiary.Photaiary.user.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    // AUTO_INCREMENT
    @Column(name = "user_index", nullable = false)
    private Long userIndex;

    @Column(unique = true, nullable = false)  // not null
    private String email;

    @Column
    private String password;

    @Column(unique = true)
    private String nickname;

    @Column
    private String name;

    @Column(length = 8)
    private String birthdate;

    @Column
    private String profileImage; //기본값 감자이미지.

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Column
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(9) default 'ACTIVE'")
    private StatusType status;

    @ManyToMany
    @JoinTable(
            name="user_authority",
            joinColumns = {@JoinColumn(name="user_index",referencedColumnName = "user_index")},
            inverseJoinColumns = {@JoinColumn(name="authority_name",referencedColumnName = "authority_name")}
    )
    private Set<Authority> authorities;


    @Builder
    public User(String email, String password, String nickname, String name, String birthdate,
                String profileImage){

        this.email=email;
        this.password=password;
        this.nickname=nickname;
        this.name=name;
        this.birthdate=birthdate;
        this.profileImage=profileImage;
    }

}
