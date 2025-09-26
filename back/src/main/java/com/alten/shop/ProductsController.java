package com.alten.shop;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<String> create(@RequestBody Product product) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(currentUser.getEmail(), "admin@admin.com")) {
            productService.create(product);
            return ResponseEntity.ok("OK");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<String> update(@PathVariable(value="productId") String productId, @RequestBody Product product) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(currentUser.getEmail(), "admin@admin.com")) {
            product.setId(Long.parseLong(productId));
            productService.update(product);
            return ResponseEntity.ok("OK");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> delete(@PathVariable(value="productId") String productId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(currentUser.getEmail(), "admin@admin.com")) {
            productService.delete(Long.parseLong(productId));
            return ResponseEntity.ok("OK");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
