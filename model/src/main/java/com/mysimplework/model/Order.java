package com.mysimplework.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mysimplework.core.annotation.*;
import com.mysimplework.model.generic.AbstractDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/*@Entity
@Table(name = "my_order")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@MyRepository
@MyRepositoryTest
@MyRestController*/
public class Order extends AbstractDomain {
    @Column(name="ORDER_DATE")
    @MySearch
    @Testable(input = "1/1/2016")
    private Date orderDate;

    @ManyToMany(mappedBy="orders")
    private List<Customer> customers;

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
