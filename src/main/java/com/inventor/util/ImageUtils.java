package com.inventor.util;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static ImageView createIcon(String iconURL, int width, int height) {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(iconURL, width,height, false, false));
        return imageView;
    }

    public static byte[] Img2ByteArray(File file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file);

        ByteOutputStream bos = null;
        try {
            bos = new ByteOutputStream();
            ImageIO.write(bufferedImage, "jpg", bos);
        } finally {
            try {
                bos.close();
            } catch (Exception e) {
            }
        }

        return bos == null ? null : bos.getBytes();
    }

    public static Image byteArray2Image(byte[] imgData) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imgData);
        BufferedImage capture = ImageIO.read(bais);
        Image image = SwingFXUtils.toFXImage(capture, null);
        return image;
    }

}
