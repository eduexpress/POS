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
import lk.ijse.pos.model.Item;
import lk.ijse.pos.views.tm.ItemTM;

import java.io.IOException;

public class ItemFormController {
    public AnchorPane contextOfItemForm;
    public TextField txtItemCode;
    public TextField txtQTYOnHand;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public Button btnSaveButton;
    public TableView<ItemTM> tblItems;
    public TableColumn colItemCode;
    public TableColumn colItemDescription;
    public TableColumn colItemUnitPrice;
    public TableColumn colItemQTY;
    public TableColumn colItemOperate;

    ObservableList<ItemTM> obList = FXCollections.observableArrayList();

    public void initialize() {

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colItemQTY.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colItemOperate.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadItems("");
    }

    private void loadItems(String searchText) {
        for (Item i : DataBase.itemList
        ) {

            Button btn = new Button("Delete");

            if (i.getId().contains(searchText) || i.getDescription().contains(searchText)) {
                obList.add(new ItemTM(i.getId(), i.getDescription(), i.getQtyOnHand(), i.getUnitPrice(), btn));
            }
        }
        tblItems.setItems(obList);

    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) contextOfItemForm.
                getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass()
                        .getResource
                                ("../views/DashBoardForm.fxml"))));
    }

    public void newItemOnAction(ActionEvent actionEvent) {
    }

    public void saveItemOnAction(ActionEvent actionEvent) {

        Item item1 = new Item(
                txtItemCode.getText(), txtDescription.getText(),
                Integer.parseInt(txtQTYOnHand.getText()),
                Double.parseDouble(txtUnitPrice.getText())
        );

        if (btnSaveButton.getText().equals("Save Item")) {
            // save
            if (DataBase.itemList.add(item1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved", ButtonType.OK)
                        .show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.CLOSE)
                        .show();
            }
        } else {
            //update
        }

    }
}
