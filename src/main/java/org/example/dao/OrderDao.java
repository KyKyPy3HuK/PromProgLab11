package org.example.dao;

import org.example.models.Order;
import org.example.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDao {
    private final DataSource dataSource;
    @Autowired
    public OrderDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public List<Order> ordersList() {
        try (Connection connection = dataSource.getConnection()) {
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
                        resultSet.getDate("date")
                );
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        public List<Order> getOrderBySumAndCountOfProducts(int sum, int countOfProducts){
            try(Connection connection = dataSource.getConnection()){
                PreparedStatement preparedStatement =
                        connection.prepareStatement("""
                    SELECT orders.ID, orders.date
                    FROM orders join productsinorders on orders.ID = orderID JOIN products on productsinorders.productID = products.ID
                    group by orders.ID, orders.date
                    having  sum(cost * count) > ?  and count(productID) = ?
                    """);
                preparedStatement.setInt(1,sum);
                preparedStatement.setInt(2,countOfProducts);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Order> orders = new ArrayList<>();
                while (resultSet.next()){
                    Order order = new Order(
                            resultSet.getInt("ID"),
                            resultSet.getDate("date")
                            );
                    orders.add(order);
                }
                return orders;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    public List<Order> getOrderByProduct(String productName){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                    SELECT orderID, date 
                    FROM orders join productsinorders on orders.id = orderID JOIN products ON productsinorders.productID = products.ID
                    where name = ?
                    """);
            preparedStatement.setString(1,productName);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()){
                Order order = new Order(
                resultSet.getInt("orderID"),
                resultSet.getDate("date")
                );
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Order> getOrderByExcProductAndDate(String productName, Date date){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                    select distinct orderID, date
                    from productsinorders join orders on productsinorders.orderID = orders.ID join products on products.ID = productsinorders.productID
                    where name != ? and date = ?
                    """);
            preparedStatement.setString(1,productName);
            preparedStatement.setDate(2,date);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()){
                int orderID = resultSet.getInt("orderID");
                Date dateFromQuery = resultSet.getDate("date");
                Order order = new Order(orderID, dateFromQuery);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int deleteOrderByProductAndCount(String productName, int count){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                    	delete orders
                        from orders JOIN productsinorders on ID = orderID join products on products.ID = productsinorders.productID
                        where name = ? and count = ? 
                    """);

            preparedStatement.setString(1,productName);
            preparedStatement.setInt(2,count);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }

