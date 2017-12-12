package com.onlinebanking.request;

import com.onlinebanking.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
	public Request findById(Long Id);
	public List<Request> findByCustomer(Customer customer);
}

