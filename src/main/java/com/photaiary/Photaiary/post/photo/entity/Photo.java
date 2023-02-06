package com.photaiary.Photaiary.post.photo.entity;

import com.photaiary.Photaiary.post.daily.Daily;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Photo {
    @Id
    @GeneratedValue
    private Long photoIndex;
    @ManyToOne
    @JoinColumn(name="dailyIndex")
    private Daily daily;
    @Column
    private String latitude;
    @Column
    private String longitude;
    @Column
    private String image;
    @Column
    private String comment;
    @Column(name="deleteStatus", columnDefinition = "varchar(9) default 'exist'")
    @Enumerated(EnumType.STRING)
    private DeleteStatus deleteStatus;
    @Column(length=9)
    private String tag;

    @Builder
    public Photo(Long photoIndex, Daily daily, String latitude, String longitude, String image, String comment, DeleteStatus deleteStatus, String tag) {
        this.photoIndex = photoIndex;
        this.daily = daily;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.comment = comment;
        this.deleteStatus = deleteStatus;
        this.tag = tag;
    }
}
