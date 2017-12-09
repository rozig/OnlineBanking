package com.onlinebanking.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findByUsername(String username);
    public Customer findByPhoneNumber(String phonenumber);
    public Customer findByEmail(String email);
    public Customer findById(Long Id);
}
