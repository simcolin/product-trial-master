package com.alten.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(UUID userId) {
        List<Wishlist> wishlists = wishlistRepository.findAllByUserId(userId);
        if (wishlists.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> productIds = wishlists.stream().map(Wishlist::getProductId).toList();
        List<Product> products = productRepository.findAllById(productIds);
        return wishlists.stream().map(cart -> {
            Optional<Product> p = products.stream().filter(product -> product.getId() == cart.getProductId()).findFirst();
            return p.orElse(null);
        }).filter(Objects::nonNull).toList();
    }

    public void add(UUID userId, Long productId) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUserId(userId);
        wishlist.setProductId(productId);
        wishlistRepository.save(wishlist);
    }

    public void remove(UUID userId, Long productId) {
        List<Wishlist> wishlists = wishlistRepository.findAllByUserIdAndProductId(userId, productId);
        if (!wishlists.isEmpty()) {
            wishlistRepository.deleteById(wishlists.get(0).getId());
        }
    }
}
