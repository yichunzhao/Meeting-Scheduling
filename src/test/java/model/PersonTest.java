package model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PersonTest {

    private String email;
    private String name;

    private Person person;

    @Before
    public void setUp() throws Exception {
        name = "mike";
        email = "ynz@gmail.com";

        person = new Person(name, Email.of(email));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetNullName_ThenThrowException() {
        new Person(null, Email.of(email));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetNameNullValue_ThenThrowException() {
        new Person(null, Email.of(email));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetEmailNullValue_ThenThrowException() {
        new Person(name, null);
    }

    @Test
    public void whenGetName_ThenGetExpected() {
        assertThat(person.getName(), is(name));
    }

    @Test
    public void whenGetEmail_ThenGetExpected() {
        assertThat(person.getEmail(), is(email));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetEmailAddressWithoutDomain_ThenThrowException() {
        String badEmailAddress = "xxx@";
        new Person("mike", Email.of(badEmailAddress));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetEmailAddressWithoutAt_ThenThrowException() {
        String badEmailAddress = "xxx";
        new Person("mike", Email.of(badEmailAddress));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetEmailAddressMissingOneDomain_ThenThrowException() {
        String badEmailAddress = "xxx@yyy";
        new Person("mike", Email.of(badEmailAddress));
    }

    @Test
    public void whenSetValidEmailAddress_ThenThrowException() {
        String goodAddress = "xxx@yyy.com";
        Person person = new Person("mike", Email.of(goodAddress));

        assertThat(person.getEmail(), is(goodAddress));
    }
}
