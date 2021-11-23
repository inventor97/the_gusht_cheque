package com.inventor.util;

import com.inventor.entities.ClientEntity;
import com.inventor.entities.DeleveryService;
import com.inventor.entities.entityMaps.orderTableMap;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class generateXlSXprinter {

    private static Image logo = new Image("logo.jpg", 420,280,false,false);

    public static boolean saveSoldCheck(ClientEntity client, DeleveryService delivery, ObservableList<orderTableMap> foods) {
        StringBuilder val = new StringBuilder();

        val.append(client.getAddress());

        Image qrCode = generateQRCode.generateCode(val.toString(), 420, 420).getImage();

        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        File file = new File(fw.getDefaultDirectory() + "/historyCheck");
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("sheet1");
            sheet.setFitToPage(true);
            sheet.setAutobreaks(true);
            sheet.setMargin(Sheet.BottomMargin, 0.0);
            sheet.setMargin(Sheet.TopMargin, 0.0);
            sheet.setMargin(Sheet.LeftMargin, 0.0);
            sheet.setMargin(Sheet.RightMargin, 0.0);
            Row headerRow = sheet.createRow(0);
            headerRow.setHeight((short) 3875);
            setUtils(sheet);

            setHeaderImages(workbook, sheet,logo, 0, 0);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String dataDate = dtf.format(now);

            Row datRow1 = sheet.createRow(5);
            datRow1.setHeight((short) 850);
            datRow1.setRowStyle(titleStyle(workbook, 15, false));
            Cell main = datRow1.createCell(0);
            main.setCellStyle(titleStyle(workbook, 15, false));
            main.setCellValue(dataDate);

            Cell no = datRow1.createCell(1);
            no.setCellStyle(style(workbook));
            no.setCellValue("Tr:  ");

            Row datRow3 = sheet.createRow(7);
            datRow3.setHeight((short) 1860);
            Cell date = datRow3.createCell(0);
            date.setCellStyle(style(workbook));
            date.setCellValue("The Go'sht\n" +
                    "Manzil: Toshkent sh. Chilonzor t.\nIntegro 7 -qavat\n" +
                    "Tel nomer: 99895 5137775");
//todo address to'lidirish kerak
            Row datRow4 = sheet.createRow(11);
            datRow4.setHeight((short) 1060);
            Cell date4 = datRow4.createCell(0);
            date4.setCellStyle(titleStyle(workbook, 14, false));
            date4.setCellValue("Hisob to'lov varag'i");

            addRow(sheet, workbook, "Mijoz: ", client.getName(), 12);
            addRow(sheet, workbook, "Mijoz tel raqami: ", client.getPhoneNumber(), 13);
            StringBuilder orderedFood = new StringBuilder();
            for (orderTableMap o : foods) {
                orderedFood.append(o.toString());
            }
            Double summ = 0.0;
            for (orderTableMap o : foods) {
                summ += o.getCount()*o.getPrice();
            }
            addRow(sheet, workbook, "Taom: " , orderedFood.toString(), 14);
            addRow(sheet, workbook, "Summa: ", String.valueOf(summ), 15);
            addRow(sheet, workbook, "Yetkazuvchi: ", delivery.getName(), 16);
            addRow(sheet, workbook, "Yetkazuvhi tel-nomeri: ", delivery.getPhoneNumber(), 17);
            addRow(sheet, workbook, "Izoh: ", "", 18);

            Row bottom = sheet.createRow(46);
            bottom.setHeight((short) 650);
            Cell btm = bottom.createCell(0);
            btm.setCellStyle(titleStyle(workbook, 12, true));
            btm.setCellValue("Yoqimli ishtaha");

            setHeaderImages(workbook, sheet, qrCode, 0, 22);

            String xlsName = client.getName() + new Date().getTime();
            FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath() + "/" + xlsName + ".xls");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            printJob.printWithXPrinter(xlsName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void addRow(HSSFSheet sheet, HSSFWorkbook workbook, String firstClm, String secondClm, int rowOrder) {
        Row row = sheet.createRow(rowOrder);
        row.setHeight((short) -1);
        row.setRowStyle(rowStyle(workbook));
        row.setHeight((short)-1);

        Cell cell0 = row.createCell(0);
        cell0.setCellStyle(mainStyle(workbook));
        cell0.setCellValue(firstClm);

        Cell cell1 = row.createCell(1);
        cell1.setCellStyle(style(workbook));
        cell1.setCellValue(secondClm);
    }

    private static void setHeaderImages(HSSFWorkbook workbook, Sheet sheet, Image image, int col, int row){
        try {
            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", s);
            byte[] res  = s.toByteArray();
            s.close();
            int pictureIdx = workbook.addPicture(res, Workbook.PICTURE_TYPE_PNG);
            CreationHelper helper = workbook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(col);
            anchor.setRow1(row);
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            pict.resize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setUtils(Sheet sheet) {
        sheet.addMergedRegion(new CellRangeAddress(0, 4, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 5));
        sheet.addMergedRegion(new CellRangeAddress(7, 10, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(11, 11, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(46, 46, 0, 1));

        sheet.setColumnWidth(0, 5730);
        sheet.setColumnWidth(1, 9980);
    }

    private static CellStyle style(HSSFWorkbook workbook) {
        CellStyle st = workbook.createCellStyle();
        st.setWrapText(true);
        Font newFont = workbook.createFont();
        newFont.setBold(true);
        st.setWrapText(true);
        newFont.setFontName("Poppins");
        newFont.setFontHeightInPoints((short) 15);
        st.setFont(newFont);
        st.setAlignment(HorizontalAlignment.RIGHT);
        st.setVerticalAlignment(VerticalAlignment.CENTER);
        return st;
    }

    private static CellStyle rowStyle(HSSFWorkbook workbook) {
        CellStyle st = workbook.createCellStyle();
        st.setWrapText(true);
        return st;
    }

    private static CellStyle mainStyle(HSSFWorkbook workbook) {
        CellStyle st = workbook.createCellStyle();
        Font newFont = workbook.createFont();
        newFont.setBold(true);
        st.setWrapText(true);
        newFont.setItalic(false);
        newFont.setFontName("Poppins");
        newFont.setFontHeightInPoints((short) 15);
        st.setFont(newFont);
        return st;
    }

    private static CellStyle titleStyle(HSSFWorkbook workbook, int size, boolean italic) {
        CellStyle st = workbook.createCellStyle();
        st.setWrapText(true);
        Font newFont = workbook.createFont();
        newFont.setBold(true);
        st.setWrapText(true);
        newFont.setItalic(italic);
        newFont.setFontName("Poppins");
        newFont.setFontHeightInPoints((short) size);
        st.setFont(newFont);
        st.setAlignment(HorizontalAlignment.CENTER);
        st.setVerticalAlignment(VerticalAlignment.CENTER);
        return st;
    }

}
