package com.inventor.controllers;

import com.inventor.dao.impls.*;
import com.inventor.entities.ClientEntity;
import com.inventor.entities.DeleveryService;
import com.inventor.entities.FoodFamilyEntity;
import com.inventor.entities.FoodsEntity;
import com.inventor.entities.entityMaps.foodFamilyTable;
import com.inventor.entities.entityMaps.orderFootMap;
import com.inventor.entities.entityMaps.orderTableMap;
import com.inventor.util.ImageUtils;
import com.inventor.util.generateXlSXprinter;
import com.inventor.util.spinnerUtil;
import com.inventor.util.windowMassage;
import com.inventor.viewUtils.footChoiceInitializer;
import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.GridView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class mainCtrl implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane bkg;

    @FXML
    private AnchorPane onBkg;

    @FXML
    private AnchorPane leftBkg;

    @FXML
    private VBox leftSideBar;

    @FXML
    private JFXButton paymentBtn;

    @FXML
    private JFXButton databaseBtn;

    @FXML
    private JFXButton reportBtn;

    @FXML
    private AnchorPane paymentPane;

    @FXML
    private VBox clientForm;

    @FXML
    private JFXTextField clientphoneNum;

    @FXML
    private JFXTextField clientName;

    @FXML
    private JFXTextArea clientAddress;

    @FXML
    private VBox deliveryForm;

    @FXML
    private JFXTextField deliveryName;

    @FXML
    private JFXTextField deliveryPhoneNum;

    @FXML
    private VBox orderFoodForm;

    @FXML
    private JFXButton goshtOption;

    @FXML
    private JFXButton ChoyxonaBtn;

    @FXML
    private TableView<orderTableMap> orderTable;

    @FXML
    private TableColumn<orderTableMap, Integer> no;

    @FXML
    private TableColumn<orderTableMap, String> orders;

    @FXML
    private TableColumn<orderTableMap, Double> price;

    @FXML
    private TableColumn<orderTableMap, Integer> count;

    @FXML
    private TableColumn<orderTableMap, Double> sumPrice;

    @FXML
    private TableColumn<orderTableMap, FoodsEntity> foodClass;

    @FXML
    private TableColumn<orderTableMap, Button> editClm;

    @FXML
    private JFXButton OrderBtn;

    @FXML
    private Label sumUpPrice;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton swipe;

    @FXML
    private AnchorPane popBkg;

    @FXML
    private AnchorPane choiceFootPane;

    @FXML
    private Label couiseName;

    @FXML
    private JFXButton backToMain;

    @FXML
    private JFXButton cancel;

    @FXML
    private ImageView selectedOrderImg;

    @FXML
    private Label selectedOrderName;

    @FXML
    private Label selectedOrderPrice;

    @FXML
    private Spinner selectedOrderSpinner;

    @FXML
    private AnchorPane editOrder;

    @FXML
    private ScrollPane scrlPane;

    @FXML
    private TableView<foodFamilyTable> familyTable;

    @FXML
    private TableColumn<foodFamilyTable,String> familyName;

    @FXML
    private TableColumn<foodFamilyTable,List<FoodsEntity>> foodClassFamily;

    @FXML
    private GridPane gridFoodsPane;

    private footChoiceInitializer footChoice;
    public static List<orderFootMap> ordered = new ArrayList<>();

    private ClientEntity activeClient = new ClientEntity();
    private DeleveryService activeDelivery = new DeleveryService();

    private JFXAutoCompletePopup<String> clientNumberAuto = new JFXAutoCompletePopup<>();
    private JFXAutoCompletePopup<String> deliveryNameAuto = new JFXAutoCompletePopup<>();

    private void initAutoSuggestions() {
        List<String> lsClNumbers = new ArrayList<>(clientDAOImpls.getInstance().getNames());
        List<String> lsDeliveryNames = new ArrayList<>(deliveryServiceDAOImpls.getInstance().getNames());

        clientNumberAuto.getSuggestions().addAll(lsClNumbers);
        deliveryNameAuto.getSuggestions().addAll(lsDeliveryNames);

        clientNumberAuto.setPrefSize(250, 260);
        deliveryNameAuto.setPrefSize(250, 260);

        clientNumberAuto.setStyle("-fx-text-fill: #222;" +
                "    -fx-font-family: \"Roboto-Regular\";" +
                "    -fx-font-weight: bold;" +
                "    -fx-font-size: 15;" +
                "    -fx-text-alignment: center;" +
                "    -fx-selection-bar: #faf3dd;" +
                "    -fx-background-color: transparent;");

        deliveryNameAuto.setStyle("-fx-text-fill: #222; " +
                "-fx-font-family: \"Roboto-Regular\"; " +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 15; " +
                "-fx-text-alignment: center; " +
                "-fx-selection-bar: #faf3dd; " +
                "-fx-background-color: transparent;");

        clientNumberAuto.setSelectionHandler(a -> {
            clientphoneNum.setText(a.getObject());
            activeClient = clientDAOImpls.getInstance().getByPhoneNum(a.getObject());
            try {
                if (activeClient.getName() != null && !activeClient.getName().isEmpty()) {
                    clientName.setText(activeClient.getName());
                    clientAddress.setText(activeClient.getAddress());
                }
            } catch (NullPointerException e) {}
        });

        deliveryNameAuto.setSelectionHandler(a -> {
            deliveryName.setText(a.getObject());
            activeDelivery = deliveryServiceDAOImpls.getInstance().getByName(a.getObject());
            try {
                if (activeDelivery.getName() != null && !activeDelivery.getName().isEmpty()) {
                    deliveryPhoneNum.setText(activeDelivery.getPhoneNumber());
                }
            }catch (NullPointerException e) {

            }
        });

        clientphoneNum.textProperty().addListener( o -> {
            clientNumberAuto.filter( string -> string.toLowerCase().contains(clientphoneNum.getText().toLowerCase()));
            if (clientNumberAuto.getFilteredSuggestions().isEmpty() || clientphoneNum.getText().isEmpty()) {
                clientNumberAuto.hide();
            } else {
                clientNumberAuto.show(clientphoneNum);
            }
        });

        clientphoneNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    clientphoneNum.setText(oldValue);
                }
            }
        });

        deliveryPhoneNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    deliveryPhoneNum.setText(oldValue);
                }
            }
        });

        deliveryName.textProperty().addListener( o -> {
            deliveryNameAuto.filter( string -> string.toLowerCase().contains(deliveryName.getText().toLowerCase()));
            if (deliveryNameAuto.getFilteredSuggestions().isEmpty() || deliveryName.getText().isEmpty()) {
                deliveryNameAuto.hide();
            } else {
                deliveryNameAuto.show(deliveryName);
            }
        });

    }
    
    private void setActiveNull(){
        activeDelivery = new DeleveryService();
        activeClient = new ClientEntity();
        clientAddress.setText("");
        clientName.setText("");
        clientphoneNum.setText("");
        orderTable.getItems().clear();
        orderTable.setItems(FXCollections.observableList(new ArrayList<>()));
        deliveryName.setText("");
        deliveryPhoneNum.setText("");
        sumUpPrice.setText("Umumiy: 0.0");
        ordered.clear();
        footChoice = new footChoiceInitializer();

        List<String> lsClNumbers = new ArrayList<>(clientDAOImpls.getInstance().getNames());
        List<String> lsDeliveryNames = new ArrayList<>(deliveryServiceDAOImpls.getInstance().getNames());
        clientNumberAuto.getSuggestions().clear();
        deliveryNameAuto.getSuggestions().clear();
        clientNumberAuto.getSuggestions().addAll(lsClNumbers);
        deliveryNameAuto.getSuggestions().addAll(lsDeliveryNames);
    }

    @FXML
    void clickHandler(ActionEvent event)    {
        if (event.getSource() == goshtOption) {
            gridFoodsPane.getChildren().clear();
            setFamilyTableContent(2, "The Go'sht");
        } else if (event.getSource() == ChoyxonaBtn) {
            gridFoodsPane.getChildren().clear();
            setFamilyTableContent(1, "The Choyxona");
        } else if (event.getSource() == backToMain) {
            popBkg.setVisible(false);
            choiceFootPane.setVisible(false);
            setTableValues();
            calculateSumm();
        } else if (event.getSource() == cancel) {
            popBkg.setVisible(false);
            editOrder.setVisible(false);
            setTableValues();
            calculateSumm();
        } else if (event.getSource() == OrderBtn) {
            fillOrder();
        }
    }

    private void setFamilyTableContent(int i, String txt) {
        popBkg.setVisible(true);
        choiceFootPane.setVisible(true);
        couiseName.setText(txt);
        List<FoodFamilyEntity> list = footsFamilyDAOImpls.getInstance().getAll();
        list.removeIf(e -> e.getCousineId() == i);
        List<foodFamilyTable> ls = new ArrayList<>();
        for (FoodFamilyEntity o : list) {
            foodFamilyTable tableObj = new foodFamilyTable();
            tableObj.setName(o.getName());
            tableObj.setFoods(footsDAOImpls.getInstance().getByFamily(o.getId()));
            ls.add(tableObj);
        }
        try {
            familyTable.getItems().clear();
            familyTable.setItems(FXCollections.observableArrayList(ls));
        } catch (NullPointerException e) {
            familyTable.setItems(FXCollections.observableArrayList(ls));
        }
    }

    private void fillOrder() {
        if (checkForFillingData()) {
            activeClient.setName(clientName.getText());
            activeClient.setPhoneNumber(clientphoneNum.getText());
            activeClient.setAddress(clientAddress.getText());

            clientDAOImpls.getInstance().update(activeClient);

            activeDelivery.setName(deliveryName.getText());
            activeDelivery.setPhoneNumber(deliveryPhoneNum.getText());
            deliveryServiceDAOImpls.getInstance().update(activeDelivery);

            ObservableList<orderTableMap> list = orderTable.getItems();
            generateXlSXprinter.saveSoldCheck(activeClient, activeDelivery, list);
            setActiveNull();
        }
    }

    private boolean checkForFillingData() {
        try {
            if (clientName.getText() != null && !clientName.getText().isEmpty()
                    && clientphoneNum.getText() != null && !clientphoneNum.getText().isEmpty()
                    && clientAddress.getText() != null && !clientAddress.getText().isEmpty()) {
                if (deliveryName.getText() != null && !deliveryName.getText().isEmpty()
                        && deliveryPhoneNum.getText() != null && !deliveryPhoneNum.getText().isEmpty()) {
                    if (ordered.size() > 0) {
                        return true;
                    } else {
                        windowMassage.makeToast("Taomlar tanlanmadi");
                        return false;
                    }
                } else {
                    windowMassage.makeToast("Yetkazuvchi ma'lumotlari yetarli emas");
                    return false;
                }
            } else {
                windowMassage.makeToast("Mijoz ma'lumotlari to'liq emas");
                return false;
            }
        } catch (NullPointerException e) {
            windowMassage.makeToast("Ma'lumotlar to'liq emas");
            return false;
        }
    }

    @FXML
    void leftSideAction(ActionEvent event) {

    }

    private void calculateSumm() {
        Double summ = 0.0;
        for (orderFootMap o : ordered) {
            summ += o.getCount()*o.getFood().getPrice();
        }
        sumUpPrice.setText("Umumiy:  " + summ);
    }

    private void initTable() {
        no.setCellValueFactory(new PropertyValueFactory<>("no"));
        orders.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        count.setCellValueFactory(new PropertyValueFactory<>("count"));
        sumPrice.setCellValueFactory(new PropertyValueFactory<>("sumPrice"));
        foodClass.setCellValueFactory(new PropertyValueFactory<>("foodClass"));
        editClm.setCellValueFactory(new PropertyValueFactory<>("btn"));

        familyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        foodClassFamily.setCellValueFactory(new PropertyValueFactory<>("foods"));
    }

    @FXML
    void windowCtrlHandler(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        if (event.getSource() == close) {
            stage.close();
            System.exit(0);
        } else if (event.getSource() == swipe) {
            stage.setIconified(true);
        }
    }


    private void setTableValues() {
        List<orderTableMap> ls = new ArrayList<>();
        final int[] order = {0};
        ordered.forEach(e -> {
            order[0]++;
            ls.add(new orderTableMap(e.getFood().getName()).setValues(e, order[0]));
        });

        for (orderTableMap p : ls) {
            p.getBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String txtName = p.getBtn().getText();
                    ordered.removeIf(e -> e.getFood().getName().equals(txtName));
                    setTableValues();
                    calculateSumm();
                }
            });
        }

        try {
            orderTable.getItems().clear();
            orderTable.setItems(FXCollections.observableArrayList(ls));
        } catch (NullPointerException e) {
            orderTable.setItems(FXCollections.observableArrayList(ls));
        }
    }

    private void initTableListener() {
        orderTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    FoodsEntity obj = orderTable.getSelectionModel().getSelectedItem().getFoodClass();
                    int count = orderTable.getSelectionModel().getSelectedItem().getCount();
                    popBkg.setVisible(true);
                    editOrder.setVisible(true);
                    try {
                        if (obj.getImg().length > 0) {
                            selectedOrderImg.setImage(ImageUtils.byteArray2Image(obj.getImg()));
                        }
                    } catch (NullPointerException | IOException e) {

                    }
                    selectedOrderName.setText(obj.getName());
                    selectedOrderPrice.setText(String.valueOf(obj.getPrice()));
                    spinnerUtil.setOnlyNumbers(selectedOrderSpinner, obj, count);
                }
//                else if (event.getButton() == MouseButton.SECONDARY){
//                    FoodsEntity obj = orderTable.getSelectionModel().getSelectedItem().getFoodClass();
//                    ordered.removeIf(e -> e.getFood().equals(obj));
//                    setTableValues();
//                    calculateSumm();
//                }
            }
        });
        orderTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (orderTable.getSelectionModel().getSelectedItem() != null) {
                    if (event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE) {
                        FoodsEntity slFood = orderTable.getSelectionModel().getSelectedItem().getFoodClass();
                        mainCtrl.ordered.removeIf(e -> e.getFood().equals(slFood));
                        setTableValues();
                        calculateSumm();
                    }
                }
            }
        });

        familyTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (familyTable.getSelectionModel().getSelectedItem() != null) {
                    foodFamilyTable obj = familyTable.getSelectionModel().getSelectedItem();
                    footChoice.addGridPanChild(gridFoodsPane, obj.getFoods());
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        footChoice = new footChoiceInitializer();
        initAutoSuggestions();
        footsDAOImpls.getInstance().getAll();
        initTable();
        initTableListener();
    }
}
