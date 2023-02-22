package com.photaiary.Photaiary.post.daily.entity;

import com.photaiary.Photaiary.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Daily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dailyIndex")
    private Long dailyIndex;

    @ManyToOne
    @JoinColumn(name = "userIndex", nullable = false)
    private User user;

    @Column(name = "dailyValue")
    private String dailyValue;

    @Builder
    public Daily(Long dailyIndex, User user, String dailyValue) {
        this.dailyIndex = dailyIndex;
        this.user = user;
        this.dailyValue = dailyValue;
    }
}
