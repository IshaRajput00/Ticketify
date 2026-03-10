package com.ticketing.ticketmanagement.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    // 🔹 NEW: home page mapping
    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        return "redirect:/redirect";
    }

    @GetMapping("/redirect")
    public String redirectAfterLogin(Authentication authentication) {

        for (GrantedAuthority authority : authentication.getAuthorities()) {

            String role = authority.getAuthority();

            if (role.equals("ROLE_CUSTOMER")) {
                return "redirect:/customer/dashboard";
            }
            if (role.equals("ROLE_AGENT")) {
                return "redirect:/agent/dashboard";
            }
            if (role.equals("ROLE_SUPERVISOR")) {
                return "redirect:/supervisor/dashboard";
            }
            if (role.equals("ROLE_CASE_MANAGER")) {
                return "redirect:/case/dashboard";
            }
            if (role.equals("ROLE_QA")) {
                return "redirect:/qa/dashboard";
            }
            if (role.equals("ROLE_ANALYTICS")) {
                return "redirect:/analytics/dashboard";
            }
        }

        return "redirect:/login";
    }
}
