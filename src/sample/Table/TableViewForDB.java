package sample.Table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import sample.Access.AccessTables;
import sample.DB.OracleDB;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TableViewForDB {

    //Наименование колонок на русском
    final private String[][] RateTableRus = {
            {"ID Анализа","ID Пациента","Дата","Наименование","Ссылка на файл"},
            {"ID Время приёма","ID Специалиста","Дата","Время","Статус"},
            {"ID Должности","Наименование","Оклад"},
            {"ID Время приёма","ID Пациента","ID Услуги","Статус","Ссылка на ZOOM"},{"ID Отзыва","ID Специалиста","Дата","Описание"},
            {"ID Пациента","ФИО","Телефон","Почта","Возраст"},{"ID Рекомендации","ID Специалиста","ID Пациента","Дата","Текст"},
            {"ID Специалиста","ID Должности","ID Специальности","ФИО","Телефон","Почта","Образование"},
            {"ID","Наименование"},{"ID Услуги","ID Специальности","ID Должности"," Тип приёма"," Стоимость"}
    };


    private ObservableList<ObservableList> data;

    private int CountColumnTable;

    final private static String DATE_FORMAT = "yyyy-MM-dd";


    private AccessTables.ADMINISTRATION accessAdministration;
    private AccessTables.ACCOUNTANT accessAccountant;
    private AccessTables.DOCTOR accessDoctor;



    public ObservableList<ObservableList> QueryTable(String SQL_Query, TableView TableData, int j, TextField[] TextInputTable, Statement cursorDataBaseIra) throws Exception
    {
        data = FXCollections.observableArrayList();
        ResultSet rs = cursorDataBaseIra.executeQuery(SQL_Query);

        AddColumnTable(rs,TableData,j,TextInputTable);
        AddRowsTable(rs,TableData);

        return data;
    }


    private void AddColumnTable(ResultSet rs,TableView TableData,int j,TextField[] TextInputTable) throws Exception
    {
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++)
        {
            final int k = i;
            TableColumn col = new TableColumn(RateTableRus[j][i]);
            setEventColumnTable(col,k);
            TextInputTable[i].setPromptText(col.getText());
            TextInputTable[i].setDisable(false);
            TableData.getColumns().addAll(col);
        }
        setColumnTable(rs.getMetaData().getColumnCount());

    }

    private void setColumnTable(int value)
    {
        CountColumnTable = value;
    }

    private void setEventColumnTable(TableColumn col, int k)
    {

        col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>()
        {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(k).toString());
            }
        });

        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                data.get(event.getTablePosition().getRow()).set(event.getTablePosition().getColumn(),event.getNewValue());
            }
        });

    }

    private void AddRowsTable(ResultSet rs,TableView TableData) throws Exception
    {
        while (rs.next())
        {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount();i++)
            {
                if (isDateValid(rs.getString(i)))
                {
                    DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    java.util.Date test = dateFormat.parse(rs.getString(i));
                    row.add(dateFormat.format(test));
                }
                else
                {
                    row.add(rs.getString(i));
                }
            }
            data.add(row);
        }
    }

    public boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.parse(date.toString());
            return true;
        } catch (ParseException e) {

            return false;
        }
    }

    public boolean isInt(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }


    public AccessTables.ADMINISTRATION getAccessAdministration() {
        return accessAdministration;
    }

    public AccessTables.ACCOUNTANT getAccessAccountant() {
        return accessAccountant;
    }

    public AccessTables.DOCTOR getAccessDoctor() {
        return accessDoctor;
    }

    public void DisplayDataToTableDoctor(String choiceTable, OracleDB oracleDB, TableView tableView, TextField[] TextInputs) throws Exception {
        switch (choiceTable){

            case "Анализ":
                accessDoctor = AccessTables.DOCTOR.ANALYSIS;
                tableView.getItems().setAll(
                        QueryTable(accessDoctor.getQuerySQLSelectNow(),tableView,0,TextInputs,oracleDB.getCursorDataBaseIra()));
                break;

            case "Врёмя приёма":
                accessDoctor = AccessTables.DOCTOR.TIME_OF_RECEIPT;
                tableView.getItems().setAll(
                        QueryTable(accessDoctor.getQuerySQLSelectNow(),tableView,1,TextInputs,oracleDB.getCursorDataBaseIra()));
                break;


            case "Пациент":
                accessDoctor = AccessTables.DOCTOR.A_PATIENT;
                tableView.getItems().setAll(
                        QueryTable(accessDoctor.getQuerySQLSelectNow(),tableView,5,TextInputs,oracleDB.getCursorDataBaseIra()));
                break;

            case "Рекомендаций":
                accessDoctor = AccessTables.DOCTOR.RECOMMENDATIONS;
                tableView.getItems().setAll(
                        QueryTable(accessDoctor.getQuerySQLSelectNow(),tableView,6,TextInputs,oracleDB.getCursorDataBaseIra()));
                break;

            default:
                ErrorMessage();
                break;
        }
    }

    public void DisplayDataToTableAdministration(String choiceTable, OracleDB oracleDB,TableView tableView,TextField[] TextInputs) throws Exception{
        switch (choiceTable){

            case "Врёмя приёма":
                accessAdministration = AccessTables.ADMINISTRATION.TIME_OF_RECEIPT;
                tableView.getItems().setAll(
                        QueryTable(accessDoctor.getQuerySQLSelectNow(),tableView,1,TextInputs,oracleDB.getCursorDataBaseIra()));
                break;

            case "Консультация":
                accessAdministration = AccessTables.ADMINISTRATION.CONSULTATION;
                tableView.getItems().setAll(
                        QueryTable(accessDoctor.getQuerySQLSelectNow(),tableView,3,TextInputs,oracleDB.getCursorDataBaseIra()));
                break;


            case "Отзывы":
                accessAdministration = AccessTables.ADMINISTRATION.REVIEWS;
                tableView.getItems().setAll(
                        QueryTable(accessDoctor.getQuerySQLSelectNow(),tableView,4,TextInputs,oracleDB.getCursorDataBaseIra()));
                break;


            case "Пациент":
                accessAdministration = AccessTables.ADMINISTRATION.A_PATIENT;
                tableView.getItems().setAll(
                        QueryTable(accessDoctor.getQuerySQLSelectNow(),tableView,5,TextInputs,oracleDB.getCursorDataBaseIra()));
                break;

            default:
                ErrorMessage();
                break;

        }


    }

    public void DisplayDataToTableAccountant (String choiceTable, OracleDB oracleDB,TableView tableView,TextField[] TextInputs) throws Exception{
        switch (choiceTable){

            case "Должность":
                accessAccountant = AccessTables.ACCOUNTANT.JOB_TITLE;
                tableView.getItems().setAll(
                        QueryTable(accessAccountant.getQuerySQLSelectNow(),tableView,2,TextInputs,oracleDB.getCursorDataBaseIra()));
                break;

            case "Специалист":
                accessAccountant = AccessTables.ACCOUNTANT.SPECIALIST;
                tableView.getItems().setAll(
                        QueryTable(accessAccountant.getQuerySQLSelectNow(),tableView,7,TextInputs,oracleDB.getCursorDataBaseIra()));
                break;

            case "Специальность":
                accessAccountant = AccessTables.ACCOUNTANT.SPECIALITY;
                tableView.getItems().setAll(
                        QueryTable(accessAccountant.getQuerySQLSelectNow(),tableView,8,TextInputs,oracleDB.getCursorDataBaseIra()));
                break;

            case "Услуги":
                accessAccountant = AccessTables.ACCOUNTANT.SERVICES;
                tableView.getItems().setAll(
                        QueryTable(accessAccountant.getQuerySQLSelectNow(),tableView,9,TextInputs,oracleDB.getCursorDataBaseIra()));
                break;

            default:
                ErrorMessage();
                break;
        }
    }



    private void ErrorMessage()
    {
        Alert errorAlertEnterToApp = new Alert(Alert.AlertType.ERROR);
        errorAlertEnterToApp.setTitle("");
        errorAlertEnterToApp .setHeaderText("Ошибка: невозможно просмотреть таблицу ");
        errorAlertEnterToApp .setContentText("Возможность посмотреть данную таблицу нельзя, так как требуется другой уровень доступа ");
        errorAlertEnterToApp .showAndWait();
    }

    public void ErrorMessage(String header,String content)
    {
        Alert errorAlertEnterToApp = new Alert(Alert.AlertType.ERROR);
        errorAlertEnterToApp.setTitle("");
        errorAlertEnterToApp .setHeaderText(header);
        errorAlertEnterToApp .setContentText(content);
        errorAlertEnterToApp .showAndWait();
    }


    public void MessageAllDoneSQL(String HeaderText)
    {
        Alert errorAlertEnterToApp = new Alert(Alert.AlertType.INFORMATION);
        errorAlertEnterToApp.setTitle("");
        errorAlertEnterToApp .setHeaderText(HeaderText);
        errorAlertEnterToApp .showAndWait();
    }


}
