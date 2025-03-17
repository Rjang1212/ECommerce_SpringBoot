package com.ecommerce.demo.DTO;

import lombok.Data;

@Data
public class RegisterRequest {
    private String Username;
    private String Password;
    private String Email;
    private String Role;
}
