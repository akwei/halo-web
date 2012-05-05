package halo.util.image;

public class ImageRect {

    private ImageOrigin imageOrigin;

    private ImageSize imageSize;

    public ImageRect() {
    }

    public ImageRect(int x, int y, int width, int height) {
        this.imageOrigin = new ImageOrigin(x, y);
        this.imageSize = new ImageSize(width, height);
    }

    public ImageOrigin getImageOrigin() {
        return imageOrigin;
    }

    public void setImageOrigin(ImageOrigin imageOrigin) {
        this.imageOrigin = imageOrigin;
    }

    public ImageSize getImageSize() {
        return imageSize;
    }

    public void setImageSize(ImageSize imageSize) {
        this.imageSize = imageSize;
    }
}
