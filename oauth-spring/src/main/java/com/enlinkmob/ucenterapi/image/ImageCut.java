package com.enlinkmob.ucenterapi.image;

import com.enlinkmob.ucenterapi.util.ImageUtils;
import com.enlinkmob.ucenterapi.util.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zhaowy on 2015/2/9.
 */
public abstract class ImageCut {
    @Autowired
    protected ServerProperties serverProperties;

    protected Map<String, BufferedImage> imageCut(String path) throws Exception {
        Map<String, BufferedImage> bufferedImages = new HashMap<>();
        BufferedImage bigImg = ImageUtils.compressImageDIY(path, 200, 250);
        BufferedImage middleImg = ImageUtils.compressImage(path, 120, 120);
        BufferedImage smallImg = ImageUtils.compressImage(path, 48, 48);
        bufferedImages.put("big", bigImg);
        bufferedImages.put("middle", middleImg);
        bufferedImages.put("small", smallImg);
        return bufferedImages;
    }

    public abstract Map<String, String> imgStore(Map<String, String> pathes, String serverPath, boolean needcut) throws Exception;
}
