package com.photaiary.Photaiary.post.diary;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    @Column(columnDefinition = "boolean default false")
    private boolean isPublic; //디폴트 false

    @Builder
    public Diary(Long diaryIndex, Daily daily, String diaryTitle, String diaryContent) {
        this.diaryIndex = diaryIndex;
        this.daily = daily;
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
    }

    public void update(String diaryTitle, String diaryContent, Boolean isPublic) {
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
        this.isPublic = isPublic;
    }

    public boolean updateLockState(boolean isPublic){
        this.isPublic = isPublic; // 역접 시킴
        return this.isPublic;
    }

    public static Diary toEntity(Daily getDaily) {
        return Diary.builder()
                .daily(getDaily)
                .build();
    }

}

