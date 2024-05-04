package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UpdateItemController implements Initializable {
    protected Integer id=0;
    @FXML
    protected TextField update_search_code;
    @FXML
    protected TextField update_search_name;
    @FXML
    protected TextField update_name;
    @FXML
    protected TextField update_quantity;
    @FXML
    protected TextField update_cost;
    @FXML
    protected TextField update_selling;
    @FXML
    protected Button update_item_button;
    @FXML
    protected Button delete_item_button;
    @FXML
    protected Label update_final_message;
    private int update_row_index;
    public void initialize(URL url,ResourceBundle resourceBundle){
        delete_item_button.setDisable(true);
        update_search_name.setDisable(true);
        update_item_button.setDisable(true);
    }
    private static boolean search_type=true;
    @FXML
    protected void search_type(){
        if (search_type){
            search_type=false;
            update_search_name.setDisable(false);
            update_search_code.setDisable(true);
            update_search_code.clear();
        }else{
            search_type=true;
            update_search_name.setDisable(true);
            update_search_code.setDisable(false);
            update_search_name.clear();
        }
    }
    @FXML
    protected void update() throws SQLException {
        String sql = "UPDATE items SET name='" + update_name.getText() + "', quantity=" + Integer.parseInt(update_quantity.getText()) + ", price=" + Integer.parseInt(update_cost.getText()) + ", sellingprice=" + Integer.parseInt(update_selling.getText()) + " WHERE id=" + this.update_row_index;
        Statement smt = HelloApplication.conn.createStatement();
        int rowsAffected = smt.executeUpdate(sql);
        if (rowsAffected > 0) {
            update_final_message.setText("Item Details Updated Successfully");
            clear_fields();
        } else {
            clear_fields();
            update_final_message.setText("Something went wrong");
        }
    }
    @FXML
    protected void search_for_update() throws SQLException{
        String sql;
        if(update_search_name.getText().isBlank()){
            int i = 0;
            try{
                i=Integer.parseInt(update_search_code.getText());
            }catch (Exception e){
                update_final_message.setText("Unexpected Input");
            }
            sql="select * from items where id="+i;
        }else{
            sql="select * from items where name='"+update_search_name.getText().toLowerCase()+"'";
        }
        update_search_name.clear();
        update_search_code.clear();
        Statement smt=HelloApplication.conn.createStatement();
        ResultSet rs= smt.executeQuery(sql);
        sql="";
        if (rs.next()){
            this.id=rs.getInt("id");
            update_name.setText(rs.getString("name"));
            update_name.setDisable(false);
            update_quantity.setText(String.valueOf(rs.getInt("quantity")));
            update_quantity.setDisable(false);
            update_cost.setText(String.valueOf(rs.getInt("price")));
            update_cost.setDisable(false);
            update_selling.setText(String.valueOf(rs.getInt("sellingprice")));
            update_selling.setDisable(false);
            this.update_row_index=rs.getInt("id");
            delete_item_button.setDisable(false);
            update_item_button.setDisable(false);
        }else{
            update_final_message.setText("No Item Found");
            clear_fields();
        }
    }
    @FXML
    protected void delete_item() throws SQLException{
        if(update_name.getText().isBlank() || this.id==0){
            Alert a=new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Error");
            a.setContentText("Something went wrong,try again!");
            a.showAndWait();
            clear_fields();
        }else{
            String sql="delete from items where id="+this.id;
            Statement smt=HelloApplication.conn.createStatement();
            int delete_flag=smt.executeUpdate(sql);
            Alert a=new Alert(Alert.AlertType.INFORMATION);
            if(delete_flag > 0){
                a.setHeaderText("Item Deleted");
                a.showAndWait();
                clear_fields();
            }else{
                a.setHeaderText("Item is Not Deleted ,Try again!");
                a.showAndWait();
                clear_fields();
            }
        }
    }
    protected void clear_fields(){
        update_name.clear();;
        update_search_code.clear();
        update_quantity.clear();
        update_cost.clear();
        update_selling.clear();
        update_search_name.clear();
        update_item_button.setDisable(true);
        delete_item_button.setDisable(true);
    }
}
