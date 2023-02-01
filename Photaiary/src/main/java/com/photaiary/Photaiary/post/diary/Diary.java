package com.photaiary.Photaiary.post.diary;

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
    @JoinColumn(name="dailyIndex", insertable = false, updatable = false)
    @Column(nullable = false)
    private Daily daily;
    @Column
    private String diaryTitle;
    @Column
    private String diaryContent;
    public Diary(Long diaryIndex, Diary diary, String diaryTitle, String diaryContent){
        this.diaryIndex = diaryIndex;
        this.diary = diary;
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
    }
}

