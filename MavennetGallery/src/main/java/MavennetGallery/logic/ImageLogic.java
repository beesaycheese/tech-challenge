package MavennetGallery.logic;

import MavennetGallery.common.entity.Album;
import MavennetGallery.common.entity.Image;
import MavennetGallery.storage.repository.ImageRepository;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ImageLogic {

    private ImageRepository imageRepository;

    public ImageLogic(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    public List<Image> findAllImageByUserAndAlbum(String userIdentifier, Album album) {
        return imageRepository.findAllByUserIdentifierEqualsAndAlbumEquals(userIdentifier, album);
    }

    @Transactional
    public Image findImageByUserAndId(String userIdentifier, Long id) {
        return imageRepository.findByUserIdentifierAndId(userIdentifier, id);
    }

    @Transactional
    public Image saveImageForUserAndAlbum(String userIdentifier, Album album, Image image) {
        Image newImage = new Image();
        newImage.setId(image.getId());
        newImage.setUserIdentifier(userIdentifier);
        newImage.setAlbum(album);
        newImage.setImageURL(image.getImageURL());
        newImage.setThumbnailURL(image.getThumbnailURL());
        newImage.setTitle(image.getTitle());

        return imageRepository.save(newImage);
    }
}
