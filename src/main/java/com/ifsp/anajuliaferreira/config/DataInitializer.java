package com.ifsp.anajuliaferreira.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ifsp.anajuliaferreira.model.Admin;
import com.ifsp.anajuliaferreira.repository.AdminRepository;

@Component
public class DataInitializer {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        if (adminRepository.findByEmail("admin@site.com") == null) {

            Admin admin = new Admin();
            admin.setEmail("admin@site.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setNome("Admin");
            adminRepository.insert(admin);
        }
}
}
