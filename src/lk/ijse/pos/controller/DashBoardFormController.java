package lk.ijse.pos.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DashBoardFormController {

    public AnchorPane contextOfDashBoard;

    public void openCustomerForm(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) contextOfDashBoard.
                getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass()
                        .getResource
                                ("../views/CustomerForm.fxml"))));
    }
}
