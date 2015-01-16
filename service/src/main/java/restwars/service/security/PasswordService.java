package restwars.service.security;

/**
 * Hashes and verifies passwords.
 */
public interface PasswordService {
    /**
     * Hashes the given password.
     *
     * @param plaintext Plaintext password.
     * @return Hash.
     */
    String hash(String plaintext);

    /**
     * Verifies the given plaintext password against the given password hash.
     *
     * @param plaintext Plaintext password.
     * @param hash      Password hash.
     * @return True if the password matches, false otherwise.
     * @throws PasswordException If something went wrong while verifying the password.
     */
    boolean verify(String plaintext, String hash) throws PasswordException;
}
