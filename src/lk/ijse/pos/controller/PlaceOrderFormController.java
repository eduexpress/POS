package lk.ijse.pos.controller;

import javafx.application.Platform;
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
import lk.ijse.pos.model.ItemDetails;
import lk.ijse.pos.model.Order;
import lk.ijse.pos.views.tm.CartTM;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public TableView<CartTM> tblcart;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQTY;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public Label txtTotal;
    public Label txtOId;

    public void initialize() {


        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        setDateAndTime();
        loadAllCustomerIds();
        loadAllItemIds();
        setOrderId();

        //-------------------

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomerData(newValue);
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setItemData(newValue);
        });

        tblcart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            tempCartTM = newValue;
        });

        //-------------------

    }

    private void setOrderId() {
        if (DataBase.orderList.size() > 0) {

            String tempNumber = DataBase.orderList.get(DataBase.orderList.size() - 1).getOrderId();
            String array[] = tempNumber.split("O-");
            int number = Integer.parseInt(array[1]);
            number++;

            if (number > 100) {
                tempNumber = "O-" + number;
            } else if (number > 10) {
                tempNumber = "O-0" + number;
            } else {
                tempNumber = "O-00" + number;
            }

            txtOId.setText(tempNumber);

        } else {
            txtOId.setText("O-001");
        }
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
       /* txtDate.setText(f.format(date));
        txtTime.setText(new SimpleDateFormat("HH:mm:ss").format(date));*/


        Thread timer = new Thread(() -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            while (true) {
                try {
                    Thread.sleep(1000);// 1 sec
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final String time = simpleDateFormat.format(new Date());
                Platform.runLater(() -> {
                    txtTime.setText(time);
                });
            }
        });
        timer.start();

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

        if (Integer.parseInt(txtQty.getText()) <=
                Integer.parseInt(ItemQTYOnHand.getText())) {

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

            boolean isExists = checkIsExists(tm);
            if (isExists) {
                tblcart.refresh();
            } else {
                cartObList.add(tm);
                calculateTotalCost();
                tblcart.setItems(cartObList);
            }

        } else {
            new Alert(Alert.AlertType.CONFIRMATION,
                    "Invalid qty").show();
        }


    }

    private boolean checkIsExists(CartTM tm) {
        int counter = 0;
        for (CartTM temp : cartObList
        ) {
            if (temp.getItemCode().equals(tm.getItemCode())) {

                if (cartObList.get(counter).getQty() >= Integer.parseInt(txtQty.getText())
                        + cartObList.get(counter).getQty()) {

                    cartObList.get(counter).setQty(tm.getQty() + temp.getQty());
                    cartObList.get(counter).setTotal(tm.getTotal() + temp.getTotal());
                    calculateTotalCost();
                    return true;
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Invalid QTY").show();
                    return true;
                }


            }
            counter++;
        }
        return false;
    }

    CartTM tempCartTM = null;

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        if (tempCartTM != null) {
            for (CartTM tm : cartObList
            ) {
                if (tm.getItemCode().equals(tempCartTM.getItemCode())) {
                    cartObList.remove(tm);
                    calculateTotalCost();
                    tblcart.refresh();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Select a Row.").show();
        }
    }

    void calculateTotalCost() {

        double total = 0.00;

        for (CartTM tm : cartObList
        ) {
            /*total=total+tm.getTotal();*/
            total += tm.getTotal();
        }
        txtTotal.setText(total + " /=");
    }


    public void placeOrder(ActionEvent actionEvent) {
        ArrayList<ItemDetails> details = new ArrayList<>();
        for (CartTM tm : cartObList
        ) {
            details.add(
                    new ItemDetails(
                            tm.getItemCode(),
                            tm.getQty(), tm.getUnitPrice()
                    )
            );
        }

        Order order = new Order(
                txtOId.getText(),
                txtDate.getText(),
                cmbCustomerId.getValue(),
                Double.parseDouble(txtTotal.getText().split(" /=")[0]),
                details

        );

        if (DataBase.orderList.add(order)) {
            new Alert(Alert.AlertType.CONFIRMATION, " Placed.").show();
            cartObList.clear();
            calculateTotalCost();
        }


    }
}
