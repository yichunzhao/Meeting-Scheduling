package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Person {
    private final Name name;
    private final Email email;
}

