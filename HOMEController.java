/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invmanagement;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author rishabh
 */
public class HOMEController implements Initializable , ControlledScreen {
    
    
    ScreensController myController;
    
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button bill;
    
    
    @Override
    public void setScreenParent(ScreensController screenParent) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     myController = screenParent; }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleAddAction(ActionEvent event) throws SQLException {
        myController.setScreen(ScreensFramework.screen2ID);
    }

    @FXML
    private void handleUpdateAction(ActionEvent event) {
        myController.setScreen(ScreensFramework.screen3ID);
    }

    @FXML
    private void handleBillAction(ActionEvent event) {
    myController.setScreen(ScreensFramework.screen4ID);
    }
    
}
