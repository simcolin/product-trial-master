package com.alten.shop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private String description;

    private String image;

    private String category;

    private Float price;

    private Integer quantity;

    private String internalReference;

    private Integer shellId;

    private String inventoryStatus;

    private Integer rating;

    private Long createdAt;

    private Long updatedAt;
}