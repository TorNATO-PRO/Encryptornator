package sample;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TextDecryptor {

    private final KeyPair keys;

    public TextDecryptor(KeyPair keys) {
        this.keys = keys;
    }

    public String decrypt(String encrypted) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, keys.getPublic());
            return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted)));
        } catch (NoSuchAlgorithmException | InvalidKeyException
                | NoSuchPaddingException | BadPaddingException
                | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";
    }

}
