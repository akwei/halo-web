package halo.util.image;

import halo.util.FileUtil;

import java.io.File;
import java.io.IOException;

public class MockImageShaper implements ImageShaper {

    @Override
    public File cut(ImageParam imageParam, ImageRect cutImageRect, String path,
            String name) throws ImageException {
        return this.copyFile(imageParam.getOriginInfo().getFile(), path, name);
    }

    @Override
    public File scale(ImageParam imageParam, ImageSize scaleImageSize,
            String path, String name) throws ImageException {
        return this.copyFile(imageParam.getOriginInfo().getFile(), path, name);
    }

    @Override
    public File cutAndScale(ImageParam imageParam, ImageRect cutImageRect,
            ImageSize scaledmageSize, String path, String name)
            throws ImageException {
        return this.copyFile(imageParam.getOriginInfo().getFile(), path, name);
    }

    private File copyFile(File file, String path, String name)
            throws ImageException {
        try {
            FileUtil.copyFile(file, path, name);
        }
        catch (IOException e) {
            throw new ImageException(e);
        }
        return new File(path + name);
    }
}
