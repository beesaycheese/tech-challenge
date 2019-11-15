package MavennetGallery.storage.repository;

import MavennetGallery.common.entity.Image;
import MavennetGallery.common.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {


    List<Image> findAllByUserIdentifierEqualsAndAlbumEquals(String userIdentifier, Album album);

    Image findByUserIdentifierAndId(String userIdentifier, Long id);

    Image findByTitle(String title);

    Image findByTitleAndAlbumEquals(String title, Album album);

    void deleteImageById(Long id);

    void deleteAllByUserIdentifierEquals(String userIdentifier);

    void deleteAllByUserIdentifierEqualsAndAlbumEquals(String userIdentifier, Album album);
}
