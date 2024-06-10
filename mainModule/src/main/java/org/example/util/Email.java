package org.example.util;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.regex.Pattern.compile;

public class Email {

    private final String email;

    private Email(String email) {
        this.email = email;
    }

    public static boolean isEmail(String value) {
        final var emailPattern = compile("^(.+)@(\\S+)$");
        return emailPattern.matcher(value).matches();
    }

    public static Email from(String email) {
        checkArgument(isEmail(email), "email doesn't match pattern");
        return new Email(email);
    }

    public String getEmail() {
        return email;
    }
}
