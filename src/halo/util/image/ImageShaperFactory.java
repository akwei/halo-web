package halo.util.image;

import java.util.HashMap;
import java.util.Map;

public class ImageShaperFactory {

    public static final String SHAPER_JMAGICK = "jmagick";

    public static final String SHAPER_MOCK = "mock";

    private static final Map<String, ImageShaper> map = new HashMap<String, ImageShaper>();
    static {
        map.put(SHAPER_JMAGICK, new JmagickImageShaper());
        map.put(SHAPER_MOCK, new MockImageShaper());
    }

    /**
     * 获得处理图片的类，
     * 
     * @param type
     *            目前图片只支持JMagick，因此参数需要传入jmagick，忽略大小写
     * @return 图片处理类，null为不支持的类型
     */
    public static ImageShaper getImageShaper(String type) {
        return map.get(type);
    }
}