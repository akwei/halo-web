package halo.util.image;

import java.awt.Rectangle;
import java.io.File;

import magick.CompressionType;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.PreviewType;

public class JmagickImageShaper implements ImageShaper {

    static {
        System.setProperty("jmagick.systemclassloader", "no");
    }

    @Override
    public File cut(ImageParam imageParam, ImageRect cutImageRect, String path,
            String name) throws ImageException {
        this.mkdirs(path);
        String filePath = path + name;
        try {
            Rectangle rect = new Rectangle(
                    cutImageRect.getImageOrigin().getX(), cutImageRect
                            .getImageOrigin().getY(), cutImageRect
                            .getImageSize().getWidth(), cutImageRect
                            .getImageSize().getHeight());
            ImageInfo info = this.createImageInfo(imageParam);
            info = this.processQuality(imageParam, info);
            MagickImage image = new MagickImage(info);
            image = this.processClearExif(imageParam, image);
            image = this.processSharp(imageParam, image);
            MagickImage cropped = image.cropImage(rect);
            cropped.setFileName(filePath);
            cropped.writeImage(info);
            return new File(filePath);
        }
        catch (MagickException e) {
            throw new ImageException(e);
        }
    }

    @Override
    public File scale(ImageParam imageParam, ImageSize scaleImageSize,
            String path, String name) throws ImageException {
        this.mkdirs(path);
        try {
            ImageInfo info = this.createImageInfo(imageParam);
            info = this.processQuality(imageParam, info);
            MagickImage image = new MagickImage(info);
            image = this.processClearExif(imageParam, image);
            MagickImage scaled = null;
            scaled = image.scaleImage(scaleImageSize.getWidth(),
                    scaleImageSize.getHeight());
            image = this.processSharp(imageParam, scaled);
            String filePath = path + name;
            scaled.setFileName(filePath);
            scaled.writeImage(info);
            return new File(filePath);
        }
        catch (MagickException e) {
            throw new ImageException(e);
        }
    }

    @Override
    public File cutAndScale(ImageParam imageParam, ImageRect cutImageRect,
            ImageSize scaledmageSize, String path, String name)
            throws ImageException {
        this.mkdirs(path);
        String filePath = path + name;
        try {
            Rectangle rect = new Rectangle(
                    cutImageRect.getImageOrigin().getX(), cutImageRect
                            .getImageOrigin().getY(), cutImageRect
                            .getImageSize().getWidth(), cutImageRect
                            .getImageSize().getHeight());
            ImageInfo info = this.createImageInfo(imageParam);
            info = this.processQuality(imageParam, info);
            MagickImage image = new MagickImage(info);
            image = this.processClearExif(imageParam, image);
            image = this.processSharp(imageParam, image);
            MagickImage cropped = image.cropImage(rect).scaleImage(
                    scaledmageSize.getWidth(), scaledmageSize.getHeight());
            cropped.setFileName(filePath);
            cropped.writeImage(info);
            return new File(filePath);
        }
        catch (MagickException e) {
            throw new ImageException(e);
        }
    }

    private ImageInfo createImageInfo(ImageParam imageParam)
            throws MagickException {
        ImageInfo info = new ImageInfo(imageParam.getOriginInfo().getFile()
                .getAbsolutePath());
        if (imageParam.getOriginInfo().getImageType().getValue() == ImageType.PNG
                .getValue()) {
            info.setCompression(CompressionType.NoCompression);
        }
        else {
            info.setCompression(CompressionType.JPEGCompression);
            info.setPreviewType(PreviewType.JPEGPreview);
        }
        return this.processQuality(imageParam, info);
    }

    private boolean mkdirs(String path) {
        File f = new File(path);
        if (!f.exists() || !f.isDirectory()) {
            return f.mkdirs();
        }
        return false;
    }

    private ImageInfo processQuality(ImageParam imageParam, ImageInfo info) {
        if (imageParam.getQuality() > 0) {
            try {
                info.setQuality(imageParam.getQuality());
            }
            catch (MagickException e) {// 忽略这个错误
            }
        }
        return info;
    }

    private MagickImage processClearExif(ImageParam imageParam,
            MagickImage image) {
        if (!imageParam.isClearExif()) {
            return image;
        }
        try {
            image.profileImage("*", null);
        }
        catch (Exception e1) {// 忽略这个错误
        }
        return image;
    }

    private MagickImage processSharp(ImageParam imageParam, MagickImage image) {
        if (imageParam.getSharp0() > 0 || imageParam.getSharp1() > 0) {
            try {
                return image.sharpenImage(imageParam.getSharp0(),
                        imageParam.getSharp1());
            }
            catch (MagickException e) {// 忽略这个错误
            }
        }
        return image;
    }
}