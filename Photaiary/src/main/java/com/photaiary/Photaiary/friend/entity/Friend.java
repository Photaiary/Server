package com.photaiary.Photaiary.friend.entity;

import com.photaiary.Photaiary.user.entity.User;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Friend{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    @Column(name="friendIndex")
    private Long Index;


    @ManyToOne
    @JoinColumn(name="userIndex", insertable = false, updatable = false)
    @NotNull
    private User userIndex;

    @ManyToOne
    @JoinColumn(name="userIndex", insertable = false, updatable = false)
    @NotNull
    private User otherIndex;

    @ColumnDefault("active")
    @Enumerated(EnumType.STRING)
    private StatusType status;
}
