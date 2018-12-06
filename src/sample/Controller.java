package sample;

import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.Date;

public class Controller {

    Stage primaryStage;
    Dao dao;
    View view;

    public Controller(Stage primaryStage) {
        this.primaryStage = primaryStage;
        dao = new Dao();
        view = new View(primaryStage, this);
        view.updateTableView();
    }

    //Takes new informations by button event, gives it to model class Dao to insert and updates TV
    public void enterData(int employeeID, int invetoryID, String expectedReturn) {
        dao.enterData(employeeID,invetoryID, new Date(), dao.parsingStringDate(expectedReturn));
        view.updateTableView();
    }

    //Takes new information about returned and updates TV
    public void updateReturned(int id) {
        dao.updateReturned(id);
        view.updateTableView();
    }

    public Date getDate(String s) {
        return dao.parsingStringDate(s);
    }

    public ObservableList getTableData() {
        return dao.getTableData();
    }

    public void insertTestData() {
        dao.insertTestData();
        view.updateTableView();
    }
}


