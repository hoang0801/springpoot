package com.jmaster.io.shopservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="cart")
@Entity
public class Cart extends CreateAuditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    private String title;

    private String description;

    private String featureImage;
    @OneToMany
   private List<Product> products;
}
