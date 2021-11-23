package com.inventor.viewUtils;

import com.inventor.controllers.mainCtrl;
import com.inventor.entities.FoodsEntity;
import com.inventor.entities.entityMaps.orderFootMap;
import com.inventor.util.ImageUtils;
import com.inventor.util.spinnerUtil;
import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class footChoiceInitializer {

    public void addGridPanChild(GridPane pane, List<FoodsEntity> ls) {
        pane.getChildren().clear();
        int size = ls.size();
        AnchorPane[] nodes = new AnchorPane[size];
        int iterator = 0;
        for (FoodsEntity o : ls) {
            try {
                nodes[iterator] = FXMLLoader.load(getClass().getResource("/footNode.fxml"));
                for (Node p : nodes[iterator].getChildren()) {
                    if (p instanceof Label) {
                        String id = p.getId();
                        if ("name".equals(id)) {
                            ((Label) p).setText(o.getName());
                        } else if ("price".equals(id)) {
                            ((Label) p).setText(String.valueOf(o.getPrice()));
                        }
                    } else if (p instanceof ImageView) {
                        try {
                            if (o.getImg() != null && o.getImg().length > 0) {
                                ((ImageView) p).setImage(ImageUtils.byteArray2Image(o.getImg()));
                            }
                        } catch (NullPointerException er) {
                            er.printStackTrace();
                        }
                    } else if (p instanceof JFXButton) {
                        String id = p.getId();
                        if ("edit".equals(id)) {
                            p.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    try {
                                        int count = Integer.valueOf(((JFXButton) p).getText());
                                        ((JFXButton) p).setText(String.valueOf(count + 1));
                                    } catch (Exception e) {
                                        ((JFXButton) p).setText("1");
                                    }
                                    final int[] t = {0};
                                    mainCtrl.ordered.forEach(e -> {
                                        if (e.getFood().equals(o)) {
                                            e.setCount(e.getCount() + 1);
                                            t[0]++;
                                        }
                                    });
                                    if (t[0] == 0) {
                                        orderFootMap object = new orderFootMap();
                                        object.setCount(1);
                                        object.setFood(o);
                                        mainCtrl.ordered.add(object);
                                    }
                                }
                            });
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            iterator++;
        }
        fillScanGridPane(nodes, size, pane);
    }

    private void fillScanGridPane(AnchorPane[] panes, int sizeNode, GridPane pane) {
        pane.getChildren().clear();

        double rows = sizeNode/6 + 1;
        for (int i = 1; i <= rows;i++) {
            pane.addRow(i);
        }
        int k = 0;
        try {
            for (int i = 0; i < rows;i++) {
                for (int j = 0;j < 6;j++) {
                    pane.add(panes[k], j, i);
                    k++;
                }
            }
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {

        }
    }
}
