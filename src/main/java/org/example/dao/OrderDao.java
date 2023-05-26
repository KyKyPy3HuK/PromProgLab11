package org.example.dao;

import org.example.models.Order;
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
public class OrderDao {
    private final DataSource dataSource;
    @Autowired
    public OrderDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public List<Order> ordersList(){
        try(Connection connection = dataSource.getConnection()){
            List<Order> orders = new ArrayList<>();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                    SELECT*
                    FROM orders
                    """);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Order order = new Order(

                        resultSet.getInt("ID"),
                        resultSet.getInt("date")
                );
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
