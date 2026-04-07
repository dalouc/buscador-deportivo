package activities.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for password security operations.
 */
public class SecurityUtils {

    private static final String PEPPER = "DATJEE";

    /**
     * Generates the SHA-256 hash of the password concatenated with the pepper.
     * @param password the plain-text password
     * @return hex-encoded SHA-256 hash string
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        String toHash = password + PEPPER;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(toHash.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
