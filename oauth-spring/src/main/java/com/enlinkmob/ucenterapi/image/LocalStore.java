package com.enlinkmob.ucenterapi.image;

import com.enlinkmob.ucenterapi.exception.ResponseException;
import com.enlinkmob.ucenterapi.util.ImageUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zhaowy on 2015/2/9.
 */
@Service("localStore")
public class LocalStore extends ImageCut {

    @Override
    public Map<String, String> imgStore(Map<String, String> pathes, String serverPath, boolean needcut) throws Exception {
        Map<String, String> storePathes = new HashMap<>();
        if (!needcut) {
            for (Map.Entry<String, String> pathEntry : pathes.entrySet()) {
                File sourceFile = new File(pathEntry.getValue());
                switch (pathEntry.getKey()) {
                    case "big":
                        FileUtils.copyFile(sourceFile, new File(serverPath + this.serverProperties.getHeadIconLocal() + "/big/" + sourceFile.getName()));
                        storePathes.put("big", this.serverProperties.getHost() + this.serverProperties.getHeadIconLocal() + "/big/" + sourceFile.getName());
                        break;
                    case "middle":
                        FileUtils.copyFile(sourceFile, new File(serverPath + this.serverProperties.getHeadIconLocal() + "/middle/" + sourceFile.getName()));
                        storePathes.put("middle", this.serverProperties.getHost() + this.serverProperties.getHeadIconLocal() + "/middle/" + sourceFile.getName());
                        break;
                    case "small":
                        FileUtils.copyFile(sourceFile, new File(serverPath + this.serverProperties.getHeadIconLocal() + "/small/" + sourceFile.getName()));
                        storePathes.put("small", this.serverProperties.getHost() + this.serverProperties.getHeadIconLocal() + "/small/" + sourceFile.getName());
                        break;
                }
            }
        } else {
            String path = pathes.get("headicon");
            File sourceFile = new File(path);
            if (!sourceFile.exists()) {
                throw new ResponseException("can not find file", "can not find file");
            }
            Map<String, BufferedImage> bufferedImages = this.imageCut(path);
            String pngname = sourceFile.getName().substring(0, sourceFile.getName().lastIndexOf(".")) + ".png";
            for (Map.Entry<String, BufferedImage> bimg : bufferedImages.entrySet()) {
                switch (bimg.getKey()) {
                    case "big":
                        ImageUtils.OutImage(serverPath + this.serverProperties.getHeadIconLocal() + "/big/" + pngname, bimg.getValue());
                        storePathes.put("big", this.serverProperties.getHost() + this.serverProperties.getHeadIconLocal() + "/big/" + pngname);
                        break;
                    case "middle":
                        ImageUtils.OutImage(serverPath + this.serverProperties.getHeadIconLocal() + "/middle/" + pngname, bimg.getValue());
                        storePathes.put("middle", this.serverProperties.getHost() + this.serverProperties.getHeadIconLocal() + "/middle/" + pngname);
                        break;
                    case "small":
                        ImageUtils.OutImage(serverPath + this.serverProperties.getHeadIconLocal() + "/small/" + pngname, bimg.getValue());
                        storePathes.put("small", this.serverProperties.getHost() + this.serverProperties.getHeadIconLocal() + "/small/" + pngname);
                        break;
                }
            }

        }
        return storePathes;
    }
}
