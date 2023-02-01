package com.photaiary.Photaiary.post.daily;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Daily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dailyIndex")
    private Long dailyIndex;

    @Column(name="userIndex",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIndex;

    @Column(name = "daily",nullable = false)
    private String daily;

    @ManyToOne
    @JoinColumn(name = "userIndex", insertable = false, updatable = false)
    private User user;
}
