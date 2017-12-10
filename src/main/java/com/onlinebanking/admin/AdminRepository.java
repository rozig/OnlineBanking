package com.onlinebanking.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    public Admin findByEmail(String email);
    public Admin findByToken(String token);
}
