package halo.util.image;

public class ImageSizeMaker {

    /**
     * 设定缩放图片的尺寸。图片比指定尺寸小或者相等，使用原尺寸;图片为方图，尺寸 > size，设定新图片尺寸为size;其他方式按照比例进行设定
     * 
     * @param width
     * @param height
     * @param size
     * @return
     */
    public static ImageSize makeSize(int width, int height, int size) {
        ImageSize imageSize = new ImageSize();
        // 图片比指定尺寸小，使用原尺寸
        if (width <= size && height <= size) {
            imageSize.setWidth(width);
            imageSize.setHeight(height);
            return imageSize;
        }
        // 图片为方图，尺寸 > 指定size，设定新图片尺寸为size
        if (width == height) {
            imageSize.setHeight(size);
            imageSize.setWidth(size);
            return imageSize;
        }
        // 图片为长方图
        // 横图
        if (width > height) {
            imageSize.setWidth(size);
            imageSize.setHeight(height * size / width);
            return imageSize;
        }
        // 竖图
        imageSize.setHeight(size);
        imageSize.setWidth(width * size / height);
        return imageSize;
    }
}
