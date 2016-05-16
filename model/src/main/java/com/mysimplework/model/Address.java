package com.mysimplework.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mysimplework.core.annotation.*;
import com.mysimplework.model.generic.AbstractDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by dzhao on 10/02/2016.
 */
@Entity
@Table(name = "my_address")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@MyRepository
@MyRepositoryTest
@MyRestController
public class Address extends AbstractDomain {
    @Column
    @MySearch
    @Testable(input = "95")
    private int streetNumber;
    @Column
    @MySearch
    @Testable(input = "Rickard Road")
    private String streeName;
    @Column
    @MySearch
    @Testable(input = "Cordeaux Heights")
    private String suburb;
    @Column
    @MySearch
    @Testable(input = "NSW")
    private String state;
    @Column
    @MySearch
    @Testable(input = "2526")
    private int postcode;

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreeName() {
        return streeName;
    }

    public void setStreeName(String streeName) {
        this.streeName = streeName;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }
}
