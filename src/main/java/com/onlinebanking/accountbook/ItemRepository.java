package com.onlinebanking.accountbook;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>{
	public Item findById(Long Id);
}
