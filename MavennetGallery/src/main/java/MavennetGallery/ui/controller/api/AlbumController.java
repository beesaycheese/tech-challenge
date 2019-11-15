package MavennetGallery.ui.controller.api;

import MavennetGallery.common.entity.Album;
import MavennetGallery.common.exception.AlbumNotFoundException;
import MavennetGallery.logic.AlbumLogic;
import MavennetGallery.logic.GateKeeper;
import org.springframework.web.bind.annotation.*;
import MavennetGallery.common.Access.AccessLevel;
import MavennetGallery.common.Access.UserInfo;

import java.util.List;

@RestController
public class AlbumController extends BaseRestController {

    private AlbumLogic albumLogic;

    private GateKeeper gateKeeper;

    public AlbumController(AlbumLogic albumLogic, GateKeeper gateKeeper) {
        this.albumLogic = albumLogic;
        this.gateKeeper = gateKeeper;
    }

    //Get list of albums
    @GetMapping("/albums")
    public List<Album> all() {
        UserInfo currentUser = gateKeeper.verifyLoginAccess();
        return albumLogic.findAllForUser(currentUser);
    }

    //Get one specific album via id
    @GetMapping("/albums/{id}")
    public Album oneAlbum(@PathVariable Long id) {
        Album album = albumLogic.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException(id));

        gateKeeper.verifyAccessForAlbum(album, AccessLevel.CAN_READ);

        return album;
    }
}
