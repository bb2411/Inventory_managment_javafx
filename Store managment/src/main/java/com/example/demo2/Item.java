package com.example.demo2;

public class Item {
    public Integer list_code;
    public String list_name;

    public Integer getList_code() {
        return list_code;
    }

    public String getList_name() {
        return list_name;
    }

    public Integer getList_quantity() {
        return list_quantity;
    }

    public Integer getList_cost() {
        return list_cost;
    }

    public Integer getList_selling() {
        return list_selling;
    }

    public Integer getList_profit() {
        return list_profit;
    }
    public Integer getTotal(){
        return total;
    }
    public Integer list_quantity;
    public Integer list_cost;
    public Integer list_selling;
    public Integer list_profit;
    public Integer total;
    public Item(int code,String name,int qnty,int cost,int sell){
        this.list_code=code;
        this.list_cost=cost;
        this.list_name=name;
        this.list_quantity=qnty;
        this.list_selling=sell;
        this.list_profit=sell-cost;
        this.total=qnty*sell;
    }
}
