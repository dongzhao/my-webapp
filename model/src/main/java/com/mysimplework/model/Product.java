package com.mysimplework.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mysimplework.core.annotation.*;
import com.mysimplework.model.generic.AbstractDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "my_product")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@MyRepository
@MyRepositoryTest
@MyRestController
public class Product extends AbstractDomain {
    @Column
    @MySearch
    @Testable(input = "iPad")
    private String name;

    @Column
    @MySearch
    @Testable(input = "300.00")
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMyPrice(){
        return BigDecimal.valueOf(100.00) ;
    }
}
