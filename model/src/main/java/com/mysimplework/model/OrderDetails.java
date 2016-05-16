package com.mysimplework.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mysimplework.core.annotation.*;
import com.mysimplework.model.generic.AbstractDomain;

import javax.persistence.*;

@Entity
@Table(name = "my_order_details")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@MyRepository
@MyRepositoryTest
@MyRestController
public class OrderDetails extends AbstractDomain {
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @MyReferenceSearch(
            fieldSearch = {
                    @MySearch(name = "id")
            }

    )
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_ID", nullable=false, updatable=false)
    @MyReferenceSearch(
            fieldSearch = {
                    @MySearch(name = "id"),
                    @MySearch(name = "name", pageable = true, sortable = true),
                    @MySearch(name = "price", type = "java.math.BigDecimal", pageable = true, sortable = true)
            }

    )
    private Product product;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

   public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
