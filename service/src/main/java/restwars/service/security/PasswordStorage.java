package restwars.service.security;

public interface PasswordStorage {
    String store(String plaintext);

    boolean verify(String plaintext, String stored) throws PasswordStorageException;
}
