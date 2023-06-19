package org.example.dao;

import org.example.models.Order;
import org.example.models.Product;
import org.example.models.ProductInOrder;
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
public class ProductInOrderDao {
    private final DataSource dataSource;
    @Autowired
    public ProductInOrderDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<ProductInOrder> getOrderInfoByOrderID(int orderID){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                    SELECT*
                    FROM productsinorders JOIN orders ON productsinorders.orderID = orders.ID JOIN products on productsinorders.productID = products.ID
                    WHERE productsinorders.orderID = ?
                    """);
            preparedStatement.setInt(1,orderID);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<ProductInOrder> orders = new ArrayList<>();

            while (resultSet.next()){
                ProductInOrder productInOrder = new ProductInOrder(
                        new Order(
                                resultSet.getInt("orderID"),
                                resultSet.getDate("date")
                        ),
                        new Product(
                                resultSet.getInt("productID"),
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                resultSet.getInt("cost")
                        ),
                        resultSet.getInt("count")
                );

                orders.add(productInOrder);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getOrderBySumAndCountOfProducts(int sum, int countOfProducts){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                    SELECT orderID
                    	FROM productsinorders JOIN products on productsinorders.productID = products.ID
                    	group by orderID
                        having  sum(cost * count) > ?  and count(productID) = ?
                    """);
            preparedStatement.setInt(1,sum);
            preparedStatement.setInt(2,countOfProducts);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> orders = new ArrayList<>();
            while (resultSet.next()){
                Integer orderID = resultSet.getInt("orderID");
                orders.add(orderID);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getOrderByProduct(String productName){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                    SELECT orderID
                    FROM productsinorders JOIN products ON productsinorders.productID = products.ID
                    where name = ?
                    """);
            preparedStatement.setString(1,productName);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> orders = new ArrayList<>();
            while (resultSet.next()){
                Integer orderID = resultSet.getInt("orderID");
                orders.add(orderID);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getOrderByExcProductAndDate(String productName, int date){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                    select distinct orderID
                    from productsinorders join orders on productsinorders.orderID = orders.ID join products on products.ID = productsinorders.productID
                    where name != ? and date = ?
                    """);
            preparedStatement.setString(1,productName);
            preparedStatement.setInt(2,date);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> orders = new ArrayList<>();
            while (resultSet.next()){
                Integer orderID = resultSet.getInt("orderID");
                orders.add(orderID);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer addOrderByProductsInOrdersByDate(int date){
        Integer result = 0;
        int targetId = 0;
        try(Connection connection = dataSource.getConnection()){
            //Создание ID
            PreparedStatement preparedStatement = connection.prepareStatement("""
                SELECT max(ID) as maxID
                from orders
            """);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                targetId = resultSet.getInt("maxID");
            }
            targetId++;

            //получение заказов по дате
            preparedStatement =
                    connection.prepareStatement("""
                    select productID, sum(count)
                    from productsinorders join orders on productsinorders.orderID = orders.ID
                    where date = ?
                    group by productID
                    """);

            preparedStatement.setInt(1,date);
            resultSet = preparedStatement.executeQuery();

            // Добавление нового заказа
            preparedStatement = connection.prepareStatement("""
                INSERT INTO `promprog10`.`orders` (`date`) VALUES (?)
            """);
            preparedStatement.setInt(1,date);
            result += 100 * preparedStatement.executeUpdate();

            //добавление товаров
            while (resultSet.next()){
                int productID = resultSet.getInt("productID");
                Integer count = resultSet.getInt("count");

                preparedStatement = connection.prepareStatement("""
                    INSERT INTO `promprog10`.`productsinorders` (`orderID`, `productID`, `count`) VALUES (?, ?, ?)
                """);
                preparedStatement.setInt(1,targetId);
                preparedStatement.setInt(2,productID);
                preparedStatement.setInt(3,count);

                result += preparedStatement.executeUpdate();
            }

            return result;
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
