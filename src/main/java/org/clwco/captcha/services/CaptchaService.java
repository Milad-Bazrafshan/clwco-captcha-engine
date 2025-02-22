package org.clwco.captcha.services;

import org.clwco.captcha.dto.CaptchaResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class CaptchaService {
    public static CaptchaResponse getCaptcha() {
        int number = (int) ((Math.random() * 900000) + 1000);
        String outputPath = "captchaImage.png";

        try {
            System.out.println("Success : " + outputPath);
            CaptchaResponse captchaResponse = new CaptchaResponse();
            captchaResponse.setCaptchaCode(number);
            captchaResponse.setBase64(convertNumberToImage(number, outputPath));
            return captchaResponse;
        } catch (IOException e) {
            System.err.println("Error : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String convertNumberToImage(int number, String outputPath) throws IOException {
        int width = 200;
        int height = 60;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g.drawLine(x1, y1, x2, y2);
        }

        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g.fillRect(x, y, 2, 2);
        }

        g.setColor(Color.WHITE);
//        g.fillRect(0, 0, width, height);

//        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));

        String numberText = Integer.toString(number);

        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(numberText);
        int textHeight = fontMetrics.getHeight();
        int x = (width - textWidth) / 2;
        int y = (height - textHeight) / 2 + fontMetrics.getAscent();

        g.drawString(numberText, 30, 50);

        ImageIO.write(image, "png", new File(outputPath));

        g.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
