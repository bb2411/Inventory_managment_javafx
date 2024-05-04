package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdditemController {
    @FXML
    protected TextField add_name;
    @FXML
    protected TextField add_cost;
    @FXML
    protected TextField add_selling;
    @FXML
    protected TextField add_quantity;
    @FXML
    protected Label add_message;
    @FXML
    protected void add_item() throws SQLException {
        if(add_name.getText().isBlank() || add_cost.getText().isBlank() || add_quantity.getText().isBlank() || add_selling.getText().isBlank()){
            add_message.setText("Enter all details");
        }else{
            Statement smt=HelloApplication.conn.createStatement();
            String sql = "INSERT INTO items (name, quantity, price, sellingprice) VALUES ('" +
                    add_name.getText().toLowerCase() + "', " + Integer.parseInt(add_quantity.getText()) + ", " +
                    Integer.parseInt(add_cost.getText()) + ", " + Integer.parseInt(add_selling.getText()) + ")";
            try {
                smt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
                ResultSet rs=smt.getGeneratedKeys();
                if(rs.next()){
                    add_message.setText("Item Added Successfully & Item_code is :"+rs.getInt(1));
                }
                add_selling.clear();
                add_cost.clear();
                add_quantity.clear();
                add_name.clear();
            }catch (SQLException e){
                add_message.setText("Something Went Wrong"+e);
            }
        }
    }
}
