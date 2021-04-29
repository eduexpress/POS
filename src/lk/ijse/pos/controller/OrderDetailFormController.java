package lk.ijse.pos.controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lk.ijse.pos.model.ItemDetails;

public class OrderDetailFormController {
    public TableView<ItemDetails> tblOrderDetail;
    public TableColumn colItemCode;
    public TableColumn colQTY;
    public TableColumn colUnitPrice;

    public void setData(String orderId) {
        System.out.println(orderId);
    }

}
