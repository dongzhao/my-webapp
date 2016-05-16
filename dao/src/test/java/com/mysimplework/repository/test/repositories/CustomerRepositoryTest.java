/*
package com.mysimplework.repository.test.repositories;

import com.mysimplework.model.Customer;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.List;

*/
/**
 * Created by dzhao on 22/09/2015
 *//*

@Ignore
public class CustomerRepositoryTest extends AbstractRepositoryTest{

    @Test
    public void test_get_all(){
        clearAllCustomers();
        initCustomers();
        List<Customer> results = customerRepository.findAll();
        Assert.assertNotNull(results);
        Assert.assertTrue(results.size()==2);
    }

    @Test
    public void test_create_new(){
        clearAllCustomers();
        Customer domain = new Customer();
        domain.setFirstName("test1");
        domain.setLastName("test2");
        domain.setGender(false);
        domain.setCreationDate(new Date());
        customerRepository.save(domain);
        Customer result = customerRepository.findOne(domain.getId());
        Assert.assertNotNull(result);
    }

    @Test
    public void test_create_save(){
        String firstName = "Tony";
        String lastName = "Abbott";
        clearAllCustomers();

        String id = newCustomer(firstName, lastName, false);
        Customer domain = customerRepository.findOne(id);
        domain.setFirstName("upd-"+firstName);
        domain.setLastName("upd-" + lastName);
        customerRepository.save(domain);
        Customer result = customerRepository.findOne(id);
        Assert.assertNotNull(result);
        Assert.assertEquals("upd-" + firstName, result.getFirstName());
        Assert.assertEquals("upd-" + lastName, result.getLastName());
    }

    @Test
    public void test_delete(){
        clearAllCustomers();
        String firstName = "Tony";
        String lastName = "Abbott";
        String id = newCustomer(firstName, lastName, false);
        Customer domain = customerRepository.findOne(id);
        Assert.assertNotNull(domain);

        customerRepository.delete(id);
        Customer result = customerRepository.findOne(id);
        Assert.assertNull(result);
    }
}
*/
