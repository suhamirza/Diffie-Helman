import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class VernamCipherDH {

    // This function generates a random key based on a shared secret and the index
    private static String generateKey(int length, BigInteger sharedSecret, int index) {
        StringBuilder key = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Use sharedSecret and index to create a unique key for each plaintext
        for(int i=0; i < length; i++) {
            int charIndex = (sharedSecret.intValue() + index + i) % characters.length();
            key.append(characters.charAt(charIndex)); // Append a character based on the shared secret and index
        }
        return key.toString();
    }

    // This function is responsible for encrypting the plain text, using XOR
    private static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        for(int i=0; i < plaintext.length(); i++) {
            char p = plaintext.charAt(i);
            char c = key.charAt(i);
            char encryptedChar = (char) (p ^ c);
            ciphertext.append(encryptedChar);
        }
        return ciphertext.toString();
    }

    // function to decrypt, XOR is reversible so we can use same function
    private static String decrypt(String ciphertext, String key) {
        return encrypt(ciphertext, key);
    }

    // Diffie-Hellman key exchange
    public static BigInteger[] diffieHellman(BigInteger p, BigInteger g) {
        SecureRandom random = new SecureRandom();
        BigInteger a = new BigInteger(p.bitLength(), random); // Alice's private key
        BigInteger A = g.modPow(a, p); // Alice's public key

        BigInteger b = new BigInteger(p.bitLength(), random); // Bob's private key
        BigInteger B = g.modPow(b, p); // Bob's public key

        // Exchange public keys and compute the shared secret
        BigInteger sharedSecretAlice = B.modPow(a, p); // Alice's shared secret
        BigInteger sharedSecretBob = A.modPow(b, p); // Bob's shared secret

        return new BigInteger[]{sharedSecretAlice, sharedSecretBob}; // Both should be the same
    }

    public static void main(String[] args) {
        String[] plaintexts = {
            "SuhaMirza", "cybersecurity", "ImHungry", "ILoveFood",
            "MyLittlePony", "Sephora", "Pakistan", "Zindabad",
            "FriedChicken", "Instagram"
        };

        ArrayList<String> ciphertexts = new ArrayList<>();
        ArrayList<String> decryptedtexts = new ArrayList<>();

        // Diffie-Hellman parameters
        BigInteger p = new BigInteger("23"); // A prime number
        BigInteger g = new BigInteger("5"); // A base

        BigInteger[] sharedSecrets = diffieHellman(p, g);
        BigInteger sharedSecret = sharedSecrets[0]; // Use Alice's shared secret

        System.out.println("Shared Secret: " + sharedSecret); // Shared secret from Alice's side

        System.out.println("ENCRYPTING...\n");
        System.out.println("DECRYPTING...\n");

        for(int i=0; i<plaintexts.length; i++) {
            String plaintext = plaintexts[i];

            // Generate a unique key for each plaintext using the shared secret and index
            String key = generateKey(plaintext.length(), sharedSecret, i);

            // Encrypt the plaintext
            String ciphertext = encrypt(plaintext, key);

            // Decrypt the ciphertext
            String decryptedtext = decrypt(ciphertext, key);

            ciphertexts.add(ciphertext);
            decryptedtexts.add(decryptedtext);
        }

        System.out.println("STATUS CHECK...\n");
        for(int i = 0; i < plaintexts.length; i++) {
            if(plaintexts[i].equals(decryptedtexts.get(i))) {
                System.out.println("Plain Text " + (i+1) + ": SUCCESS" );
            } else {
                System.out.println("FAILED");
            }
        }

        System.out.println("\nLOADING ALL VALUES...\n");
        for(int i=0; i < plaintexts.length; i++) {
            System.out.println("Plain Text " + (i+1) + ": " + plaintexts[i]);
            System.out.println("Cipher Text " + (i+1) + ": "  + ciphertexts.get(i));
            System.out.println("Decrypted Text " + (i+1) + ": "  + decryptedtexts.get(i) + "\n");
        }
    }
}
