package org.example.controllers;

import org.example.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    private final OrderDao orderDao;
    @Autowired
    OrdersController(OrderDao orderDao){
        this.orderDao = orderDao;
    }

    @GetMapping()
    public String index(){
        return "orders/index";
    }
    @GetMapping("/ordersList")
    public String ordersList(Model model){
        model.addAttribute("orders",orderDao.ordersList());
        return "orders/ordersList";
    }
    @GetMapping("/ordersListSumAndCount")
    public String ordersListBySumAndCount(@RequestParam("sum") int sum,  @RequestParam("count") int count, Model model){
        model.addAttribute("orders",orderDao.getOrderBySumAndCountOfProducts(sum,count));
        return "orders/ordersList";
    }
    @GetMapping("/ordersListProduct")
    public String ordersList(@RequestParam("product") String product,Model model){
        model.addAttribute("orders",orderDao.getOrderByProduct(product));
        return "orders/ordersList";
    }
    @GetMapping("/ordersListNotProductAndDate")
    public String ordersListNotProductAndDate(@RequestParam("product") String product, @RequestParam("date") Date date, Model model)
    {
        model.addAttribute("orders",orderDao.getOrderByExcProductAndDate(product,date));
        return "orders/ordersList";
    }
    @DeleteMapping("/ordersDelete")
    public String ordersDelete(@RequestParam("product") String product, @RequestParam("count") int count){
        orderDao.deleteOrderByProductAndCount(product,count);
        return "productsinorders/index";
    }
    @GetMapping("/orderByProductForm")
    public String orderByProductForm(){
        return "orderByProductForm";
    }
}