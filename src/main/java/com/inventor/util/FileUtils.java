package com.inventor.util;

import javafx.scene.layout.AnchorPane;

import java.io.File;

public class FileUtils {

    public static File openFile(AnchorPane root) {
        javafx.stage.FileChooser chooser = new javafx.stage.FileChooser();
        chooser.setTitle("Select Image File");
        chooser.getExtensionFilters().addAll(new javafx.stage.FileChooser.ExtensionFilter("Img Document files", "*.jpg"));
        File file = chooser.showOpenDialog(root.getScene().getWindow());
        return file;
    }

}
