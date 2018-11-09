package pers.lyrichu.java.util.scripts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PrintScreen {
    private static String savePath = "src/main/resources/screen.png";
    public static void main(String[] args) throws Exception{
        captureScreen(savePath);
    }

    public static void captureScreen(String savePath) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage bufferedImage = robot.createScreenCapture(screenRectangle);
        ImageIO.write(bufferedImage,"png",new File(savePath));
    }
}
