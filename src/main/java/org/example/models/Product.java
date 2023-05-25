package org.example.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Product {
    private int id;
    @NotEmpty(message = "Product name should not be empty")
    @Size(min=2,max=45,message = "Name length not between 2-45")
    private String name;
    private String description;
    @Min(value = 0, message = "Cost is negative value!")
    private int cost;
    public Product(int id,String name, String description, int cost){
        this.id = id;
        this.name=name;
        this.description=description;
        this.cost=cost;
    }
    public Product(){

    }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}
