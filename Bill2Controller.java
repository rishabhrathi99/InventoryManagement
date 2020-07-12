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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Bill2Controller implements Initializable, ControlledScreen {

    ScreensController myController;

    int tot = 0;

    @FXML
    private TextField productid;
    @FXML
    private TextField manuf;
    @FXML
    private TextField productname;
    @FXML
    private TextField quantity;
    @FXML
    private TextField price;
    @FXML
    private TextField dosage;
    @FXML
    private Button search;
    @FXML
    private Button add;
    @FXML
    private Button clear;
    @FXML
    private Button back;
    @FXML
    private Label totalamount;
    @FXML
    private Label prompt;
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

    private ObservableList<ContentModel> data, databill;
    private DbConnection db;
    @FXML
    private Button showbill;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DbConnection();
        databill = FXCollections.observableArrayList();
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
            if (!productid.getText().isEmpty() && !this.dosage.getText().isEmpty()) {
                rs = stmt.executeQuery("SELECT * FROM Product NATURAL JOIN Dosage where Pro_ID=" + Integer.parseInt(productid.getText()) + " and Dosage='" + this.dosage.getText() + "';");
            } else if (!productid.getText().isEmpty() && dosage.getText().isEmpty()) {
                rs = stmt.executeQuery("SELECT * FROM Product NATURAL JOIN Dosage where Pro_ID=" + Integer.parseInt(productid.getText()) + ";");
            } else if (productid.getText().isEmpty() && dosage.getText().isEmpty()) {
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
    private void addEventHandle(ActionEvent event) {
        try {
            int presentid=0;
            int count = 0;
            int prc = 0;
            int presentamount = 0;
            String presentdosage="";
            String presentname="";
            String presentmanu="";

            Connection con = db.Connect();
            Statement stmt = con.createStatement();
            ResultSet rs = null;
            if (!productid.getText().isEmpty() && !this.dosage.getText().isEmpty()) {
                rs = stmt.executeQuery("SELECT * FROM Product NATURAL JOIN Dosage where Pro_ID=" + Integer.parseInt(productid.getText()) + " and Dosage='" + this.dosage.getText() + "';");
            } else if (!productid.getText().isEmpty() && dosage.getText().isEmpty()) {
                rs = stmt.executeQuery("SELECT * FROM Product NATURAL JOIN Dosage where Pro_ID=" + Integer.parseInt(productid.getText()) + ";");
            }
            while (rs.next()) {
                ++count;
                presentid=rs.getInt("Pro_ID");
                presentdosage=rs.getString("Dosage");
                prc = rs.getInt("Price");
                presentamount = rs.getInt("Amount");
                presentname = rs.getString("Pro_Name");
                presentmanu=rs.getString("Pro_manu");
            }

            if (count == 1) {
                int id = Integer.parseInt(productid.getText());
                int amt = Integer.parseInt(quantity.getText());

                System.out.println("id " + id + " price " + prc + " amt " + amt);

                if (amt <= presentamount) {
                    stmt.executeUpdate("UPDATE Dosage SET Amount=Amount-" + amt + " WHERE Pro_ID=" + id + " AND Dosage='" + dosage.getText() + "';");
                    databill.add(new ContentModel(presentid, presentdosage, prc, amt, presentname, presentmanu));
                    tot += prc * amt;
                    DataTable.setItems(databill);
                    totalamount.setText(Integer.toString(tot));
                } else {
                    prompt.setText("Amount not available");
                }

            } else {
                prompt.setText("Invalid Input");
            }
        } catch (SQLException e) {
            prompt.setText("Invalid input sql");
        } catch (Exception e) {
            prompt.setText("Invalid input");
        }
    }

    @FXML
    private void clearEventHandle(ActionEvent event) {
        productid.clear();
        productname.clear();
        manuf.clear();
        dosage.clear();
        quantity.clear();
        price.clear();
        DataTable.getItems().clear();
        databill = FXCollections.observableArrayList();
        tot = 0;
        totalamount.setText("");
        prompt.setText("");
    }

    @FXML
    private void backEventHandle(ActionEvent event) {
        myController.setScreen(ScreensFramework.screen1ID);
    }

    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @FXML
    private void showBillEventHandle(ActionEvent event) {
        DataTable.setItems(databill);
    }

}
