package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.Table.TableViewForDB;
import sample.User.User;
import sample.DB.OracleDB;

import java.util.ArrayList;


public class ControllerMain {



    @FXML
    private Button  ButtUpdate;

    @FXML
    private Button  ButtAdd;

    @FXML
    private Button  ButtDel;

    @FXML
    private ComboBox<String> table_access;

    @FXML
    private TableView TableData;

    @FXML
    private ScrollPane ScrollTable;

    @FXML
    private TextField TextInputOne,TextInputTwo,TextInputThree,TextInputFour,TextInputFive,TextInputSix,TextInputSeven,TextInputEight;

    @FXML
    private TextField[] TextInputs =  {TextInputOne,TextInputTwo,TextInputThree,TextInputFour,TextInputFive,TextInputSix,TextInputSeven,TextInputEight};


    private OracleDB oracleDB;

    private User user = User.getUser();

    private TableViewForDB tableViewForDB = new TableViewForDB();


    @FXML
    public void initialize() {
        TableData.setEditable(false);
        table_access.setItems(FXCollections.observableArrayList("Анализ" ,"Врёмя приёма" ,"Должность" ,"Консультация" ,"Отзывы" ,"Пациент" ,"Рекомендаций" ,"Специалист" ,"Специальность","Услуги"));
        SwitchDisableButton(true);
        ScrollTable.setFitToHeight(true);
        ScrollTable.setFitToWidth(true);
        TextInputs = new TextField[] {TextInputOne,TextInputTwo,TextInputThree,TextInputFour,TextInputFive,TextInputSix,TextInputSeven,TextInputEight};
        setEnable();
    }




    @FXML
    public void DeleteCellDatabase(ActionEvent event) throws Exception
    {
        switch (user.getAccess()){
            case "Doctor":
                PrepareDeleteData(tableViewForDB.getAccessDoctor().getQuerySQLDeleteNow(),tableViewForDB.getAccessDoctor().getColumn());
                tableViewForDB.DisplayDataToTableDoctor(table_access.getValue(),oracleDB,TableData,TextInputs);
                break;

            case "Administration":
                PrepareDeleteData(tableViewForDB.getAccessAdministration().getQuerySQLDeleteNow(),tableViewForDB.getAccessAdministration().getColumn());
                tableViewForDB.DisplayDataToTableDoctor(table_access.getValue(),oracleDB,TableData,TextInputs);
                break;

            case "Accountant":
                PrepareDeleteData(tableViewForDB.getAccessAccountant().getQuerySQLDeleteNow(),tableViewForDB.getAccessAccountant().getColumn());
                tableViewForDB.DisplayDataToTableDoctor(table_access.getValue(),oracleDB,TableData,TextInputs);
                break;
        }
    }


    @FXML
    public void AddCellDataBase(ActionEvent event) throws Exception
    {
        ArrayList<String> dataNeedAdd = new ArrayList<>();
        for (int i = 0; i < TextInputs.length;i++){
            if (!TextInputs[i].isDisable()){
                dataNeedAdd.add(TextInputs[i].getText());
            }
        }
        switch (user.getAccess()){
            case "Doctor":
                PrepareAddData(tableViewForDB.getAccessDoctor().getQuerySQLAddNow(),dataNeedAdd);
                tableViewForDB.DisplayDataToTableDoctor(table_access.getValue(),oracleDB,TableData,TextInputs);
                break;

            case "Administration":
                PrepareAddData(tableViewForDB.getAccessAdministration().getQuerySQLAddNow(),dataNeedAdd);
                tableViewForDB.DisplayDataToTableDoctor(table_access.getValue(),oracleDB,TableData,TextInputs);
                break;

            case "Accountant":
                PrepareAddData(tableViewForDB.getAccessAccountant().getQuerySQLAddNow(),dataNeedAdd);
                tableViewForDB.DisplayDataToTableDoctor(table_access.getValue(),oracleDB,TableData,TextInputs);
                break;
        }
    }


    @FXML
    public void UpdateDataCell(ActionEvent event) throws Exception {
        ArrayList<String> dataNeedUpdate = new ArrayList<>();
        for (int i = 0; i < TextInputs.length;i++){
            if (!TextInputs[i].isDisable()){
                dataNeedUpdate.add(TextInputs[i].getText());
            }
        }

        switch (user.getAccess()){
            case "Doctor":
                PrepareUpdateData(tableViewForDB.getAccessDoctor().getQuerySQLUpdateNow(),dataNeedUpdate);
                tableViewForDB.DisplayDataToTableDoctor(table_access.getValue(),oracleDB,TableData,TextInputs);
                break;

            case "Administration":
                PrepareUpdateData(tableViewForDB.getAccessAdministration().getQuerySQLUpdateNow(),dataNeedUpdate);
                tableViewForDB.DisplayDataToTableAdministration(table_access.getValue(),oracleDB,TableData,TextInputs);
                break;

            case "Accountant":
                PrepareUpdateData(tableViewForDB.getAccessAccountant().getQuerySQLUpdateNow(),dataNeedUpdate);
                tableViewForDB.DisplayDataToTableAccountant(table_access.getValue(),oracleDB,TableData,TextInputs);
                break;
        }
    }


    @FXML
    public void ViewDetails(MouseEvent mouseEvent){
        ObservableList<String> array = (ObservableList) TableData.getSelectionModel().getSelectedItem();
        for (int i = 0 ; i < array.size();i++){
            TextInputs[i].setText(array.get(i).toString());
        }
    }

    @FXML
    public void SwitchComboBox() throws Exception
    {
        setEnable();
        ClearTextInput();
        ClearTableAll();
        SwitchDisableButton(true);
        UpdateTable(table_access.getValue());
    }




    private void PrepareUpdateData(String sqlUpdate,ArrayList<String> dataNeedUpdate){

        if (!sqlUpdate.isEmpty() && checkEmptyInputs()){
            try {
                if (oracleDB.getResultUpdateDate(sqlUpdate,dataNeedUpdate,tableViewForDB)){
                    tableViewForDB.MessageAllDoneSQL("Данные изменены");
                }
            } catch (Exception ex){
                tableViewForDB.ErrorMessage("Ошибка: невозможно обновить данные ","Возможно данные введены неправильно, пожалуйста введите снова");
            }
        }
        else {
            tableViewForDB.ErrorMessage("Ошибка: невозможно обновить данную запись","Вашего уровня доступа недостаточно для обновления записи");
        }
        ClearTableAll();
    }

    private void PrepareAddData(String sqlAdd,ArrayList<String> dataNeedAdd){

        if (!sqlAdd.isEmpty() && checkEmptyInputs()){
            try {
                if (oracleDB.getResultAddDate(sqlAdd,dataNeedAdd,tableViewForDB)){
                    tableViewForDB.MessageAllDoneSQL("Данные добавлены");
                }
            } catch (Exception ex){
                System.out.println(ex);
                tableViewForDB.ErrorMessage("Ошибка: невозможно добавить данные ","Возможно данные введены неправильно, пожалуйста введите снова");
            }
        }else {
            tableViewForDB.ErrorMessage("Ошибка: невозможно добавить данную запись","Вашего уровня доступа недостаточно для добавления записи");
        }
        ClearTableAll();
    }

    private void PrepareDeleteData(String sqlDelete,int column){
        if (!sqlDelete.isEmpty() && checkEmptyInputs()){
            try {
                ArrayList<String> dataNeedDelete = new ArrayList<>();
                if (column == 3){
                    for (int i = 0; i < 3;i++){
                        if (!TextInputs[i].isDisable()){
                            dataNeedDelete.add(TextInputs[i].getText());
                        }
                    }
                }
                else {
                    for (int i = 0; i < 1;i++){
                        if (!TextInputs[i].isDisable()){
                            dataNeedDelete.add(TextInputs[i].getText());
                        }
                    }
                }

                if (oracleDB.getResultDeleteDate(sqlDelete,dataNeedDelete,tableViewForDB)){
                    tableViewForDB.MessageAllDoneSQL("Данные удалены");
                }


            } catch (Exception ex){
                System.out.println(ex);
                tableViewForDB.ErrorMessage("Ошибка: невозможно удалить данные ","Возможно данные введены неправильно, пожалуйста введите значения снова");
            }
        }
        else {
            tableViewForDB.ErrorMessage("Ошибка: невозможно удалить данную запись","Вашего уровня доступа недостаточно для удаления записи");
        }
        ClearTableAll();

    }


    private boolean checkEmptyInputs(){
        ArrayList<TextField> arr = new ArrayList<>();
        for (int i = 0; i < TextInputs.length;i++){
            if (!TextInputs[i].isDisable()){
                arr.add(TextInputs[i]);
            }
        }

        for (int i = 0; i < arr.size();i++){
            if (arr.get(i).getText().isEmpty()){
                return false;
            }
        }

        return true;
    }

    private void setEnable()
    {
        for (int i =0; i < TextInputs.length;i++)
        {
            TextInputs[i].setDisable(true);
        }
    }

    private void SwitchDisableButton(boolean res)
    {
        ButtAdd.setDisable(res);
        ButtDel.setDisable(res);
        ButtUpdate.setDisable(res);
    }

    private void ClearTableAll(){
        TableData.getColumns().clear();
        TableData.getItems().clear();
    }


    private void ClearTextInput()
    {
        for (int i =0; i < TextInputs.length;i++)
        {
            TextInputs[i].setPromptText("");
            TextInputs[i].setText("");
        }
    }


    private void UpdateTable(String selectValueSwitch) throws Exception{
        TableData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        oracleDB = OracleDB.getDataBaseIra();
        String access = user.getAccess();
        switch (access){
            case "Doctor":
                tableViewForDB.DisplayDataToTableDoctor(selectValueSwitch,oracleDB,TableData,TextInputs);
                break;

            case "Administration":
                tableViewForDB.DisplayDataToTableAdministration(selectValueSwitch,oracleDB,TableData,TextInputs);
                break;

            case "Accountant":
                tableViewForDB.DisplayDataToTableAccountant(selectValueSwitch,oracleDB,TableData,TextInputs);
                break;

        }
        ScrollTable.setContent(TableData);
        SwitchDisableButton(false);
    }





}
