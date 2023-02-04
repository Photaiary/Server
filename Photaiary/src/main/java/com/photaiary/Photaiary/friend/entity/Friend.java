package com.photaiary.Photaiary.friend.entity;

import com.photaiary.Photaiary.user.entity.User;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class Friend{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
//    @Column(name="friend_index")
    @Column
    private Long index;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "user_index", nullable = false, name="from_user")
    private User fromUser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "user_index", nullable = false, name="to_user")
    private User toUser;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(9) default 'ACTIVE'")
    private StatusType status;
}
