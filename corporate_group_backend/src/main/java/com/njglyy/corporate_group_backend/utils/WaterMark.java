package com.njglyy.corporate_group_backend.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class WaterMark {
    public static byte[] generateImageWithText(String text) throws Exception {
        int width = 500;
        int height = 200;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Set rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the font and color (with transparency)
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        g2d.setColor(new Color(150, 150, 150, 50)); // Gray color with transparency

        // Rotate the text
        AffineTransform orig = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(-45), width / 2.0, height / 2.0);
        g2d.setTransform(at);

        // Draw the text centered in the image
        FontMetrics fm = g2d.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;
        int y = ((height - fm.getHeight()) / 2) + fm.getAscent();

        g2d.drawString(text, x, y);
        g2d.setTransform(orig);
        g2d.dispose();

        // Convert BufferedImage to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        return imageInByte;
    }

    public static void addWatermarkToSheet(XSSFSheet sheet, int pictureIdx) {
        CreationHelper helper = sheet.getWorkbook().getCreationHelper();
        Drawing<?> drawing = sheet.createDrawingPatriarch();

        // Calculate the range to cover with the watermark
        int lastRow = sheet.getLastRowNum();
        int lastCol = 0;
        for (Row row : sheet) {
            if (row.getLastCellNum() > lastCol) {
                lastCol = row.getLastCellNum();
            }
        }

        // Set the anchor to position the image
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(0);
        anchor.setRow1(0);
        anchor.setCol2(lastCol + 1);
        anchor.setRow2(lastRow + 5);

        // Insert the picture
        Picture pict = drawing.createPicture(anchor, pictureIdx);

        // Adjust the image position and size
        // No need to call pict.resize() as we're setting the anchor manually
    }
}
