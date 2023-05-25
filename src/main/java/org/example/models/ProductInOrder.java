package org.example.models;

public class ProductInOrder {
    private Order order;
    private Product product;
    private int count;
    public ProductInOrder(Order order, Product product, int count){
        this.order=order;
        this.product=product;
        this.count=count;
    }
    public ProductInOrder(){

    }
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ProductInOrder{" +
                "order=" + order +
                ", product=" + product +
                ", count=" + count +
                '}';
    }
}
