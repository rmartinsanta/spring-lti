package rmartin.lti.server.service;

/**
 * Crypto Utils
 */
public interface SecretService {
    /**
     * Generate and returns a secret
     * @param size Number of bytes, or strength of the secret
     * @return Returns a base64 encoded string of the secret
     */
    String generateSecret(int size);

    /**
     * Generate and returns a secret with the default size
     * @return Returns a base64 encoded string of the secret
     */
    String generateSecret();
}
