package lk.ijse.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.db.DataBase;
import lk.ijse.pos.model.Customer;
import lk.ijse.pos.model.Item;
import lk.ijse.pos.views.tm.CartTM;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlaceOrderFormController {
    public AnchorPane contextOfOrderForm;
    public Label txtDate;
    public Label txtTime;
    public ComboBox<String> cmbCustomerId;
    public TextField txtCustomerName;
    public TextField txtCustomerAddress;
    public TextField txtCustomerSalary;
    public ComboBox<String> cmbItemCode;
    public TextField txtItemDescription;
    public TextField ItemQTYOnHand;
    public TextField txtItemUnitPrice;
    public TextField txtQty;
    public TableView tblcart;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQTY;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;

    public void initialize() {


        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        setDateAndTime();
        loadAllCustomerIds();
        loadAllItemIds();

        //-------------------

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomerData(newValue);
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setItemData(newValue);
        });

        //-------------------

    }

    private void setCustomerData(String id) {
        for (Customer c : DataBase.customersList
        ) {
            if (id.equals(c.getId())) {
                txtCustomerName.setText(c.getName());
                txtCustomerAddress.setText(c.getAddress());
                txtCustomerSalary.setText(String.valueOf(c.getSalary()));
                break;
            }
        }
    }

    private void setItemData(String id) {
        for (Item i : DataBase.itemList
        ) {
            if (id.equals(i.getId())) {
                txtItemDescription.setText(i.getDescription());
                txtItemUnitPrice.setText(String.valueOf(i.getUnitPrice()));
                ItemQTYOnHand.setText(String.valueOf(i.getQtyOnHand()));
                break;
            }
        }
    }

    private void loadAllCustomerIds() {
        ObservableList<String> customerObList = FXCollections.observableArrayList();

        for (Customer c : DataBase.customersList
        ) {
            customerObList.add(c.getId());
        }
        cmbCustomerId.setItems(customerObList);
    }

    private void loadAllItemIds() {
        ObservableList<String> itemObList = FXCollections.observableArrayList();

        for (Item i : DataBase.itemList
        ) {
            itemObList.add(i.getId());
        }
        cmbItemCode.setItems(itemObList);
    }

    private void setDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-dd");
        /*String tempDate=f.format(date );
        txtDate.setText(tempDate);*/
        txtDate.setText(f.format(date));
        txtTime.setText(new SimpleDateFormat("HH:mm:ss").format(date));
    }


    public void backToHome(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) contextOfOrderForm.
                getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass()
                        .getResource
                                ("../views/DashBoardForm.fxml"))));
    }

    ObservableList<CartTM> cartObList = FXCollections.observableArrayList();

    public void addToCart(ActionEvent actionEvent) {
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtItemUnitPrice.getText());
        double total = qty * unitPrice;

        CartTM tm = new CartTM(
                cmbItemCode.getValue().toString(),
                txtItemDescription.getText(),
                qty,
                unitPrice,
                total
        );
        cartObList.add(tm);
        tblcart.setItems(cartObList);

    }
}
