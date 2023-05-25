package org.example.models;

public class Order {
    private int id;
    private int date;

    public Order(int id, int date){
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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
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
