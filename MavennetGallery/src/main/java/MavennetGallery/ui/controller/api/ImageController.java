package MavennetGallery.ui.controller.api;

import MavennetGallery.common.entity.Album;
import MavennetGallery.common.entity.Image;
import MavennetGallery.common.exception.AlbumNotFoundException;
import MavennetGallery.logic.GateKeeper;
import MavennetGallery.logic.AlbumLogic;
import MavennetGallery.logic.ImageLogic;
import org.springframework.web.bind.annotation.*;
import MavennetGallery.common.Access.AccessLevel;
import MavennetGallery.common.Access.UserInfo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ImageController extends BaseRestController {

    private GateKeeper gateKeeper;

    private AlbumLogic albumLogic;

    private ImageLogic imageLogic;

    public ImageController(GateKeeper gateKeeper, AlbumLogic albumLogic, ImageLogic imageLogic) {
        this.gateKeeper = gateKeeper;
        this.albumLogic = albumLogic;
        this.imageLogic = imageLogic;
    }

    @GetMapping("/albums/{albumId}/images")
    public List<Image> viewImagesInAlbum(@PathVariable Long albumId) {
        UserInfo userInfo = gateKeeper.verifyLoginAccess();
        Album album = albumLogic.findById(albumId)
                .orElseThrow(() -> new AlbumNotFoundException(albumId));
        gateKeeper.verifyAccessForAlbum(album, AccessLevel.CAN_READ);

        return this.imageLogic.findAllImageByAlbum(album);
    }
}
