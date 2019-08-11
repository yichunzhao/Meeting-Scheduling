package mode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

import static util.Helper.isValidEmail;

@EqualsAndHashCode
@ToString
@Getter
public final class Person {
  private final String name;
  private final String email;

  public Person(String name, String email) {

    this.name =
        Optional.ofNullable(name).orElseThrow(() -> new IllegalArgumentException("name is null"));

    Optional.ofNullable(email).orElseThrow(() -> new IllegalArgumentException("email is null"));

    if (!isValidEmail(email)) throw new IllegalArgumentException("Email address is not valid.");

    this.email = email;
  }
}
