package com.inventor.util;


import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class printJob {


    public static void printWithXPrinter(String fileName) {
        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        File file = new File(fw.getDefaultDirectory() + "/historyCheck");
            setDefaultPrinter("XP-80C (copy 1)");
        try {
            Desktop.getDesktop().print(new File(file.getAbsolutePath() + "/" + fileName + ".xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printOstatkaXPrinter(String splrname) {

        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        File file = new File(fw.getDefaultDirectory() + "/Остатки");
        setDefaultPrinter("XP-80C (copy 1)");
        try {
            Desktop.getDesktop().print(new File(file.getAbsolutePath() + "/" + splrname + ".xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean setDefaultPrinter(String printerName) {
        String name = "";

        PrintService[] service =
                PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService o : service) {
            if(o.getName().endsWith(printerName)){
                name = o.getName();
            }
        }

        String defaultPrinterSetter = "cscript \"C:\\Windows\\System32\\Printing_Admin_Scripts\\en-US\\prnmngr.vbs\" -t -p \"" + name + "\"\n";
        try {
            Process setDefaultPrinterProcess = Runtime.getRuntime().exec("cmd /c start cmd.exe /C \n" + defaultPrinterSetter);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
