package sample;

import java.io.*;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyLoader {

    private KeyPair keys;
    private String privateKey;
    private String publicKey;

    public KeyLoader(File file)  {
        loadKeys(file);
    }

    public void loadKeys(File file) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line = fileReader.readLine();
            while (line != null) {
                if (line.equals("Private Key:")) {
                    privateKey = fileReader.readLine();
                } else if (line.equals("Public Key:")) {
                    publicKey = fileReader.readLine();
                }
                line = fileReader.readLine();
            }
            fileReader.close();
        } catch (FileNotFoundException exception) {
            System.out.println("That file was not found!");
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            decodeKeys();
        }
    }

    public void decodeKeys() {
        byte[] decodePrivate = Base64.getDecoder().decode(privateKey);
        byte[] decodePublic = Base64.getDecoder().decode(publicKey);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodePrivate);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodePublic);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            this.keys = new KeyPair(publicKey, privateKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public KeyPair getKeys() {
        return keys;
    }

}
