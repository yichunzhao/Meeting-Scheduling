package util;

public class Helper {

  public static boolean isValidEmail(String email) {
    return email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
  }

  public static boolean isValidName(String name) {
    return name.matches("^[a-zA-Z]+$");
  }
}
