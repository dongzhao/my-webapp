/*
package com.mysimplework.repository.test.repositories;

import com.mysimplework.model.Address;
import com.mysimplework.model.Customer;
import com.mysimplework.repositories.AddressRepository;
import com.mysimplework.repositories.CustomerRepository;
import com.mysimplework.repositories.custom.CustomCustomerRepository;
import com.mysimplework.repository.test.configuration.RepositoryConfiguration;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

*/
/**
 * Created by dzhao on 22/09/2015.
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public abstract class AbstractRepositoryTest {

    @Autowired
    protected CustomerRepository customerRepository;

    @Autowired
    protected CustomCustomerRepository customCustomerRepository;

    @Autowired
    protected AddressRepository addressRepository;



    @Before
    public void setUp(){
        initCustomers();
    }

    protected void clearAllCustomers(){

        customerRepository.deleteAll();
        addressRepository.deleteAll();
    }

    protected void initCustomers(){
        newCustomer("Tony", "Abbott", false);
        newCustomer("Malcolm", "Turnbull", false);
    }

    protected String newCustomer(String firstName, String lastName, Boolean gender){
        //String addressId = newAddress();
        Address address = new Address();
        address.setStreetNumber(95);
        address.setStreeName("Rickard Road");
        address.setSuburb("Cordeaux Heights");
        address.setState("NSW");
        address.setPostcode(2526);

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setGender(gender);
        customer.setCreationDate(new Date());
        //customer.setAddress(addressRepository.findOne(addressId));
        customer.setAddress(address);


        customerRepository.save(customer);
        return customer.getId();
    }

    protected String newAddress(){
        Address address = new Address();
        address.setStreetNumber(95);
        address.setStreeName("Rickard Road");
        address.setSuburb("Cordeaux Heights");
        address.setState("NSW");
        address.setPostcode(2526);
        addressRepository.save(address);
        return address.getId();
    }
}
*/
