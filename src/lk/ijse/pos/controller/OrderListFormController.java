package lk.ijse.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.db.DataBase;
import lk.ijse.pos.model.Order;
import lk.ijse.pos.views.tm.OrderTM;

import java.io.IOException;

public class OrderListFormController {
    public AnchorPane contextOfOrderList;
    public TableView<OrderTM> tblOrdersId;
    public TableColumn colOrderId;
    public TableColumn colOrderDate;
    public TableColumn colCustomer;
    public TableColumn colTotalCost;

    public void initialize() {

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadAllOrders();
    }

    private void loadAllOrders() {
        ObservableList<OrderTM> orderObList = FXCollections.observableArrayList();
        for (Order o : DataBase.orderList
        ) {
            orderObList.add(
                    new OrderTM(o.getOrderId(),
                            o.getOrderDate(),
                            o.getCustomerId(),
                            o.getTotalCost())
            );
        }
        tblOrdersId.setItems(orderObList);
    }

    public void backToHome(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) contextOfOrderList.
                getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass()
                        .getResource
                                ("../views/DashBoardForm.fxml"))));
    }
}
