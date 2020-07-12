package invmanagement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ContentModel {
   
//+--------+--------+-------+--------+-------------+------------+
//| Pro_ID | Dosage | Price | Amount | Pro_Name    | Pro_manu   |
//+--------+--------+-------+--------+-------------+------------+

    private final IntegerProperty id,price,amount;
    private final StringProperty dosage,name,manu;

    public ContentModel(int id, String dosage, int price, int amount, String name, String manu) {
        this.id = new SimpleIntegerProperty(id);
        this.price = new SimpleIntegerProperty(price);
        this.amount = new SimpleIntegerProperty(amount);
        this.dosage = new SimpleStringProperty(dosage);
        this.name = new SimpleStringProperty(name);
        this.manu = new SimpleStringProperty(manu);
    }
    
    public int getId(){
        return id.get();
    }
    
    public int getPrice(){
        return price.get();
    }
    
    public int getAmount(){
        return amount.get();
    }
    
    public String getDosage(){
        return dosage.get();
    }
     
    public String getName(){
        return name.get();
    }
    
    public String getManu(){
        return manu.get();
    }
    
    
    public void setId(int id){
        this.id.set(id);
    }
    
    public void setPrice(int price){
        this.price.set(price);
    }
      
    public void setAmount(int amount){
        this.amount.set(amount);
    }
    
    public void setDosage(String dosage){
        this.dosage.set(dosage);
    }
    
    public void setName(String name){
        this.name.set(name);
    }
    
    public void setManu(String manu){
        this.manu.set(manu);
    }
    
    /*
    public ContentModel(){
        this.sno = null;
        this.name = null;
    }    

    public ContentModel(int sno, String name) {
        this.sno = new SimpleIntegerProperty(sno);
        this.name = new SimpleStringProperty(name);
    }

    public int getSno() {
        return sno.get();
    }

    public String getName() {
        return name.get();
    }

    public void setSno(int sno) {
        this.sno.set(sno);
    }

    public void setName(String name) {
        this.name.set(name);
    }
*/
}
