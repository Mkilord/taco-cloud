package ru.mkilord.tacos.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mkilord.tacos.entites.User;

@Data
public class RegistrationForm {

    @NotBlank(message = "User name can't be empty!")
    private String username;
    @NotBlank(message = "Password field can't be empty!")
    private String password;
    @NotBlank(message = "Fullname field can't be empty!")
    private String fullname;
    @NotBlank(message = "Street field can't be empty!")
    private String street;
    @NotBlank(message = "City field can't be empty!")
    private String city;
    @NotBlank(message = "State field can't be empty!")
    private String state;
    @NotBlank(message = "Zip field can't be empty!")
    private String zip;
    @NotBlank(message = "Phone field can't be empty!")
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password)
                , fullname, street, city, state, zip, phone);
    }
}
