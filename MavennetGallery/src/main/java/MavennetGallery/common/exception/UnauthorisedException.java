package MavennetGallery.common.exception;

public class UnauthorisedException extends RuntimeException {

    public UnauthorisedException() {
        super("You are unauthorised to view");
    }

}
