package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Table.TableViewForDB;
import sample.User.User;
import sample.DB.OracleDB;

import java.util.Optional;

public class Controller {

    @FXML
    private Button EnterToApp;

    @FXML
    private PasswordField password_enter;

    @FXML
    private TextField login_enter;


    @FXML
    public void EnterToAccount(ActionEvent event) throws Exception{
        OracleDB oracleDB = OracleDB.getDataBaseIra();
        String result = oracleDB.getResultCheckPasswordLogin(login_enter.getText(),password_enter.getText());
        if (!"not".equals(result))
        {
            Stage stage1 = (Stage) EnterToApp.getScene().getWindow();
            stage1.close();

            User.getUser(result,login_enter.getText(),password_enter.getText());

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FormsApp/MainDateBase.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Форма отображения данных");
            stage.setScene(new Scene(root1));
            stage.show();

        }
        else {
            Alert errorAlertEnterToApp = new Alert(Alert.AlertType.ERROR);
            errorAlertEnterToApp.setTitle("");
            errorAlertEnterToApp .setHeaderText("Ошибка: невозможно зайти в приложение");
            errorAlertEnterToApp .setContentText("Неправильно введён логин или пароль");
            errorAlertEnterToApp .showAndWait();
        }
    }

    @FXML
    public void ExitApp(ActionEvent event)
    {
        Alert errorAlertEnterToApp = new Alert(Alert.AlertType.CONFIRMATION);
        errorAlertEnterToApp.setTitle("");
        errorAlertEnterToApp .setHeaderText("Выйти из программы");
        errorAlertEnterToApp .setContentText("Вы действительно хотите выйти из программы ?");
        Optional<ButtonType> result = errorAlertEnterToApp .showAndWait();
        if (result.get() == ButtonType.OK)
        {
            Stage stage = (Stage) EnterToApp.getScene().getWindow();
            stage.close();
        }

    }


    @FXML
    public void initialize() {
    }




}
