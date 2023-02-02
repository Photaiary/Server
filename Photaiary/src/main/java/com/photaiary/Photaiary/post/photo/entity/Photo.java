package com.photaiary.Photaiary.post.photo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Photo {
    @Id
    Long photoIndex;
    @ManyToOne
    @JoinColumn(name="dailyIndex", insertable = false, updatable = false)
    Daily daily;
    @Column
    String latitude;
    @Column
    String longitude;
    @Column
    String image;
    @Column
    String comment;
    @ColumnDefault("exist")
    @Enumerated(EnumType.STRING)
    DeleteStatus status;
    @Column(length=9)
    String tag;
}
