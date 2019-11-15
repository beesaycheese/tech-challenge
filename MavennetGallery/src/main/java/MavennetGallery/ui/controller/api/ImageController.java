package MavennetGallery.ui.controller.api;

import MavennetGallery.common.Access.AccessLevel;
import MavennetGallery.common.Access.UserInfo;
import MavennetGallery.common.entity.Album;
import MavennetGallery.common.entity.Image;
import MavennetGallery.common.exception.AlbumNotFoundException;
import MavennetGallery.logic.AlbumLogic;
import MavennetGallery.logic.GateKeeper;
import MavennetGallery.logic.ImageLogic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

        return this.imageLogic.findAllImageByUserAndAlbum(userInfo.getUserId(), album);
    }

    @GetMapping("/albums/{albumId}/images/{imageId}")
    public Image viewImagesInAlbum(@PathVariable Long albumId, @PathVariable Long imageId) {
        UserInfo userInfo = gateKeeper.verifyLoginAccess();
        Album album = albumLogic.findById(albumId)
                .orElseThrow(() -> new AlbumNotFoundException(albumId));
        gateKeeper.verifyAccessForAlbum(album, AccessLevel.CAN_READ);

        return this.imageLogic.findImageByUserAndId(userInfo.getUserId(), imageId);
    }
}
