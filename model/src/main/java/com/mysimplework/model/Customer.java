package com.mysimplework.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mysimplework.core.annotation.*;
import com.mysimplework.model.converter.BooleanToStringConverter;
import com.mysimplework.model.generic.AbstractDomain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "my_customer")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@MyRepository
@MyRepositoryTest
@MyRestController
public class Customer extends AbstractDomain {

    @Column(name="FIRST_NAME")
    @MySearch(pageable = true, sortable = true)
    @Testable(input = "Tony")
    private String firstName;

    @Column(name="LAST_NAME")
    @MySearch(pageable = true, sortable = true)
    @Testable(input = "Abbott")
    private String lastName;

    @Convert(converter = BooleanToStringConverter.class)
    @MySearch(pageable = true, sortable = true)
    @Testable(input = "false")
    private Boolean gender;

    @ManyToMany
    @JoinTable(name="my_customer_order",
            joinColumns=
            @JoinColumn(name="CUST_ID", referencedColumnName="ID"),
            inverseJoinColumns=
            @JoinColumn(name="ORDER_ID", referencedColumnName="ID")
    )
    @MyReferenceSearch(
            fieldSearch = {
                    @MySearch(name = "id")
            }

    )
    private List<Order> orders;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ADDRESS_ID", nullable=false, updatable=false)
    @MyReferenceSearch(
            fieldSearch = {
                    @MySearch(name = "id"),
                    @MySearch(name = "suburb", pageable = true, sortable = true),
                    @MySearch(name = "state", pageable = true, sortable = true)
            }

    )
    private Address address;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
