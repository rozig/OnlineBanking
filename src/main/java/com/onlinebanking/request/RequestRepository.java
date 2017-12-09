package com.onlinebanking.request;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
	public Request findById(Long Id);
}
