package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dao {

    //set file
    private static String filename = "inventory.db";

    public Dao() {
        createInventory();
    }

    private Connection connect() throws SQLException, ClassNotFoundException {

        //Instructions by worksheet in lesson

        String driver = "org.sqlite.JDBC";
        Class.forName(driver);

        String jdbc = "jdbc:sqlite";
        String url = jdbc + ":" + filename;
        return DriverManager.getConnection(url);
    }

    private void createInventory() {

        // sql querys separately and not in addition because of the new version of sqlite driver? (bug?)
        // autoincrement to count up id numbers instead of generating random

        String sql = "CREATE TABLE IF NOT EXISTS employee (employeeID INTEGER primary key autoincrement, employeeName TEXT);";
        String sql2 = "CREATE TABLE IF NOT EXISTS device (inventoryID INTEGER primary key autoincrement, deviceName TEXT);";
        String sql3 = "CREATE TABLE IF NOT EXISTS borrow (id INTEGER primary key autoincrement, borrowDate TEXT, " +
                "expectedReturn TEXT, returned TEXT, inventoryID INTEGER, employeeid INTEGER);";

        try {
            connect().createStatement().execute(sql2);
            connect().createStatement().execute(sql3);
            connect().createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Inserts 3 employees and 3 devices
    public void insertTestData() {
        String sql = "INSERT INTO employee(employeeName) VALUES ('Mueller');";
        String sql2 = "INSERT INTO employee(employeeName) VALUES ('Schmidt');";
        String sql3 = "INSERT INTO employee(employeeName) VALUES ('Schueler');";
        String sql4 = "INSERT INTO device(deviceName) VALUES ('Projektor');";
        String sql5 = "INSERT INTO device(deviceName) VALUES ('Presenter');";
        String sql6 = "INSERT INTO device(deviceName) VALUES ('Kamera');";

        //Borrow Testdata
        enterData(1, 1, new Date(), new Date());
        enterData(3, 1, new Date(), new Date());
        enterData(2, 2, new Date(), new Date());
        enterData(3, 3, new Date(), new Date());

        try {
            connect().createStatement().execute(sql);
            connect().createStatement().execute(sql2);
            connect().createStatement().execute(sql3);
            connect().createStatement().execute(sql4);
            connect().createStatement().execute(sql5);
            connect().createStatement().execute(sql6);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void enterData(int employeeID, int inventoryID, Date borrowDate, Date expectedReturn) {
        String sql = "INSERT INTO borrow(id, employeeID, inventoryID, borrowDate, expectedReturn, returned)" +
                "VALUES (NULL, ?, ?, ?, ?, NULL);";

        /*questionmarks as placeholders and got set at preparedStatement before execution below
        Provides sql injections, is more comfortable (no need to prepare statement everytime)
        and results better performance*/

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setInt(1, employeeID);
            preparedStatement.setInt(2, inventoryID);
            preparedStatement.setString(3, parsingDate(borrowDate));
            preparedStatement.setString(4, parsingDate(expectedReturn));

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // From Date to String
    public String parsingDate(Date date) {
        String d = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(d);

        return dateFormat.format(date);
    }

    // From String to Date
    public Date parsingStringDate(String string) {
        String d = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(d);

        try {
            return dateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Updates returned column by id with date
    public void updateReturned(int id) {

        String sql =
                "UPDATE borrow " +
                "SET returned = ? " +
                "WHERE id = ?;";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(1, parsingDate(new Date()));

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList getTableData() {
        String sql = "SELECT * FROM  borrow " +
                "INNER JOIN employee ON borrow.employeeID = employee.employeeID " +
                "INNER JOIN device ON borrow.inventoryID = device.inventoryID;";

        try {
            PreparedStatement preparedStatement = connect().prepareStatement(sql);
            ResultSet cursor = preparedStatement.executeQuery();

            List list = new ArrayList();

            // Until it's trough whole query
            //Instruction by worksheet in lesson
            while (cursor.next()) {
                int id = cursor.getInt("id");
                String borrowDate = cursor.getString("borrowDate");
                String expectedReturn = cursor.getString("expectedReturn");
                String returned = cursor.getString("returned");
                int inventoryID = cursor.getInt("inventoryID");
                int employeeid = cursor.getInt("employeeid");
                String employeeName = cursor.getString("employeeName");
                String deviceName = cursor.getString("deviceName");

                DataRow dataRow = new DataRow(id, employeeid, inventoryID, deviceName, employeeName, borrowDate,
                        expectedReturn, returned);

                list.add(dataRow);
            }
            return FXCollections.observableList(list);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
