package org.example.controllers;

import org.example.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/orderByProductForm")
    public String orderByProductForm(){
        return "orderByProductForm";
    }
}