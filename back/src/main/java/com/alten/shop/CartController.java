package com.alten.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("")
    public List<Product> get() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return cartService.getAll(currentUser.getId());
    }

    @PostMapping("/{productId}")
    public String add(@PathVariable(value="productId") String productId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long pid = Long.parseLong(productId);
        cartService.add(currentUser.getId(), pid);
        return "OK";
    }

    @DeleteMapping("/{productId}")
    public String remove(@PathVariable(value="productId") String productId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long pid = Long.parseLong(productId);
        cartService.remove(currentUser.getId(), pid);
        return "OK";
    }
}
