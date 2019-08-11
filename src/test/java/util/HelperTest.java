package util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HelperTest {

  @Test
  public void whenNameIsEmpty_ThenIsNotValid() {
    String name = "";

    assertFalse(Helper.isValidName(name));
  }

  @Test
  public void whenNameContainsNonAlphanumeric_ThenIsNotValid() {
    String name = "mike007";

    assertFalse(Helper.isValidName(name));
  }

  @Test(expected = NullPointerException.class)
  public void whenNameIsNull_ThenThrowException() {
    String name = null;

    assertFalse(Helper.isValidName(name));
  }

  @Test
  public void whenNameWordCharAlone_ThenIsValidName() {
    String name = "mike";

    assertTrue(Helper.isValidName(name));
  }
}
