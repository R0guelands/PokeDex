package br.com.up.printing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;

public class Printing {

    private BufferedImage img;
    private double pixval;

    public void convertToAscii(String imgurl) {
        try {
            URL url = new URL(imgurl);
            img = ImageIO.read(url);
            img = resizeImage(img, 90, 45);
        } catch (IOException e) {
        }

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color pixcol = new Color(img.getRGB(j, i));
                pixval = (((pixcol.getRed() * 0.30) + (pixcol.getBlue() * 0.59) + (pixcol.getGreen() * 0.11)));
                print(strChar(pixval));
            }
            try {
                System.out.println("");
            } catch (Exception e) {
            }
        }
    }

    public String strChar(double g) {
        String str = " ";
        if (g >= 240) {
            str = "&";
        } else if (g >= 170) {
            str = "#";
        } else if (g >= 110) {
            str = "!";
        } else if (g >= 60) {
            str = "l";
        } else {
            str = " ";
        }
        return str;
    }

    public void print(String str) {
        try {
            System.out.print(str);
        } catch (Exception e) {
        }
    }

    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        try {
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            java.awt.Graphics2D graphics2D = resizedImage.createGraphics();
            graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
            graphics2D.dispose();
            return resizedImage;
        } catch (Exception e) {
            return null;
        }

    }
}
