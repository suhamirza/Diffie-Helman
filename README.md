# Diffie-Helman Project
This project demonstrates a secure implementation of the Vernam cipher, enhanced with Diffie-Hellman key exchange to perform encryption and decryption on two "servers" (simulated as such) without sharing the encryption key directly.

## Features
* Vernam Cipher: Implements the Vernam cipher using XOR operation to encrypt and decrypt text.
* Diffie-Hellman Key Exchange: Securely generates a shared secret between two parties, which is used to derive encryption keys without sharing them directly.
* Unique Keys: Each plaintext is encrypted with a unique key generated using the shared secret and the index of the plaintext.
* Security Validation: Ensures that decrypted texts match the original plaintexts through automated testing.
