package MavennetGallery.common.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Exportable(name = "Image", nameInDB = "image")
@Entity
public class Image {

    @Id
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateUsingIdentity", strategy = "MavennetGallery.common.entity.UseExistingIdOtherwiseGenerateUsingIdentity")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "a_id")
    private Long id;

    // each album will be created by each user, userIdentifier is used to distinguished the user
    private String userIdentifier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Album album;

    @Exportable(name = "Title", nameInDB = "title")
    @Column(name = "title")
    private String title;

    @Exportable(name = "Image URL", nameInDB = "image_url")
    @Column(name = "image_url")
    private String imageURL;

    @Exportable(name = "Thumbnail URL", nameInDB = "thumbnail_url")
    @Column(name = "thumbnail_url")
    private String thumbnailURL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
