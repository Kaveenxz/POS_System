package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import dto.CustomerDto;
import dto.tm.CustomerTm;
import model.CustomerModel;
import model.impl.CustomerModelImpl;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class CustomerFormController {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtSalary;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colOption;

    private CustomerModel customerModel= new CustomerModelImpl();

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadCustomerTable();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(CustomerTm newValue) {
       if(newValue != null){
           txtId.setEditable(false);
           txtId.setText(newValue.getId());
           txtName.setText(newValue.getName());
           txtAddress.setText(newValue.getAddress());
           txtSalary.setText(String.valueOf(newValue.getSalary()));
       }
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();


        try {
           List<CustomerDto> dtoList = customerModel.allCustomer();

           for (CustomerDto dto : dtoList){
               Button btn = new Button("Delete");

               CustomerTm c = new CustomerTm(
                       dto.getId(),
                       dto.getName(),
                       dto.getAddress(),
                       dto.getSalary(),
                       btn
               );
               btn.setOnAction(actionEvent -> {
                   deleteCustomer(c.getId());
               });
               tmList.add(c);
           }
           tblCustomer.setItems(tmList);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCustomer(String id) {
        try {
           boolean isDeleted = customerModel.deleteCustomer(id);

            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted").show();
                loadCustomerTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something When Wrong").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        Stage stage = (Stage)tblCustomer.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void reloadButtonOnAction(ActionEvent event) {
        loadCustomerTable();
        tblCustomer.refresh();
        clearFields();
    }

    private void clearFields() {
        tblCustomer.refresh();
        txtId.setEditable(true);
        txtSalary.clear();
        txtAddress.clear();
        txtId.clear();
        txtName.clear();
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            boolean isSaved = customerModel.saveCustomer(
                    new CustomerDto(txtId.getText(),
                            txtName.getText(),
                            txtAddress.getText(),
                            Double.parseDouble(txtSalary.getText())
                    )
            );
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                loadCustomerTable();
                clearFields();
            }
        }catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entity").show();
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        try {
            boolean isUpdated = customerModel.updateCustomer(new CustomerDto(txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())
            ));
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Customer Updated!").show();
                loadCustomerTable();
                clearFields();
            }
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
