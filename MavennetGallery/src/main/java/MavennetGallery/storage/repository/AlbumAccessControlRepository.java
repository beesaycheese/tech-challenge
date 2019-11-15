package MavennetGallery.storage.repository;

import MavennetGallery.common.entity.Album;
import MavennetGallery.common.entity.AlbumAccessControl;
import org.springframework.data.jpa.repository.JpaRepository;
import MavennetGallery.common.Access.AccessLevel;

import java.util.List;

public interface AlbumAccessControlRepository extends JpaRepository<AlbumAccessControl, Long> {

    List<AlbumAccessControl> findAllByAlbum(Album album);

    boolean existsByAlbumAndUserIdentifierEqualsAndAccessLevelEquals(Album album, String userIdentifier, AccessLevel accessLevel);

}
