package halo.util.image;

import java.io.File;
import java.io.IOException;

public class ImageParam {

    private OriginInfo originInfo;

    private int quality;

    private double sharp0;

    private double sharp1;

    private boolean clearExif;

    public ImageParam(File file, int quality, double sharp0, double sharp1,
            boolean clearExif) throws IOException {
        this.originInfo = new OriginInfo(file);
        this.quality = quality;
        this.sharp0 = sharp0;
        this.sharp1 = sharp1;
        this.clearExif = clearExif;
    }

    public OriginInfo getOriginInfo() {
        return originInfo;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public double getSharp0() {
        return sharp0;
    }

    public void setSharp0(double sharp0) {
        this.sharp0 = sharp0;
    }

    public double getSharp1() {
        return sharp1;
    }

    public void setSharp1(double sharp1) {
        this.sharp1 = sharp1;
    }

    public boolean isClearExif() {
        return clearExif;
    }

    public void setClearExif(boolean clearExif) {
        this.clearExif = clearExif;
    }
}