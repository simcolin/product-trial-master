package com.alten.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    @GetMapping("")
    public List<Product> get() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return wishlistService.getAll(currentUser.getId());
    }

    @PostMapping("/{productId}")
    public String add(@PathVariable(value="productId") String productId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long pid = Long.parseLong(productId);
        wishlistService.add(currentUser.getId(), pid);
        return "OK";
    }

    @DeleteMapping("/{productId}")
    public String remove(@PathVariable(value="productId") String productId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long pid = Long.parseLong(productId);
        wishlistService.remove(currentUser.getId(), pid);
        return "OK";
    }
}