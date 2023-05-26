package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dao.ProductInOrderDao;
import org.example.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productsinorders")
public class ProductsInOrdersController {

    private final ProductInOrderDao productInOrderDao;
    @Autowired
    public ProductsInOrdersController(ProductInOrderDao productInOrderDao) {
        this.productInOrderDao = productInOrderDao;
    }

    @GetMapping()
    public String index(){
        return "productsinorders/index";
    }

    @GetMapping("/{id}")
    public String getByID(@PathVariable("id") int id,Model model){
        model.addAttribute("orderId",id);
        model.addAttribute("productsinorders",productInOrderDao.getOrderInfoByOrderID(id));
        return "productsinorders/getByOrderID";
    }

}