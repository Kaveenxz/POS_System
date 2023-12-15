package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {
    public AnchorPane pane;
    public Label lblTime;

    public  void initialize(){
        calculateTime();
    }

    private void calculateTime() {
        Timeline timeline = new Timeline(new KeyFrame(
                javafx.util.Duration.ZERO,
                actionEvent -> lblTime.setText((LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))))
        ),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void customerBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"))));
            stage.setTitle("Customer Form");
            stage.getIcons().add(new Image("images/customer.png"));            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void itemButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemForm.fxml"))));
            stage.setTitle("Item Form");
            stage.getIcons().add(new Image("images/customer.png"));            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void placeButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceOrderForm.fxml"))));
            stage.setTitle("Place Order Form");
            stage.getIcons().add(new Image("images/customer.png"));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
