package com.photaiary.Photaiary.post.photo;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Column
    String status;
    @Column
    String tag;
}
