package com.webapp.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2018/1/9 0009 上午 09:29
 * @description
 */
public class ImageUtil {

    private static final int ZERO_ANGLE = 0;
    private static final int RIGHT_ANGLE = 90;
    private static final int STRAIGHT_ANGLE = 180;
    private static final int REFLEX_ANGLE = 270;


    private File originImage;

    /**
     * 正在处理的内存中的图片
     */
    private BufferedImage bufferedImage;
    private BufferedImage dealedImage;

    public ImageUtil(File image) throws IOException {
        if (image.exists()) {
            this.originImage = image;
            this.bufferedImage = ImageIO.read(image);
            this.dealedImage = this.bufferedImage;
        } else {
            throw new FileNotFoundException("图片文件不存在");
        }
    }

    private String getFileType(String imageName) {
        String imageType = "jpg";
        int index = imageName.lastIndexOf(".");
        if (index != -1 && index != imageName.length()) {
            imageType = imageName.substring(index + 1);
        }
        return imageType;
    }

    /**
     * 生成处理后的图片，若参数为null，则修改原始图片
     */
    public boolean createPic(File disposedImage) {
        boolean flag = false;
        if (null == disposedImage) {
            disposedImage = originImage;
        }
        String imageType = getFileType(disposedImage.getName());
        try {
            flag = ImageIO.write(dealedImage, imageType, disposedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 图片缩放处理
     */
    public void scale(int width, int height) {
        BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(this.dealedImage, 0, 0, width, height, null);
        this.dealedImage = newImg;
        g.dispose();
    }

    /**
     * 剪切处理
     */
    public void clip(int srcX, int srcY, int width, int height) {
        BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(this.dealedImage, 0, 0, width, height, srcX, srcY, srcX + width, srcY + height, null);
        this.dealedImage = newImg;
        g.dispose();
    }

    /**
     *  angle：角度，图片旋转角度
     * @param angle
     */
    public void rotate(int angle) {
        int width = this.dealedImage.getWidth();
        int height = this.dealedImage.getHeight();
        int newWidth = 0, newHeight = 0;
        int newRadian = angle;
        if (angle <= RIGHT_ANGLE) {
            newWidth = (int) (width * Math.cos(Math.toRadians(newRadian)) + height * Math.sin(Math.toRadians(newRadian)));
            newHeight = (int) (height * Math.cos(Math.toRadians(newRadian)) + width * Math.sin(Math.toRadians(newRadian)));
        } else if (angle <= STRAIGHT_ANGLE) {
            newRadian = angle - RIGHT_ANGLE;
            newWidth = (int) (height * Math.cos(Math.toRadians(newRadian)) + width * Math.sin(Math.toRadians(newRadian)));
            newHeight = (int) (width * Math.cos(Math.toRadians(newRadian)) + height * Math.sin(Math.toRadians(newRadian)));
        } else if (angle <= REFLEX_ANGLE) {
            newRadian = angle - STRAIGHT_ANGLE;
            newWidth = (int) (width * Math.cos(Math.toRadians(newRadian)) + height * Math.sin(Math.toRadians(newRadian)));
            newHeight = (int) (height * Math.cos(Math.toRadians(newRadian)) + width * Math.sin(Math.toRadians(newRadian)));
        } else {
            newRadian = angle - REFLEX_ANGLE;
            newWidth = (int) (height * Math.cos(Math.toRadians(newRadian)) + width * Math.sin(Math.toRadians(newRadian)));
            newHeight = (int) (width * Math.cos(Math.toRadians(newRadian)) + height * Math.sin(Math.toRadians(newRadian)));
        }
        BufferedImage toStore = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = toStore.createGraphics();
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(angle), width / 2, height / 2);
        if (angle != STRAIGHT_ANGLE) {
            AffineTransform translationTransform = this.findTranslation(affineTransform, this.dealedImage, angle);
            affineTransform.preConcatenate(translationTransform);
        }
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, newWidth, newHeight);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawRenderedImage(this.dealedImage, affineTransform);
        g.dispose();
        this.dealedImage = toStore;
    }

    /**
     * isVertical:true,垂直翻转 ; false,水平翻转
     */
    public void reverse(boolean isVertical) {
        int width = this.dealedImage.getWidth();
        int height = this.dealedImage.getHeight();
        double[] matrix;
        if (isVertical) {
            matrix = new double[]{1, 0, 0, -1, 0, height};
        } else {
            matrix = new double[]{-1, 0, 0, 1, width, 0};
        }
        AffineTransform affineTransform = new AffineTransform(matrix);
        BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawRenderedImage(this.dealedImage, affineTransform);
        g.dispose();
        this.dealedImage = newImg;
    }

    private AffineTransform findTranslation(AffineTransform at, BufferedImage bi, int angle) {//45
        Point2D p2din, p2dout;
        double ytrans = 0.0, xtrans = 0.0;
        if (angle <= RIGHT_ANGLE) {
            p2din = new Point2D.Double(0.0, 0.0);
            p2dout = at.transform(p2din, null);
            ytrans = p2dout.getY();

            p2din = new Point2D.Double(0, bi.getHeight());
            p2dout = at.transform(p2din, null);
            xtrans = p2dout.getX();
        }
        else if (angle <= STRAIGHT_ANGLE) {
            p2din = new Point2D.Double(0.0, bi.getHeight());
            p2dout = at.transform(p2din, null);
            ytrans = p2dout.getY();

            p2din = new Point2D.Double(bi.getWidth(), bi.getHeight());
            p2dout = at.transform(p2din, null);
            xtrans = p2dout.getX();

        }
        else if (angle <= REFLEX_ANGLE) {
            p2din = new Point2D.Double(bi.getWidth(), bi.getHeight());
            p2dout = at.transform(p2din, null);
            ytrans = p2dout.getY();

            p2din = new Point2D.Double(bi.getWidth(), 0.0);
            p2dout = at.transform(p2din, null);
            xtrans = p2dout.getX();

        } else {
            p2din = new Point2D.Double(bi.getWidth(), 0.0);
            p2dout = at.transform(p2din, null);
            ytrans = p2dout.getY();


            p2din = new Point2D.Double(0.0, 0.0);
            p2dout = at.transform(p2din, null);
            xtrans = p2dout.getX();

        }
        AffineTransform tat = new AffineTransform();
        tat.translate(-xtrans, -ytrans);
        return tat;
    }
}
