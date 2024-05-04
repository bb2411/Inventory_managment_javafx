package com.example.demo2;
import javax.imageio.ImageIO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.ResourceBundle;
public class BillingController implements Initializable {
    protected Integer total=0;
    protected Alert a=new Alert(Alert.AlertType.WARNING);
    LinkedList<Item> bill=new LinkedList<>();
    @FXML
    protected TextField customer_name;
    @FXML
    protected TextField grand_total;
    @FXML
    protected TableView bill_items;
    @FXML
    protected TableColumn<Item,Integer> bill_item_code;
    @FXML
    protected TableColumn<Item,String> bill_item_name;
    @FXML
    protected TableColumn<Item,Integer> bill_item_quantity;
    @FXML
    protected TableColumn<Item,Integer> bill_item_total;
    @FXML
    protected TableColumn<Item,Integer> bill_item_mrp;
    @FXML
    protected TextField bill_code;
    @FXML
    protected TextField bill_quantity;
    public void initialize(URL url, ResourceBundle resourceBundle){
        bill_item_code.setCellValueFactory(new PropertyValueFactory<Item,Integer>("list_code"));
        bill_item_total.setCellValueFactory(new PropertyValueFactory<Item,Integer>("total"));
        bill_item_name.setCellValueFactory(new PropertyValueFactory<Item,String>("list_name"));
        bill_item_quantity.setCellValueFactory(new PropertyValueFactory<Item,Integer>("list_quantity"));
        bill_item_mrp.setCellValueFactory(new PropertyValueFactory<Item,Integer>("list_selling"));
    }
    @FXML
    protected void add_next_item() throws SQLException {
        if(bill_code.getText().isBlank() || bill_quantity.getText().isBlank() || bill_quantity.getText().contains("-")){
            a.setHeaderText("Enter all Details");
            a.showAndWait();
        }else{
            Integer code=Integer.parseInt(bill_code.getText());
            Integer quantity=Integer.parseInt(bill_quantity.getText());
            Statement smt=HelloApplication.conn.createStatement();
            String sql="select * from items where id="+code;
            ResultSet rs=smt.executeQuery(sql);
            if(rs.next()){
                if(quantity <= rs.getInt("quantity")){
                Item item=new Item(rs.getInt("id"),rs.getString("name"),quantity, rs.getInt("price"),rs.getInt("sellingprice"));
                    int new_quantity=rs.getInt("quantity")-quantity;
                    int flag=smt.executeUpdate("update items set quantity="+new_quantity+" where id="+rs.getInt("id"));
                    if(flag>=1){
                        this.total=this.total+item.getTotal();
                        grand_total.setText(""+(this.total));
                        bill_items.getItems().add(item);
                        bill.add(item);
                        bill_code.clear();
                        bill_quantity.clear();
                    }else{
                        a.setHeaderText("something went wrong");
                        a.showAndWait();
                    }
                }else{
                    a.setHeaderText("Item is Not in Stock");
                    a.showAndWait();
                }
            }else{
                a.setHeaderText("Item is Not in Inventory");
                a.showAndWait();
            }
        }
    }
    @FXML
    protected void generate_bill(){
        if(customer_name.getText().isBlank() || bill.isEmpty()){
            a.setHeaderText("Cannot generate bill");
            a.setContentText("first, Add any item than we can generate bill");
            a.showAndWait();
        }else{
            bill_items.getItems().add(new Item(0,"Total :",1,0,this.total));
            WritableImage basic=bill_items.snapshot(null,null);
            File file=new File("D:\\Programs\\java\\javafx\\Store managment\\bills\\"+customer_name.getText()+".png");
            try {
                BufferedImage img=convertToBufferedImage(basic);
                ImageIO.write(img,"png",file);
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setHeaderText("Bill generated successfully");
                a.setContentText("Bill saved as " + customer_name.getText()+".png");
                a.showAndWait();
                bill_items.getItems().clear();
                customer_name.clear();
                bill_code.clear();
                bill_quantity.clear();
                grand_total.clear();
            } catch (IOException e) {
                a.setHeaderText("Error saving bill");
                a.setContentText("An error occurred while saving the bill image.");
                a.showAndWait();
            }
        }
    }
    private BufferedImage convertToBufferedImage(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        PixelReader pixelReader = image.getPixelReader();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = pixelReader.getArgb(x, y);
                bufferedImage.setRGB(x, y, argb);
            }
        }
        return bufferedImage;
    }
}
