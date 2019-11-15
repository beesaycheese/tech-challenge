package MavennetGallery.logic;

import MavennetGallery.common.entity.Album;
import MavennetGallery.common.entity.AlbumAccessControl;
import MavennetGallery.storage.repository.AlbumAccessControlRepository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AlbumAccessControlLogic {

    private AlbumAccessControlRepository albumAccessControlRepository;

    public AlbumAccessControlLogic(AlbumAccessControlRepository albumAccessControlRepository) {
        this.albumAccessControlRepository = albumAccessControlRepository;
    }

    public List<AlbumAccessControl> findAllByAlbum(Album album) {
        return albumAccessControlRepository.findAllByAlbum(album);
    }

    public AlbumAccessControl saveForAlbum(Album album, AlbumAccessControl albumAccessControl) {
        AlbumAccessControl newAlbumAccessControl = new AlbumAccessControl();

        newAlbumAccessControl.setAlbum(album);
        newAlbumAccessControl.setUserIdentifier(albumAccessControl.getUserIdentifier());
        newAlbumAccessControl.setAccessLevel(albumAccessControl.getAccessLevel());

        return albumAccessControlRepository.save(newAlbumAccessControl);
    }

    public AlbumAccessControl updateAlbumAccessControl(AlbumAccessControl oldAlbumAccessControl, AlbumAccessControl newAlbumAccessControl) {
        oldAlbumAccessControl.setAccessLevel(newAlbumAccessControl.getAccessLevel());

        return albumAccessControlRepository.save(oldAlbumAccessControl);
    }

    public Optional<AlbumAccessControl> findById(Long id) {
        return albumAccessControlRepository.findById(id);
    }

    public void deleteById(Long id) {
        albumAccessControlRepository.deleteById(id);
    }
}
