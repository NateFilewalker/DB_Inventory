package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DataRow {

    SimpleIntegerProperty id;
    SimpleIntegerProperty employeeID;
    SimpleIntegerProperty inventoryID;

    SimpleStringProperty deviceName;
    SimpleStringProperty employeeName;
    SimpleStringProperty borrowDate;
    SimpleStringProperty expectedReturn;
    SimpleStringProperty returned;

    public DataRow(int id, int employeeID,
                   int inventoryID, String deviceName,
                   String employeeName, String borrowDate,
                   String expectedReturn, String returned) {

        this.id = new SimpleIntegerProperty(id);
        this.employeeID = new SimpleIntegerProperty(employeeID);
        this.inventoryID = new SimpleIntegerProperty(inventoryID);
        this.deviceName = new SimpleStringProperty(deviceName);
        this.employeeName = new SimpleStringProperty(employeeName);
        this.borrowDate = new SimpleStringProperty(borrowDate);
        this.expectedReturn = new SimpleStringProperty(expectedReturn);
        this.returned = new SimpleStringProperty(returned);


    }

    public int getid() {
        return id.get();
    }


    // get methods: variable names has to be written in lower case (otherwise error)

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public int getemployeeID() {
        return employeeID.get();
    }

    public SimpleIntegerProperty employeeIDProperty() {
        return employeeID;
    }

    public int getinventoryID() {
        return inventoryID.get();
    }

    public SimpleIntegerProperty inventoryIDProperty() {
        return inventoryID;
    }

    public String getdeviceName() {
        return deviceName.get();
    }

    public SimpleStringProperty deviceNameProperty() {
        return deviceName;
    }

    public String getemployeeName() {
        return employeeName.get();
    }

    public SimpleStringProperty employeeNameProperty() {
        return employeeName;
    }

    public String getborrowDate() {
        return borrowDate.get();
    }

    public SimpleStringProperty borrowDateProperty() {
        return borrowDate;
    }

    public String getexpectedReturn() {
        return expectedReturn.get();
    }

    public SimpleStringProperty expectedReturnProperty() {
        return expectedReturn;
    }

    public String getreturned() {
        return returned.get();
    }

    public SimpleStringProperty returnedProperty() {
        return returned;
    }
}
