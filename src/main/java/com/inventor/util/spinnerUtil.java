package com.inventor.util;

import com.inventor.controllers.mainCtrl;
import com.inventor.entities.FoodsEntity;
import com.inventor.entities.entityMaps.orderFootMap;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;

public class spinnerUtil {

    public static void setOnlyNumbers(Spinner spinner, FoodsEntity obj, int initial)  {
        NumberFormat format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);
                format.parse(c.getControlNewText(), parsePosition);
                if (parsePosition.getIndex() == 0 ||
                        parsePosition.getIndex() < c.getControlNewText().length()) {
                    return null;
                }
            }
            return c;
        };
        TextFormatter<Integer> priceFormatter = new TextFormatter<Integer>(
                new IntegerStringConverter(), 0, filter);

        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 10000, initial));
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            orderFootMap object = new orderFootMap();
            object.setCount((Integer) newValue);
            object.setFood(obj);
            mainCtrl.ordered.removeIf( e -> e.getFood().equals(obj));
            int value = Integer.parseInt(String.valueOf(newValue));
            if (value != 0) {
                mainCtrl.ordered.add(object);
            }
        });
        spinner.setEditable(true);
        spinner.getEditor().setTextFormatter(priceFormatter);
    }
}
