package com.alten.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public List<Product> get() {
        return productService.getAll();
    }

    @PostMapping("")
    public String create(@RequestBody Product product) {
        productService.create(product);
        return "OK";
    }

    @PatchMapping("/{productId}")
    public String update(@PathVariable(value="productId") String productId, @RequestBody Product product) {
        product.setId(Long.parseLong(productId));
        productService.update(product);
        return "OK";
    }

    @DeleteMapping("/{productId}")
    public String delete(@PathVariable(value="productId") String productId) {
        productService.delete(Long.parseLong(productId));
        return "OK";
    }
}
