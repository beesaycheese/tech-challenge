package MavennetGallery.storage.repository;

import MavennetGallery.common.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByUserIdentifier(String userIdentifier);

    Album findByUserIdentifierAndTitleEquals(String userIdentifier, String title);
}
