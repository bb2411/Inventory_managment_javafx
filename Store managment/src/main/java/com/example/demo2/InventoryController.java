package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {
    protected LinkedList<Item> list=new LinkedList<>();
    @FXML
    protected TextField list_all_code;
    @FXML
    protected TextField list_all_name;
    @FXML
    protected TableView list_inventory;
    @FXML
    protected TableColumn<Item,Integer> list_code;
    @FXML
    protected TableColumn<Item,String> list_name;
    @FXML
    protected TableColumn<Item,Integer> list_quantity;
    @FXML
    protected TableColumn<Item,Integer> list_cost;
    @FXML
    protected TableColumn<Item,Integer> list_selling;
    @FXML
    protected TableColumn<Item,Integer> list_profit;
    private static boolean search_type=true;
    @FXML
    protected void search_item_type(){
        if (search_type){
            search_type=false;
            list_all_name.setDisable(false);
            list_all_code.setDisable(true);
            list_all_code.clear();
        }else{
            search_type=true;
            list_all_name.setDisable(true);
            list_all_code.setDisable(false);
            list_all_name.clear();
        }
    }
    public void initialize(URL url, ResourceBundle resourceBundle){
        search_item_type();
        list_cost.setCellValueFactory(new PropertyValueFactory<>("list_cost"));
        list_code.setCellValueFactory(new PropertyValueFactory<Item,Integer>("list_code"));
        list_profit.setCellValueFactory(new PropertyValueFactory<Item,Integer>("list_profit"));
        list_name.setCellValueFactory(new PropertyValueFactory<Item,String>("list_name"));
        list_quantity.setCellValueFactory(new PropertyValueFactory<Item,Integer>("list_quantity"));
        list_selling.setCellValueFactory(new PropertyValueFactory<Item,Integer>("list_selling"));
        try {
            load_data_to_list();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void load_data_to_list() throws SQLException {
        list_inventory.getItems().clear();
        Statement smt=HelloApplication.conn.createStatement();
        ResultSet rs=smt.executeQuery("select * from items");
        while(rs.next()){
            System.out.println("printing");
            list.add(new Item(rs.getInt("id"),rs.getString("name"),rs.getInt("quantity"),rs.getInt("price"),rs.getInt("sellingprice")));
            list_inventory.getItems().add(new Item(rs.getInt("id"),rs.getString("name"),rs.getInt("quantity"),rs.getInt("price"),rs.getInt("sellingprice")));
        }
        rs.close();
    }
    public void search_item_in_list(){
        boolean found=true;
        if(list_all_name.getText().isBlank()){
            Integer code=Integer.parseInt(list_all_code.getText());
            for(int i=0;i<list_inventory.getItems().size();i++){
                Item item=(Item) list_inventory.getItems().get(i);
                if(item.getList_code()==code){
                    list_inventory.getItems().remove(i);
                    list_inventory.getItems().add(0,item);
                    found=false;
                }
            }
        }else{
            String name=list_all_name.getText().toLowerCase();
            for(int i=0;i<list_inventory.getItems().size();i++){
                Item item=(Item) list_inventory.getItems().get(i);
                if(item.getList_name().contains(name)){
                    list_inventory.getItems().remove(i);
                    list_inventory.getItems().add(0,item);
                    found=false;
                }
            }
        }
        if(found){
            Alert display_404=new Alert(Alert.AlertType.INFORMATION);
            display_404.setHeaderText("Not found");
            display_404.setContentText("item not found in inventory");
            display_404.showAndWait();
        }
    }

}
