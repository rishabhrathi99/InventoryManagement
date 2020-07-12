package invmanagement;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Update2Controller implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    private TextField productid;
    @FXML
    private TextField productdosage;
    @FXML
    private Button search;
    @FXML
    private TextField dosage;
    @FXML
    private TextField price;
    @FXML
    private TextField quantity;
    @FXML
    private Button updatequantity;
    @FXML
    private Button updatedosage;
    @FXML
    private Button updateprice;
    @FXML
    private Button clear;
    @FXML
    private Button back;
    @FXML
    private TableView<ContentModel> DataTable;
    @FXML
    private TableColumn<ContentModel, Integer> columnId;
    @FXML
    private TableColumn<ContentModel, String> columnDosage;
    @FXML
    private TableColumn<ContentModel, Integer> columnPrice;
    @FXML
    private TableColumn<ContentModel, Integer> columnAmount;
    @FXML
    private TableColumn<ContentModel, String> columnName;
    @FXML
    private TableColumn<ContentModel, String> columnManu;

    private ObservableList<ContentModel> data;
    private DbConnection db;
    @FXML
    private Label prompt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DbConnection();
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnManu.setCellValueFactory(new PropertyValueFactory<>("manu"));
    }

    @FXML
    private void searchEventHandle(ActionEvent event) {
        try {
            Connection con = db.Connect();
            data = FXCollections.observableArrayList();
            Statement stmt = con.createStatement();
            ResultSet rs = null;
            if (!productid.getText().isEmpty() && !this.productdosage.getText().isEmpty()) {
                rs = stmt.executeQuery("SELECT * FROM Product NATURAL JOIN Dosage where Pro_ID=" + Integer.parseInt(productid.getText()) + " and Dosage='" + this.productdosage.getText() + "';");
            } else if (!productid.getText().isEmpty() && productdosage.getText().isEmpty()) {
                rs = stmt.executeQuery("SELECT * FROM Product NATURAL JOIN Dosage where Pro_ID=" + Integer.parseInt(productid.getText()) + ";");
            } else if (productid.getText().isEmpty() && productdosage.getText().isEmpty()) {
                rs = stmt.executeQuery("SELECT * FROM Product NATURAL JOIN Dosage;");
            }
            while (rs.next()) {
                data.add(new ContentModel(rs.getInt("Pro_ID"), rs.getString("Dosage"), rs.getInt("Price"), rs.getInt("Amount"), rs.getString("Pro_Name"), rs.getString("Pro_manu")));
            }

            prompt.setText("Search Results...");

            DataTable.setItems(data);
        } catch (SQLException e) {
            prompt.setText("Invalid Input");
            System.out.println("Invalid Input");
        }

    }

    @FXML
    private void updateDosageEventHandle(ActionEvent event) {

        int id = Integer.parseInt(productid.getText());
        String dosage = this.productdosage.getText();
        String updDosage = this.dosage.getText();

        try {
            Connection con = db.Connect();
            data = FXCollections.observableArrayList();
            Statement stmt = con.createStatement();

            stmt.executeUpdate("UPDATE Dosage SET Dosage='" + updDosage + "' WHERE Pro_ID=" + id + " and Dosage='" + dosage + "';");

            ResultSet rs;

            rs = stmt.executeQuery("SELECT * FROM Product NATURAL JOIN Dosage where Pro_ID=" + id + " and Dosage='" + updDosage + "';");
            while (rs.next()) {
                data.add(new ContentModel(rs.getInt("Pro_ID"), rs.getString("Dosage"), rs.getInt("Price"), rs.getInt("Amount"), rs.getString("Pro_Name"), rs.getString("Pro_manu")));
            }

            prompt.setText("Updated Successfully...");

            DataTable.setItems(data);
        } catch (SQLException e) {
            System.out.println("Invalid Input");
            prompt.setText("Invalid input");
        }

    }

    @FXML
    private void updatePriceEventHandle(ActionEvent event) {
        int id = Integer.parseInt(productid.getText());
        String dosage = this.productdosage.getText();
        String updPrice = this.price.getText();

        try {
            Connection con = db.Connect();
            data = FXCollections.observableArrayList();
            Statement stmt = con.createStatement();

            stmt.executeUpdate("UPDATE Dosage SET Price='" + updPrice + "' WHERE Pro_ID=" + id + " and Dosage='" + dosage + "';");

            ResultSet rs;

            rs = stmt.executeQuery("SELECT * FROM Product NATURAL JOIN Dosage where Pro_ID=" + id + " and Dosage='" + dosage + "';");
            while (rs.next()) {
                data.add(new ContentModel(rs.getInt("Pro_ID"), rs.getString("Dosage"), rs.getInt("Price"), rs.getInt("Amount"), rs.getString("Pro_Name"), rs.getString("Pro_manu")));
            }

            prompt.setText("Updated Successfully...");

            DataTable.setItems(data);
        } catch (SQLException e) {
            System.out.println("Invalid Input");
            prompt.setText("Invalid input");
        }
    }

    @FXML
    private void updateQuantityEventHandle(ActionEvent event) {
        int id = Integer.parseInt(productid.getText());
        String dosage = this.productdosage.getText();
        String updAmount = this.quantity.getText();

        try {
            Connection con = db.Connect();
            data = FXCollections.observableArrayList();
            Statement stmt = con.createStatement();

            stmt.executeUpdate("UPDATE Dosage SET Amount=Amount+" + updAmount + " WHERE Pro_ID=" + id + " and Dosage='" + dosage + "';");

            ResultSet rs;

            rs = stmt.executeQuery("SELECT * FROM Product NATURAL JOIN Dosage where Pro_ID=" + id + " and Dosage='" + dosage + "';");
            while (rs.next()) {
                data.add(new ContentModel(rs.getInt("Pro_ID"), rs.getString("Dosage"), rs.getInt("Price"), rs.getInt("Amount"), rs.getString("Pro_Name"), rs.getString("Pro_manu")));
            }

            prompt.setText("Updated Successfully...");

            DataTable.setItems(data);
        } catch (SQLException e) {
            System.out.println("Invalid Input");
            prompt.setText("Invalid input");
        }
    }

    @FXML
    private void clearEventHandle(ActionEvent event) {
        productid.clear();
        productdosage.clear();
        dosage.clear();
        quantity.clear();
        price.clear();
        prompt.setText("");
        DataTable.getItems().clear();
    }

    @FXML
    private void BackEventHandle(ActionEvent event) {
        myController.setScreen(ScreensFramework.screen1ID);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

}
