package Utilities;

public class FormValidator {
    // Validate email format using regex
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    // Check if the password is strong (at least 8 characters, contains uppercase, lowercase, and a digit)
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$";
        return password.matches(passwordRegex);
    }

    // Check if the password and confirm password match
    public static boolean doPasswordsMatch(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }

    // Check if the username is valid (non-empty, alphanumeric, and no special characters)
    public static boolean isValidUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 20) {
            return false;
        }
        String usernameRegex = "^[a-zA-Z0-9]+$";
        return username.matches(usernameRegex);
    }

    // Generic validation for any field - checks if it's not empty
    public static boolean areNotEmpty(String... fieldValues) {
        for (String fieldValue : fieldValues) {
            if (fieldValue == null || fieldValue.trim().isEmpty()) {
                return false;  // Returns false if any field is null or empty
            }
        }
        return true; // Returns true if all fields are valid
    }
}
