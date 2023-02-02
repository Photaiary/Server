package com.photaiary.Photaiary.friend.entity;

import com.photaiary.Photaiary.user.entity.User;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Friend{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    private int Index;


    @JoinColumn(name="userIndex")
    @NotNull
    private User userIndex;

    @JoinColumn(name="userIndex")
    @NotNull
    private User friendIndex;

    @ColumnDefault("active")
    @Enumerated(EnumType.STRING)
    private StatusType status;

}
