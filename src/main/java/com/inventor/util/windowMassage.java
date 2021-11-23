package com.inventor.util;

import com.inventor.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;

public class windowMassage {

    public static void makeToast(String toastMsg) {

        int toastDelay = 1500;
        int fadeInDelay = 1500;
        int fadeOutDelay = 500;

        Stage toastStage = new Stage();
        toastStage.initOwner(Main.primaryStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Label text = new Label();
        text.setText(toastMsg);
        text.setId("toastLabel");
        text.setStyle("-fx-font-weight: bold; -fx-text-fill: #222");
        text.setFont(Font.font("Roboto-Regular", 24));

        StackPane root = new StackPane(text);
        root.setPadding(new Insets(5, 10, 5, 10));
        root.setId("toastPane");
        root.setOpacity(0);
        root.setStyle("-fx-background-radius: 5;-fx-background-color: #E5e5e5");

        Scene scene = new Scene(root, Region.USE_COMPUTED_SIZE, 30);
        scene.setFill(Color.TRANSPARENT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        toastStage.setY(655);
        toastStage.setScene(scene);
        toastStage.show();
        double xlay = width/2 - (double) root.getWidth()/2;
        toastStage.setX(xlay);

        Timeline fadeInTimeline = new Timeline();
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
        fadeInTimeline.getKeyFrames().add(fadeInKey1);
        fadeInTimeline.setOnFinished((ae) ->
        {
            new Thread(() -> {
                try
                {
                    Thread.sleep(toastDelay);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                Timeline fadeOutTimeline = new Timeline();
                KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay), new KeyValue (toastStage.getScene().getRoot().opacityProperty(), 0));
                fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
                fadeOutTimeline.setOnFinished((aeb) -> toastStage.close());
                fadeOutTimeline.play();
            }).start();
        });
        fadeInTimeline.play();
    }
}
