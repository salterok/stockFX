package app;

import com.google.common.collect.Lists;
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
import pojo.LocalConfig.QRTemplate;
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
        //toGenQR = Collections.nCopies(34, toGenQR.get(0)); // for test
        List<List<Item>> parts = Lists.partition(toGenQR, Config.getInstance().qr.perPage);
        List<BufferedImage> qrImages = parts.stream().map(this::renderQRs).collect(Collectors.toList());
        writePdf(qrImages, file);
    }

    private void writePdf(List<BufferedImage> images, File file) throws Exception {
        PDDocument document = new PDDocument();

        for (BufferedImage image : images) {
            float width = image.getWidth();
            float height = image.getHeight();
            PDPage page = new PDPage(new PDRectangle(width, height));
            document.addPage(page);
            PDXObjectImage img = new PDJpeg(document, image);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.drawImage(img, 0, 0);
            contentStream.close();
        }

        document.save(file);
        document.close();
    }

    private BufferedImage renderQRs(List<Item> items) {
        int qrSize = Config.getInstance().qr.size;
        int perRow = Config.getInstance().qr.perRow;
        int count = items.size();

        QRTemplate info = getTemplateImage(perRow, count, qrSize);

        for (int i = 0; i < count; i++) {
            Item item = items.get(i);
            String content = Long.toString(item.invNumber);
            BufferedImage image = QRUtils.encode(content, BarcodeFormat.QR_CODE, qrSize, qrSize);
            drawAt(info, image, (i % 4), (i / 4), item.name);
        }
        return info.template;
    }

    private QRTemplate getTemplateImage(int perRow, int count, int qrSize) {
        final int titleHeight = 20;
        int itemWidth = qrSize + 1;
        int itemHeight = qrSize + 1;
        int itemDescHeight = titleHeight + 1;
        int width = perRow * itemWidth;
        int height = (count / 4 + 1) * (itemHeight + itemDescHeight);
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // region draw template
        Graphics gr = img.getGraphics();
        gr.setColor(Color.GRAY);
        // draw vertical lines qrSize + 1 (as delimiter)
        int vLinesCount = perRow - 1;
        for (int i = 1; i <= vLinesCount; i++) {
            int mark = itemWidth * i;
            gr.drawLine(mark - 1, 0, mark - 1, height);
        }
        // draw horizontal lines qrSize + 1 (as delimiter) + titleHeight + 1 (second delimiter)
        int hLinesCount = count / perRow - 1;
        for (int i = 0; i <= hLinesCount; i++) {
            int vOffset = (itemHeight + itemDescHeight) * i;
            int mark = vOffset + itemHeight;
            int markDesc = vOffset + (itemHeight + itemDescHeight);
            gr.drawLine(0, mark - 1, width, mark - 1);
            gr.drawLine(0, markDesc - 1, width, markDesc - 1);
        }
        gr.dispose();
        // endregion draw template

        return new QRTemplate(img, itemWidth, itemHeight, itemDescHeight);
    }

    private void drawAt(QRTemplate info, BufferedImage source, int x, int y, String title) {
        int itemHeightWithTitle = info.itemHeight + info.itemDescHeight;
        for (int xi = 0; xi < source.getWidth(); xi++) {
            for (int yj = 0; yj < source.getHeight(); yj++) {
                if (source.getRGB(xi, yj) == -1)
                    info.template.setRGB(x * info.itemWidth + xi, y * itemHeightWithTitle + yj, Color.WHITE.getRGB());
                else
                    info.template.setRGB(x * info.itemWidth + xi, y * itemHeightWithTitle + yj, Color.BLACK.getRGB());
            }
        }
        Graphics gr = info.template.createGraphics();
        gr.setColor(Color.BLACK);
        gr.setFont(new Font("Monospace", 0, 9));
        FontMetrics metric = gr.getFontMetrics();
        int strWidth = metric.stringWidth(title);
        while (strWidth >= info.itemWidth - 4) {
            title = title.substring(0, title.length() - 3) + "..";
            strWidth = metric.stringWidth(title);
        }
        int offset = (info.itemWidth - strWidth) / 2;
        int lowerBound = (int)Math.ceil(metric.getLineMetrics(title, null).getDescent());

        gr.drawString(title, x * info.itemWidth + offset, (y + 1) * (itemHeightWithTitle) - lowerBound);
        gr.dispose();
    }
}
