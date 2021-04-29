package lk.ijse.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.pos.db.DataBase;
import lk.ijse.pos.model.ItemDetails;
import lk.ijse.pos.model.Order;

public class OrderDetailFormController {
    public TableView<ItemDetails> tblOrderDetail;
    public TableColumn colItemCode;
    public TableColumn colQTY;
    public TableColumn colUnitPrice;

    public void initialize() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }

    public void setData(String orderId) {
        for (Order o : DataBase.orderList
        ) {
            if (o.getOrderId().equals(orderId)) {
                ObservableList<ItemDetails> detailsObList =
                        FXCollections.observableArrayList(o.getItems());
                tblOrderDetail.setItems(detailsObList);
                return;
            }
        }
    }

}
