package com.onlinebanking.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);
    Customer findByPhoneNumber(String phonenumber);
    Customer findByEmail(String email);
}
