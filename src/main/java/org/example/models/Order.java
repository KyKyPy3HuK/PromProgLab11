package org.example.models;

import java.sql.Date;

public class Order {
    private int id;
    private Date date;

    public Order(int id, Date date){
        this.id=id;
        this.date=date;
    }
    public Order(){
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}
