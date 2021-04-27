package lk.ijse.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.db.DataBase;
import lk.ijse.pos.model.Item;

import java.io.IOException;

public class ItemFormController {
    public AnchorPane contextOfItemForm;
    public TextField txtItemCode;
    public TextField txtQTYOnHand;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public Button btnSaveButton;

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
