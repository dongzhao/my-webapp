package com.mysimplework.repositories.custom;

import com.mysimplework.model.Address;
import com.mysimplework.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by dzhao on 4/02/2016.
 */
public interface CustomCustomerRepository extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {

    Page<Customer> findByFirstName(String firstName, Pageable pageable);

    List<Customer> findByFirstName(String firstName, Sort sort);

    List<Customer> findByFirstName(String firstName);

    List<Customer> findByAddress(Address address);

    List<Customer> findByAddressState(String state);

    List<Customer> findByAddressId(String id);

}
