package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Email {
    private String emailAddress;

    public static Email of(String emailAddress) {
        return new Email(emailAddress);
    }
}
