package com.inventor.entities.entityMaps;


import com.inventor.controllers.mainCtrl;
import com.inventor.entities.FoodsEntity;
import com.inventor.util.ImageUtils;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class orderTableMap {
    private int no;
    private String foodName;
    private double price;
    private int count;
    private double sumPrice;
    private FoodsEntity foodClass;
    private Button btn;

    public orderTableMap(String txt) {
        btn = new Button();
        btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        btn.setText(txt);
        btn.setPrefSize(60, 35);
        btn.setStyle("-fx-background-radius: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-background-color:#fff;" +
                "-fx-border-color: #fff;" +
                "-fx-border-width: 1px");
        btn.setGraphic(ImageUtils.createIcon("trash_96px.png", 25, 25));
    }

    public orderTableMap setValues(orderFootMap obj, int order) {
        this.setNo(order);
        this.setFoodName(obj.getFood().getName());
        this.setPrice(obj.getFood().getPrice());
        this.setCount(obj.getCount());
        this.setSumPrice(obj.getCount()*obj.getFood().getPrice());
        this.setFoodClass(obj.getFood());
        return this;
    }

    @Override
    public String toString() {
        return foodName + "--" + count +"*" + price+"\n";
    }
}
