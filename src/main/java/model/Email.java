package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
public class Email {
    private String emailAddress;

    private Email(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public static Email of(String emailAddress) {
        return new Email(emailAddress);
    }
}
