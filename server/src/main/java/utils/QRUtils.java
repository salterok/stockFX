package utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;


/**
 * Created by salterok on 04.06.2015.
 */
public class QRUtils {
    private static final int WHITE_COLOR = 255;
    private static final int BLACK_COLOR = 0;

    public static BufferedImage encode(String content, BarcodeFormat format, int width, int height) {
        try {
            MultiFormatWriter writer = new MultiFormatWriter();
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix matrix = writer.encode(content, format, width, height, hints);

            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    img.setRGB(i, j, (matrix.get(i, j) ? Color.BLACK : Color.WHITE).getRGB());
                }
            }
            return img;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
