package com.photaiary.Photaiary.user.entity;


import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Setter
//@SQLDelete(sql = "UPDATE  SET status = Status.UNACTIVE WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE user SET deleted_at = CURRENT_TIMESTAMP where user_index = ?")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    // AUTO_INCREMENT
    @Column( nullable = false)
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

//    private String refreshToken;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(9) default 'ACTIVE'")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(9) default 'blue'")
    private Theme theme;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Authority> roles = new ArrayList<>();

    public void setRoles(List<Authority> role) {
        this.roles = role;
        role.forEach(o -> o.setUser(this));
    }


    @Builder
    public User(String email, String password, String nickname, String name, String birthdate){

        this.email=email;
        this.password=password;
        this.nickname=nickname;
        this.name=name;
        this.birthdate=birthdate;
        this.profileImage="";
        this.theme=Theme.blue;
        this.status=Status.ACTIVE;

    }

    //update 매서드들
    public boolean updateTheme(String theme)throws Exception{

        if(theme.equals("blue"))
            this.theme=Theme.blue;
        else if(theme.equals("red"))
            this.theme=Theme.red;
        else if(theme.equals("grey"))
            this.theme=Theme.grey;

        else throw new Exception("해당 theme 없음");

        return true;

    }

    public String updateNickname(String nickname){
        this.nickname=nickname;
        return "변경 됨";
    }

    public boolean updateBirthdate(String birthdate){
        this.birthdate=birthdate;
        return true;
    }

    public boolean updateName(String name){
        this.name=name;
        return true;
    }

    public boolean updatePassword(String encodedPassword){
        this.password=(encodedPassword);
        return true;
    }


}