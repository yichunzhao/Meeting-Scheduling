package mode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

import static util.Helper.isValidEmail;
import static util.Helper.isValidName;

@EqualsAndHashCode
@ToString
@Getter
public final class Person {
  private final String name;
  private final String email;

  public Person(String name, String email) {

    Optional.ofNullable(name).orElseThrow(() -> new IllegalArgumentException("Name is null."));
    Optional.ofNullable(email).orElseThrow(() -> new IllegalArgumentException("Email is null."));

    if (!isValidName(name)) throw new IllegalArgumentException("Name is not valid.");
    if (!isValidEmail(email)) throw new IllegalArgumentException("Email address is not valid.");

    this.name = name;
    this.email = email;
  }
}
