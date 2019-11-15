package MavennetGallery.logic;

import MavennetGallery.common.Access.UserInfo;
import MavennetGallery.common.entity.Album;
import MavennetGallery.storage.repository.AlbumRepository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AlbumLogic {
    private AlbumRepository albumRepository;


    public AlbumLogic(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> findAllForUser(UserInfo userInfo) {
        return albumRepository.findByUserIdentifier(userInfo.getUserId());
    }

    public Optional<Album> findById(Long id) {
        return albumRepository.findById(id);
    }

    public Album saveForUser(Album album, UserInfo userInfo) {
        Album newAlbum = new Album();
        newAlbum.setId(album.getId());
        newAlbum.setTitle(album.getTitle());
        newAlbum.setUserIdentifier(album.getUserIdentifier());

        return albumRepository.save(newAlbum);
    }

}
