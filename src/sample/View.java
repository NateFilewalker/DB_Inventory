package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class View {
    TableView tableView;
    Controller controller;

    public View(Stage primaryStage, Controller controller) {

        this.controller = controller;

        tableView = new TableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label employeeLabel = new Label("Employee ID:");
        TextField employeeTextField = new TextField();

        Label inventoryIDLabel = new Label("Inventory ID");
        TextField inventoryIDTextField = new TextField();

        Label expectedReturnLabel = new Label("Expected return:");
        TextField expectedReturnTextField = new TextField();
        expectedReturnTextField.setText("yyyy-MM-dd");

        Button addButton = new Button("Borrow ");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.enterData(Integer.parseInt(employeeTextField.getText()),
                        Integer.parseInt(inventoryIDTextField.getText()),
                        expectedReturnTextField.getText());
            }
        });


        //Stackoverflow solution for clicking on a row to start an alert
        tableView.setRowFactory( tmpV -> {
            TableRow<DataRow> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    DataRow rowData = row.getItem();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Return conformation");
                    alert.setHeaderText("Confirm the return of the item borrowed on " + rowData.getborrowDate() + "?");
                    alert.setContentText("ID: " + rowData.getid() + "\n" + "Inventory ID: " +rowData.getinventoryID() +
                            "\n" + "Inventory: " + rowData.getdeviceName());

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        controller.updateReturned(rowData.getid());
                    }
                }
            });
            return row ;
        });

        Button testButton = new Button("Insert Testdata");
        testButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.insertTestData();
            }
        });

        /* My mistake:
        setCellValueFactory NOT "setCellFactory";
        Reason why setCellFactory doesn't work:
        setCellFactory = more likely for custom implementation (desgin) and not to fill column with values
        */
        TableColumn colID = new TableColumn("ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn colEmployeeID = new TableColumn("Employee ID");
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        TableColumn colEmployeeName = new TableColumn("Employee");
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        TableColumn colInventoryID = new TableColumn("Inventory ID");
        colInventoryID.setCellValueFactory(new PropertyValueFactory<>("inventoryID"));
        TableColumn colDeviceName = new TableColumn("Device");
        colDeviceName.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        TableColumn colBorrowDate = new TableColumn("Borrow Date");
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        TableColumn colExpectedReturn = new TableColumn("Expected return");
        colExpectedReturn.setCellValueFactory(new PropertyValueFactory<>("expectedReturn"));
        TableColumn colReturned = new TableColumn("Returned");
        colReturned.setCellValueFactory(new PropertyValueFactory<>("returned"));

        tableView.getColumns().setAll(colID, colEmployeeID, colEmployeeName, colInventoryID, colDeviceName,
                colBorrowDate, colExpectedReturn, colReturned);

        ToolBar toolBar = new ToolBar(employeeLabel, employeeTextField, new Separator(), inventoryIDLabel,
                inventoryIDTextField, new Separator(), expectedReturnLabel, expectedReturnTextField,
                addButton, testButton);

        VBox vBox = new VBox(toolBar,tableView);
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateTableView() {
        tableView.setItems(controller.getTableData());
    }
}
