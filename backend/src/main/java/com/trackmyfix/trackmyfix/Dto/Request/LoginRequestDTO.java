package com.trackmyfix.trackmyfix.Dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

// Login request DTO sin usar
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequestDTO {
    @Email(message = "Email should be valid")
    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    private String password;
}
