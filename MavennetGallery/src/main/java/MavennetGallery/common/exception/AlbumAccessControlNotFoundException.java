package MavennetGallery.common.exception;

public class AlbumAccessControlNotFoundException extends RuntimeException {

    public AlbumAccessControlNotFoundException(Long id, Long accessControlId) {
        super(String.format("Could not find accessControl %d for album %d", accessControlId, id));
    }
}
