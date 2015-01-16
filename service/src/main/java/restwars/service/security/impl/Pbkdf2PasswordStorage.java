package restwars.service.security.impl;

import com.google.common.base.Splitter;
import com.google.common.io.BaseEncoding;
import restwars.service.security.PasswordStorage;
import restwars.service.security.PasswordStorageException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Iterator;

public class Pbkdf2PasswordStorage implements PasswordStorage {
    private static final int ITERATION_COUNT = 512_000;
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";
    private final SecureRandom secureRandom;

    private static final int HASH_SIZE_IN_BITS = 256;

    private static final int VERSION_1 = 1;
    private static final int CURRENT_VERSION = VERSION_1;

    public Pbkdf2PasswordStorage() {
        try {
            secureRandom = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Exception while getting strong RNG", e);
        }
    }

    @Override
    public String store(String plaintext) {
        byte[] salt = new byte[HASH_SIZE_IN_BITS / 8];
        secureRandom.nextBytes(salt);

        int iterations = ITERATION_COUNT;
        byte[] hash = pbkdf2(plaintext, salt, iterations, HASH_SIZE_IN_BITS);

        return CURRENT_VERSION + ":" + iterations + ":" + BaseEncoding.base64().encode(salt) + ":" + BaseEncoding.base64().encode(hash);
    }

    private byte[] pbkdf2(String plaintext, byte[] salt, int iterations, int bits) {
        PBEKeySpec spec = new PBEKeySpec(plaintext.toCharArray(), salt, iterations, bits);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Hash algorithm not found", e);
        } catch (InvalidKeySpecException e) {
            throw new IllegalStateException("Invalid key", e);
        }
    }

    @Override
    public boolean verify(String plaintext, String stored) throws PasswordStorageException {
        Iterator<String> parts = Splitter.on(':').split(stored).iterator();
        int version = Integer.parseInt(parts.next());
        switch (version) {
            case 1:
                return verifyVersion1(parts, plaintext);
            default:
                throw new PasswordStorageException("Unknown version: " + version);
        }
    }

    private boolean verifyVersion1(Iterator<String> parts, String plaintext) {
        int iterations = Integer.parseInt(parts.next());
        byte[] salt = BaseEncoding.base64().decode(parts.next());
        byte[] hash = BaseEncoding.base64().decode(parts.next());

        byte[] hash2 = pbkdf2(plaintext, salt, iterations, hash.length * 8);

        return equalsConstantTime(hash, hash2);
    }

    private boolean equalsConstantTime(byte[] hash1, byte[] hash2) {
        assert hash1 != null;
        assert hash2 != null;

        int diff = hash1.length ^ hash2.length;
        for (int i = 0; i < hash1.length && i < hash2.length; i++) {
            diff |= hash1[i] ^ hash2[i];
        }
        return diff == 0;
    }
}
