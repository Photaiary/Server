package com.photaiary.Photaiary.post.diary;

import com.photaiary.Photaiary.post.daily.Daily;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryIndex;
    @OneToOne
    @JoinColumn(name = "daily_index")
    private Daily daily;
    @Column
    private String diaryTitle;
    @Column
    private String diaryContent;

    @Builder
    public Diary(Long diaryIndex, Daily daily, String diaryTitle, String diaryContent) {
        this.diaryIndex = diaryIndex;
        this.daily = daily;
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
    }
}

