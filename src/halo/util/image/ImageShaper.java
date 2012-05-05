package halo.util.image;

import java.io.File;

public interface ImageShaper {

    /**
     * 裁剪图片并返回新图片文件
     * 
     * @param imageParam
     *            图片处理参数
     * @param cutImageRect
     *            图片起始位置与大小
     * @param path
     *            处理后的图片存储路径
     * @param name
     *            处理后的图片文件名称
     * @return 处理后的图片文件
     * @throws ImageException
     *             图片处理错误
     */
    File cut(ImageParam imageParam, ImageRect cutImageRect, String path,
            String name) throws ImageException;

    /**
     * 缩放图片并返回处理后的图片文件
     * 
     * @param imageParam
     *            图片处理参数
     * @param scaleImageSize
     *            缩放后的图片大小
     * @param path
     *            缩放后的图片存储路径
     * @param name
     *            缩放后的图片文件名称
     * @return 缩放后的新图片文件
     * @throws ImageException
     *             图片处理错误
     */
    File scale(ImageParam imageParam, ImageSize scaleImageSize, String path,
            String name) throws ImageException;

    /**
     * 先按照位置进行裁剪，然后对裁剪后的图片进行指定尺寸缩放
     * 
     * @param imageParam
     * @param cutImageRect
     *            需要裁剪的位置以及尺寸
     * @param scaledmageSize
     *            裁剪后需要缩放的尺寸
     * @param path
     *            处理后的图片存储路径
     * @param name
     *            处理后的图片文件名称
     * @return 处理后的文件
     * @throws ImageException
     *             图片处理错误
     */
    File cutAndScale(ImageParam imageParam, ImageRect cutImageRect,
            ImageSize scaledmageSize, String path, String name)
            throws ImageException;
    // /**
    // * 对图片进行效果处理，不进行图片缩放
    // *
    // * @param imageParam
    // * @param path
    // * @param name
    // * @return
    // * @throws ImageException
    // */
    // File filter(ImageParam imageParam, String path, String name)
    // throws ImageException;
}
