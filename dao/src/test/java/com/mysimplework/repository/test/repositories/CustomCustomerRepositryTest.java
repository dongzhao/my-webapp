/*
package com.mysimplework.repository.test.repositories;

import com.mysimplework.model.Address;
import com.mysimplework.model.Customer;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

*/
/**
 * Created by dzhao on 4/02/2016.
 *//*

@Ignore
public class CustomCustomerRepositryTest extends AbstractRepositoryTest{

    @Test
    public void test_get_all(){
        clearAllCustomers();
        initCustomers();

        List<Customer> results = customCustomerRepository.findAll();
        Assert.assertNotNull(results);
        Assert.assertTrue(results.size()==2);
    }

    @Test
    public void test_get_customer_address(){
        clearAllCustomers();
        initCustomers();

        Address address = addressRepository.findAll().get(0);
        List<Customer> results = customCustomerRepository.findByAddress(address);
        Assert.assertNotNull(results);
        Assert.assertTrue(results.size()==1);

        results = customCustomerRepository.findByAddressState(address.getState());
        Assert.assertNotNull(results);
        Assert.assertTrue(results.size()==2);

        results = customCustomerRepository.findByAddressId(address.getId());
        Assert.assertNotNull(results);
        Assert.assertTrue(results.size()==1);
    }
}
*/
