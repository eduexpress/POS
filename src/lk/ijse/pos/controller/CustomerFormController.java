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
import lk.ijse.pos.views.tm.CustomerTM;

import java.io.IOException;

public class CustomerFormController {
    public AnchorPane contextOfCustomerForm;
    public TextField txtCId;
    public TextField txtCName;
    public TextField txtCSalary;
    public TextField txtCAddress;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colCusId;
    public TableColumn colCusName;
    public TableColumn colCusAddress;
    public TableColumn colCusSalary;
    public TableColumn colCusOperate;

    public void initialize(){
        colCusId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCusSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCusOperate.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadAllCustomers();

        //-----------------------------
        tblCustomer.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue)->{
                    System.out.println(newValue);
        } );
        //-----------------------------


    }

    private void loadAllCustomers() {
        ObservableList<CustomerTM> observableList
                = FXCollections.observableArrayList();
        for (Customer c:DataBase.customersList
             ) {

            Button btn= new Button("Delete");

            observableList.add(
              new CustomerTM(c.getId(),c.getName(),c.getAddress(),c.getSalary(),btn)
            );
        }
        tblCustomer.setItems(observableList);
    }

    public void backToHome(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) contextOfCustomerForm.
                getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass()
                        .getResource
                                ("../views/DashBoardForm.fxml"))));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        Customer customer1 = new Customer(
                txtCId.getText(),
                txtCName.getText(),
                txtCAddress.getText(),
                Double.parseDouble(txtCSalary.getText())
        );
        if (DataBase.customersList.add(customer1)){
            new Alert(Alert.AlertType.CONFIRMATION,
                    "Done", ButtonType.OK).show();
        }else{
            new Alert(Alert.AlertType.WARNING,
                    "Try Again.", ButtonType.CLOSE).show();
        }
    }
}
