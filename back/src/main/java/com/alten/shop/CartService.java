package com.alten.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(UUID userId) {
        List<Cart> carts = cartRepository.findAllByUserId(userId);
        if (carts.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> productIds = carts.stream().map(Cart::getProductId).toList();
        List<Product> products = productRepository.findAllById(productIds);
        return carts.stream().map(cart -> {
            Optional<Product> p = products.stream().filter(product -> product.getId() == cart.getProductId()).findFirst();
            return p.orElse(null);
        }).filter(Objects::nonNull).toList();
    }

    public void add(UUID userId, Long productId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(productId);
        cartRepository.save(cart);
    }

    public void remove(UUID userId, Long productId) {
        List<Cart> carts = cartRepository.findAllByUserIdAndProductId(userId, productId);
        if (!carts.isEmpty()) {
            cartRepository.deleteById(carts.get(0).getId());
        }
    }
}
