package com.devanshitrivedi.springormdemo.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class ProductOrderJoin {

    @EmbeddedId
    private ProductOrderKey id;

    @ManyToOne
    @MapsId(value = "product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId(value = "order_id")
    @JoinColumn(name = "order_id")
    private Order order;

    @NonNull
    private int quantity;
}
