package invmanagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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

public class Add2Controller implements Initializable, ControlledScreen {

    ScreensController myController;

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
    private Button add;
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
    @FXML
    private Button showAll;
    @FXML
    private Button addDosage;


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
    private void addEventHandle(ActionEvent event) {
        boolean flag1;

        if (productid.getText().isEmpty() || price.getText().isEmpty() || quantity.getText().isEmpty() || this.dosage.getText().isEmpty()||productname.getText().isEmpty() || this.manuf.getText().isEmpty()) {
            flag1 = false;
            
        } else {
            flag1 = true;
        }
        if (flag1) {
            int id = Integer.parseInt(productid.getText());
            int pr = Integer.parseInt(price.getText());
            int qty = Integer.parseInt(quantity.getText());
            String manuf = this.manuf.getText();
            String name = productname.getText();
            String dosage = this.dosage.getText();

            try {
                Connection con = db.Connect();
                data = FXCollections.observableArrayList();
                Statement stmt = con.createStatement();

                String queryProduct = ("INSERT INTO Product VALUES (" + id + ",'" + name + "','" + manuf + "');");
                String queryDosage = ("INSERT INTO Dosage VALUES (" + id + ",'" + dosage + "'," + pr + "," + qty + ");");
                
                stmt.executeUpdate(queryProduct);
                stmt.executeUpdate(queryDosage);

                ResultSet rs;

                rs = stmt.executeQuery("SELECT Product.Pro_ID,Dosage,Price,Amount,Pro_Name,Pro_manu FROM Product INNER JOIN Dosage where Product.Pro_ID=" + id + " and Dosage='" + dosage + "';");
                while (rs.next()) {

                    data.add(new ContentModel(rs.getInt("Pro_ID"), rs.getString("Dosage"), rs.getInt("Price"), rs.getInt("Amount"), rs.getString("Pro_Name"), rs.getString("Pro_manu")));
                }
            prompt.setText("Added Successfully");
        
            } 
            catch(SQLIntegrityConstraintViolationException ef){
             prompt.setText("Constraint Violation");
            }
            catch (SQLException e) {
                System.out.println("SQL EXCEPTION" + e);
                prompt.setText("Invalid Input");
            }

            DataTable.setItems(data);
            } else {
            prompt.setText("Incomplete Input");
        }
    }
    
    @FXML
    private void addDosageEventHandle(ActionEvent event) {
         boolean flag1;

        if (productid.getText().isEmpty() || price.getText().isEmpty() || quantity.getText().isEmpty() || this.dosage.getText().isEmpty()) {
            flag1 = false;
            
        } else {
            flag1 = true;
        }
        if(flag1){
            int id = Integer.parseInt(productid.getText());
            int pr = Integer.parseInt(price.getText());
            int qty = Integer.parseInt(quantity.getText());
            String dosage = this.dosage.getText();

            try {
                Connection con = db.Connect();
                data = FXCollections.observableArrayList();
                Statement stmt = con.createStatement();

                String queryDosage = ("INSERT INTO Dosage VALUES (" + id + ",'" + dosage + "'," + pr + "," + qty + ");");
                stmt.executeUpdate(queryDosage);

                ResultSet rs;

                rs = stmt.executeQuery("SELECT Product.Pro_ID,Dosage,Price,Amount,Pro_Name,Pro_manu FROM Product INNER JOIN Dosage where Product.Pro_ID=" + id + " and Dosage='" + dosage + "';");
                while (rs.next()) {

                    data.add(new ContentModel(rs.getInt("Pro_ID"), rs.getString("Dosage"), rs.getInt("Price"), rs.getInt("Amount"), rs.getString("Pro_Name"), rs.getString("Pro_manu")));
                }
            prompt.setText("Added Successfully");
        
            } 
            catch(SQLIntegrityConstraintViolationException ef){
             prompt.setText("Constraint Violation");
            }
            catch (SQLException e) {
                System.out.println("SQL EXCEPTION" + e);
                prompt.setText("Invalid Input");
            }

            DataTable.setItems(data);
            } 
        else{
            prompt.setText("Invalid Input");
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
        prompt.setText("");
    }

    @FXML
    private void showAllEventHandle(ActionEvent event) {
        try {
            Connection con = db.Connect();
            data = FXCollections.observableArrayList();
            Statement stmt = con.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT * FROM Product NATURAL JOIN Dosage ;");
            while (rs.next()) {

                data.add(new ContentModel(rs.getInt("Pro_ID"), rs.getString("Dosage"), rs.getInt("Price"), rs.getInt("Amount"), rs.getString("Pro_Name"), rs.getString("Pro_manu")));
            }
            
        DataTable.setItems(data);
        prompt.setText("");
        } catch (SQLException e) {
            System.out.println("SQL EXCEPTION" + e);
            prompt.setText("Invalid Input");
        }

    }

    @FXML
    private void backEventHandle(ActionEvent event) {
        myController.setScreen(ScreensFramework.screen1ID);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        myController = screenParent;
    }
    
    

}
