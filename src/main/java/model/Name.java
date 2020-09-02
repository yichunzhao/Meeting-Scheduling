package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Name {
    private String name;

    private Name(String name) {
        this.name = name;
    }

    public static Name of(String name) {
        return new Name(name);
    }
}
