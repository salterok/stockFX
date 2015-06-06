package app;

import com.google.zxing.BarcodeFormat;
import com.sun.imageio.plugins.common.ImageUtil;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import model.Database;
import model.dbo.Item;
import model.dbo.Place;
import model.dbo.Sprint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import pojo.LocalConfig;
import utils.QRUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by salterok on 02.06.2015.
 */
public class SettingsStage {
    private static final Logger logger = LogManager.getLogger(SettingsStage.class);
    private Sprint prevSprint;
    private Sprint currSprint;

    public void init() {
        prevSprint = Database.sprints.getPrevious();
        currSprint = Database.sprints.getCurrent();
    }

    public List<Place> getCurrentPlaces() {
        return Database.places.getSprintPlaces(currSprint.id);
    }

    public List<Place> getPreviousPlaces() {
        if (prevSprint == null) {
            return Collections.emptyList();
        }
        return Database.places.getSprintPlaces(prevSprint.id);
    }

    public void getItemsToGenerateQR(LocalConfig.SettingStageResult settingStageResult) {
        List<Item> items = Database.items.getSprintItems(currSprint.id);
        List<Item> prevItems = prevSprint == null ?
                Collections.emptyList() :
                Database.items.getSprintItems(prevSprint.id);
        settingStageResult.allItems = items;

        Map<Long, Item> map = items.stream().collect(Collectors.toMap(i -> i.invNumber, i -> i));
        for (Item item : prevItems) {
            map.remove(item.invNumber);
        }

        settingStageResult.toGenQR = new ArrayList<>(map.values());
    }

    public void saveToCurrentSprint(List<Place> currPlaces) throws SQLException {
        for (Place place : currPlaces) {
            place.sprint = currSprint;
            Database.places.create(place);
        }
    }

    public void saveItemsQR(List<Item> toGenQR, File file) throws Exception {
        toGenQR = Collections.nCopies(20, toGenQR.get(0));

        int size = Config.getInstance().qr.size;

        int width = 4 * size;
        int height = (toGenQR.size() / 4 + 1) * size;
        BufferedImage main = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);


        for (int i = 0; i < toGenQR.size(); i++) {
            Item item = toGenQR.get(i);
            String content = Long.toString(item.invNumber);
            BufferedImage image = QRUtils.encode(content, BarcodeFormat.QR_CODE, size, size);

            drawAt(main, image, (i % 4) * size, (i / 4) * size);
        }

        writePdf(main);

        ImageIO.write(main, "png", new File("logs/main.png"));
    }

    private void writePdf(BufferedImage main) throws Exception {
        PDDocument document = new PDDocument();

        float width = main.getWidth();
        float height = main.getHeight();
        PDPage page = new PDPage(new PDRectangle(width, height));
        document.addPage(page);
        PDXObjectImage img = new PDJpeg(document, main);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.drawImage(img, 0, 0);
        contentStream.close();

        document.save("logs/test.pdf");
        document.close();
    }

    private void drawAt(BufferedImage target, BufferedImage source, int x, int y) {
        for (int xi = 0; xi < source.getWidth(); xi++) {
            for (int yj = 0; yj < source.getHeight(); yj++) {
                if (source.getRGB(xi, yj) == -1)
                    target.setRGB(x + xi, y + yj, Color.WHITE.getRGB());
                else
                    target.setRGB(x + xi, y + yj, Color.BLACK.getRGB());
            }
        }
    }
}
