package com.jmaster.io.shopservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="bill")
@Entity
public class Bill extends CreateAuditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    private Long price;

    private int quantity;

    @OneToOne
    private Cart cart;


}
