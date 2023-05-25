package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dao.ProductDao;
import org.example.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private final ProductDao productDao;
    @Autowired
    public ProductsController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("products",productDao.index());
        return "products/index";
    }

    @GetMapping("/{id}")
    public String getByID(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productDao.getByID(id));
        return "products/getByID";
    }

    @GetMapping("/new")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "products/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "products/new";
        }
        productDao.save(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("product", productDao.getByID(id));
        return "products/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("product") @Valid Product product,BindingResult bindingResult, @PathVariable("id") int id){
        if (bindingResult.hasErrors()){
            return "products/edit";
        }
        productDao.update(id, product);
        return "redirect:/products";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        productDao.delete(id);
        return "redirect:/products";
    }
}
