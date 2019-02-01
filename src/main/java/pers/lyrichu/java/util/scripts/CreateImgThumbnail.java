package pers.lyrichu.java.util.scripts;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateImgThumbnail {
    private static String inputImgPath = "src/main/resources/beauty.jpeg";
    private static String outputImgPath = "src/main/resources/beauty_thumbnail.jpeg";

    public static void main(String[] args) {
        int thumbWidth = 100;
        int thumbHeight = 150;
        int quality = 100;
        try {
            createThubnail(inputImgPath,thumbWidth,thumbHeight,quality,outputImgPath);
            System.out.printf("create thumbnail for %s successfully!",inputImgPath);
        } catch (Exception e) {
            System.err.println("error:"+e);
        }
    }

    // 得到图像的缩略图
    private static void createThubnail(String inputImgPath,int thumbWidth,int thumbHeight,
                                       int quality,String outputImgPath)
            throws InterruptedException,FileNotFoundException,IOException
    {
        // load img from filename
        Image image = Toolkit.getDefaultToolkit().getImage(inputImgPath);
        MediaTracker mediaTracker = new MediaTracker(new Container());
        mediaTracker.addImage(image,0); // id = 0
        mediaTracker.waitForID(0);
        // 设置缩略图的 width and height
        // 缩略图宽高比
        double thumbRatio = (double) thumbWidth/(double) thumbHeight;
        int imgWidth = image.getWidth(null);
        int imgHeight = image.getHeight(null);
        // 图像宽高比
        double imgRatio = (double)imgWidth/(double)imgHeight;
        if(thumbRatio < imgRatio){
            thumbHeight = (int)(thumbWidth/imgRatio);
        } else {
            thumbWidth = (int)(thumbHeight*imgRatio);
        }
        // draw origin image to thumbnail image object
        // and scale it to the new size
        BufferedImage thumbImage = new BufferedImage(thumbWidth,thumbHeight,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = thumbImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image,0,0,thumbWidth,thumbHeight,null);

        // save thumb image to outputImgPath
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputImgPath));
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
        quality = Math.max(0,Math.min(quality,100));
        param.setQuality((float)quality/100.0f,false);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(thumbImage);
        out.close();
    }
}
