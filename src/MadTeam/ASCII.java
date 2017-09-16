package MadTeam;

/**
 *
 * @author FrancoMain
 */
import java.awt.*;
import java.awt.image.BufferedImage;

public final class ASCII {

    int IMG_MAX_HEIGHT=0, IMG_MAX_WIDTH=0;
    boolean[] config;
    public ASCII(int IMG_HEIGHT, int IMG_WIDTH, boolean[] config) {
        this.IMG_MAX_HEIGHT=IMG_HEIGHT;
        this.IMG_MAX_WIDTH=IMG_WIDTH;
        this.config=config;
    }

    public String convert(BufferedImage image) {
        image = getResizedImage(image);
        StringBuilder sb = new StringBuilder((image.getWidth() + 1) * image.getHeight());
        for (int y = 0; y < image.getHeight(); y++) {
            if (sb.length() != 0) {
                sb.append("\n");
            }
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                //double gValue = (double) pixelColor.getRed() * 0.2989 + (double) pixelColor.getBlue() * 0.5870 + (double) pixelColor.getGreen() * 0.1140;
                double gValue = (double) pixelColor.getRed() * 0.2989 + (double) pixelColor.getBlue() * 0.5870 + (double) pixelColor.getGreen() * 0.1140;
                final char s = returnStrPos(gValue);
                sb.append(s);
            }
        }
        return sb.toString();
    }

    /**
     * Create a new string and assign to it a string based on the grayscale
     * value. If the grayscale value is very high, the pixel is very bright and
     * assign characters such as . and , that do not appear very dark. If the
     * grayscale value is very lowm the pixel is very dark, assign characters
     * such as # and @ which appear very dark.
     *
     * @param g grayscale
     * @return char
     */
    private char returnStrPos(double g)//takes the grayscale value as parameter
    {
        final char str;

        if (g >= 230.0) {
            str = config[0] ? '*' : ' ';
        } else if (g >= 200.0) {
            //str = '.';
            str = config[1] ? '*' : ' ';
        } else if (g >= 180.0) {
            str = config[2] ? '*' : ' ';
        } else if (g >= 160.0) {
            str = config[3] ? '*' : ' ';
        } else if (g >= 130.0) {
            str = config[4] ? '*' : ' ';
        } else if (g >= 100.0) {
            str = config[5] ? '*' : ' ';
        } else if (g >= 70.0) {
            str = config[6] ? '*' : ' ';
        } else if (g >= 50.0) {
            str = config[7] ? '*' : ' ';
        } else {
            str = config[8] ? '*' : ' ';
        }
        return str; // return the character

    }

    public BufferedImage getResizedImage(BufferedImage originalImage) {
        int LIMIT_IMG_WIDTH = IMG_MAX_WIDTH;
        int LIMIT_IMG_HEIGHT = IMG_MAX_HEIGHT;
        int img_width = originalImage.getWidth();
        int img_height = originalImage.getHeight();
        if(img_width>LIMIT_IMG_WIDTH){
           //Resize img size proportionally to fix the max width
           
           int propor = (LIMIT_IMG_WIDTH*100)/img_width;
           img_width=LIMIT_IMG_WIDTH;
           img_height=img_height*propor;
           if(img_height>LIMIT_IMG_HEIGHT)
               img_height=LIMIT_IMG_HEIGHT;
        }
        
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage resizedImage = new BufferedImage(img_width, img_height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, img_width, img_height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage;

    }
}
