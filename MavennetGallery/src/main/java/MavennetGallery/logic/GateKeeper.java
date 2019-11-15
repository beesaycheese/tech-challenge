package MavennetGallery.logic;

import MavennetGallery.common.entity.Album;
import MavennetGallery.common.util.Const;
import MavennetGallery.storage.repository.AlbumAccessControlRepository;
import org.springframework.stereotype.Component;
import MavennetGallery.common.Access.AccessLevel;
import MavennetGallery.common.Access.UserInfo;
import MavennetGallery.common.exception.UnauthorisedException;

import java.util.Optional;

@Component
public class GateKeeper {

    //Ideally use Google App engine for to get the current user from the UserServiceFactory
    //Setting a global variable is not a good design. I used it for simplicity of the program
    public UserInfo userInfo = new UserInfo();

    private AlbumAccessControlRepository albumAccessControlRepository;

    public GateKeeper(AlbumAccessControlRepository albumAccessControlRepository) {
        this.albumAccessControlRepository = albumAccessControlRepository;
    }

    public Optional<UserInfo> getCurrentLoginUser() {
        return Optional.of(userInfo);
    }

    public void setUserInfo(String userId) {
        UserInfo newUserInfo = new UserInfo();
        newUserInfo.setUserId(userId);
        userInfo = newUserInfo;
    }

    public UserInfo verifyLoginAccess() {
        return getCurrentLoginUser().orElseThrow(UnauthorisedException::new);
    }

    public void verifyDeletionAccessForAlbum(Album album) {
        if (album == null) {
            throw new UnauthorisedException();
        }

        UserInfo currentUser = getCurrentLoginUser()
                .orElseThrow(UnauthorisedException::new);

        if (!currentUser.getUserId().equals(album.getUserIdentifier())) {
            throw new UnauthorisedException();
        }
    }

    public void verifyAccessForAlbum(Album album, AccessLevel accessLevel) {
        if (album == null) {
            throw new UnauthorisedException();
        }

        // check public access
        if (albumAccessControlRepository.existsByAlbumAndUserIdentifierEqualsAndAccessLevelEquals(album, Const.SpecialIdentifier.PUBLIC, accessLevel)) {
            return;
        }
        // can_write means can_read
        if (accessLevel == AccessLevel.CAN_READ &&
                albumAccessControlRepository.existsByAlbumAndUserIdentifierEqualsAndAccessLevelEquals(album, Const.SpecialIdentifier.PUBLIC, AccessLevel.CAN_WRITE)) {
            return;
        }

        UserInfo currentUser = getCurrentLoginUser()
                .orElseThrow(UnauthorisedException::new);

        // creator can always access their own album
        if (album.getUserIdentifier().equals(currentUser.getUserId())) {
            return;
        }

        if (albumAccessControlRepository.existsByAlbumAndUserIdentifierEqualsAndAccessLevelEquals(album, currentUser.getUserId(), accessLevel)) {
            return;
        }

        // can_write means can_read
        if (accessLevel == AccessLevel.CAN_READ &&
                albumAccessControlRepository.existsByAlbumAndUserIdentifierEqualsAndAccessLevelEquals(album, currentUser.getUserId(), AccessLevel.CAN_WRITE)) {
            return;
        }

        throw new UnauthorisedException();
    }
 }
