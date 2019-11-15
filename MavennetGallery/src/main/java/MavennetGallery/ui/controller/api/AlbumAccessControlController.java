package MavennetGallery.ui.controller.api;

import MavennetGallery.common.entity.Album;
import MavennetGallery.common.entity.AlbumAccessControl;
import MavennetGallery.common.exception.AlbumAccessControlNotFoundException;
import MavennetGallery.common.exception.AlbumNotFoundException;
import MavennetGallery.logic.AlbumAccessControlLogic;
import MavennetGallery.logic.AlbumLogic;
import MavennetGallery.logic.GateKeeper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import MavennetGallery.common.Access.AccessLevel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class AlbumAccessControlController extends BaseRestController {
    private final AlbumLogic albumLogic;

    private final GateKeeper gateKeeper;

    private AlbumAccessControlLogic albumAccessControlLogic;

    public AlbumAccessControlController(AlbumLogic albumLogic, GateKeeper gateKeeper,
                                               AlbumAccessControlLogic albumAccessControlLogic) {
        this.albumAccessControlLogic = albumAccessControlLogic;
        this.albumLogic = albumLogic;
        this.gateKeeper = gateKeeper;
    }

    @GetMapping("/albums/{albumId}/accessControl")
    public List<AlbumAccessControl> all(@PathVariable Long id) {
        Album album = albumLogic.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException(id));
        gateKeeper.verifyAccessForAlbum(album, AccessLevel.CAN_READ);

        return albumAccessControlLogic.findAllByAlbum(album);
    }

    @PostMapping("/albums/{albumId}/accessControl")
    public ResponseEntity<?> addPermission(@RequestBody AlbumAccessControl albumAccessControl, @PathVariable Long id) throws URISyntaxException {
        Album album = albumLogic.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException(id));
        gateKeeper.verifyAccessForAlbum(album, AccessLevel.CAN_WRITE);

        AlbumAccessControl newAccessControl = albumAccessControlLogic.saveForAlbum(album, albumAccessControl);
        return ResponseEntity
                .created(new URI("/albums/" + album.getId() + "/accessControl/" + newAccessControl.getId()))
                .body(newAccessControl);
    }

    @PutMapping("/albums/{albumId}/accessControl/{accessControlId}")
    public ResponseEntity<?> updatePermission(@RequestBody AlbumAccessControl albumAccessControl, @PathVariable Long id, @PathVariable Long accessControlId) throws URISyntaxException {
        Album album = albumLogic.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException(id));
        gateKeeper.verifyAccessForAlbum(album, AccessLevel.CAN_WRITE);

        AlbumAccessControl oldAlbumAccessControl = albumAccessControlLogic.findById(accessControlId)
                .orElseThrow(() -> new AlbumAccessControlNotFoundException(id, accessControlId));

        AlbumAccessControl updatedAlbumAccessControl =
                albumAccessControlLogic.updateAlbumAccessControl(oldAlbumAccessControl, albumAccessControl);

        return ResponseEntity
                .created(new URI("/albums/" + id + "/accessControl/" + accessControlId))
                .body(updatedAlbumAccessControl);
    }

    @DeleteMapping("/albums/{albumId}/accessControl/{accessControlId}")
    public ResponseEntity<?> removePermission(@PathVariable Long id, @PathVariable Long accessControlId) {
        Album album = albumLogic.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException(id));
        gateKeeper.verifyAccessForAlbum(album, AccessLevel.CAN_WRITE);

        albumAccessControlLogic.deleteById(accessControlId);

        return ResponseEntity.noContent().build();
    }
}
