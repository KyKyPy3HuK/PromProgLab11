package org.example.dao;
import org.example.connection.ConnectionManager;
import org.example.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDao {

    private final DataSource dataSource;
    private List<Product> products = new ArrayList<>();
    private static Product nullProduct = new Product(0,"null","product not exists",-1);
    private static int ID = 0;

    @Autowired
    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> index(){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                    SELECT*
                    FROM products 
                    """);
            ResultSet resultSet = preparedStatement.executeQuery();
            products.clear();
            while (resultSet.next()){
                Product productInOrder = new Product(

                        resultSet.getInt("ID"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("cost")
                );
                products.add(productInOrder);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //public List<Product> index(){
//
    //    return products;
    //}
    public Product getByID(int id){
        return products.stream().filter(product -> product.getId() == id).findAny().orElse(nullProduct);
    }
    public void update(int id, Product updatedProduct){
        Product productToBeUpdated = getByID(id);
        productToBeUpdated.setName(updatedProduct.getName());
        productToBeUpdated.setDescription(updatedProduct.getDescription());
        productToBeUpdated.setCost(updatedProduct.getCost());
    }
    public void save(Product product){
        product.setId(++ID);
        products.add(product);
    }

    public void delete(int id){
        products.removeIf(p -> p.getId() == id);
    }
}
