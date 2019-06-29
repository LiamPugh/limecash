package tech.limelight.limecash.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * A utility class that encrypts or decrypts a file.
 * @author www.codejava.net
 *
 */
public class CryptoUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public static void encrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    public static void decrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    public static void encryptDecryptFiles(Boolean encrypt, String[] encryptedFiles, String[] unencryptedFiles, String passwd) {
        for(int i = 0; i< encryptedFiles.length; i++) {
            File encrypted_file = new File(encryptedFiles[i]);
            File unencrypted_file = new File(unencryptedFiles[i]);
            try {
                if(encrypt) CryptoUtils.encrypt(passwd, unencrypted_file, encrypted_file);
                else CryptoUtils.decrypt(passwd, encrypted_file, unencrypted_file);
            } catch (CryptoException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    private static void doCrypto(int cipherMode, String key, File inputFile,
                                 File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }

    public static void deleteFiles(String[] files){
        for(String file : files){
            List<Object> accountList = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.writeValue(new File(file), accountList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}