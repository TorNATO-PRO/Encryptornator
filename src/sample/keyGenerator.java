package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

public class keyGenerator {

    private final KeyPair keys;

    public keyGenerator() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048, new SecureRandom());
        this.keys = keyGen.generateKeyPair();
    }

    public KeyPair getKeys() {
        return this.keys;
    }

    public void makeKeyFile(File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write("The following are your keys, keep them in a safe place!!!\n\n");
        fw.write("Public Key:\n");
        fw.write(Base64.getEncoder().encodeToString(keys.getPublic().getEncoded()));
        fw.write("\n\n");
        fw.write("Private Key:\n");
        fw.write(Base64.getEncoder().encodeToString(keys.getPrivate().getEncoded()));
        fw.close();
    }

}
