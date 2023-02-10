package com.photaiary.Photaiary.post.photo.entity;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="dailyIndex", nullable = false)
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

    public void editComment(String comment) {
        if(!comment.isEmpty())
            this.comment = comment;
    }

    public void editTag(String tag) {
        if(!tag.isEmpty() && tag.length()==9){
            this.tag = tag;
        }
    }

    @Builder
    public Photo(Long id, Daily daily, String latitude, String longitude, String image, String comment, DeleteStatus deleteStatus, String tag) {
        this.id = id;
        this.daily = daily;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.comment = comment;
        this.deleteStatus = deleteStatus;
        this.tag = tag;
    }
}
