package tech.limelight.limecash;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.limelight.limecash.util.CryptoException;
import tech.limelight.limecash.util.CryptoUtils;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LimecashApplicationTests {
    public static void main(String[] args) {
        String key = "Mary has one cat1";
        File inputFile = new File("document.txt");
        File encryptedFile = new File("document.encrypted");
        File decryptedFile = new File("document.decrypted");

        try {
            CryptoUtils.encrypt(key, inputFile, encryptedFile);
            CryptoUtils.decrypt(key, encryptedFile, decryptedFile);
        } catch (CryptoException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
