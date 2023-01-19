import java.io.IOException;

public interface PictureI {
    public void loadImage(String fileName, String server, int port) throws IOException;
    public void printImage(float percent);
}
