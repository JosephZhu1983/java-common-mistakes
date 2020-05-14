package org.geekbang.time.commonmistakes.sensitivedata.rsavsaes;

import org.junit.Assert;
import org.springframework.util.StopWatch;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class CommonMistakesApplication {
    private static KeyPair rsaKeyPair = genRSAKeyPair(2048);
    private static SecretKey aesKey = genAESKey(256);
    private static byte[] aesiv = genAESIV(16);
    private static int count = 100000;

    public static void main(String[] args) throws Exception {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("rsa");
        IntStream.rangeClosed(1, count).parallel().forEach(i -> rsa((UUID.randomUUID().toString() + IntStream.rangeClosed(1, 64).mapToObj(__ -> "a").collect(Collectors.joining(""))).getBytes()));
        stopWatch.stop();
        stopWatch.start("aes");
        IntStream.rangeClosed(1, count).parallel().forEach(i -> aes((UUID.randomUUID().toString() + IntStream.rangeClosed(1, 64).mapToObj(__ -> "a").collect(Collectors.joining(""))).getBytes()));
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    private static void aes(byte[] data) {
        byte[] encryptedBytes = encryptAES(data, aesKey, aesiv);
        byte[] decryptedBytes = decryptAES(encryptedBytes, aesKey, aesiv);
        Assert.assertTrue(Arrays.equals(decryptedBytes, data));
    }

    private static void rsa(byte[] data) {
        PublicKey publicKey = rsaKeyPair.getPublic();
        PrivateKey privateKey = rsaKeyPair.getPrivate();
        byte[] encryptedBytes = encryptRSA(data, publicKey);
        byte[] decryptedBytes = decryptRSA(encryptedBytes, privateKey);
        Assert.assertTrue(Arrays.equals(decryptedBytes, data));
    }

    private static KeyPair genRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keyLength);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static byte[] genAESIV(int ivLength) {
        byte[] iv = new byte[ivLength];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

    private static SecretKey genAESKey(int keyLength) {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keyLength);
            return keyGenerator.generateKey();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static byte[] decryptAES(byte[] content, SecretKey key, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
            return cipher.doFinal(content);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static byte[] encryptAES(byte[] content, SecretKey key, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
            return cipher.doFinal(content);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static byte[] encryptRSA(byte[] content, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(content);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static byte[] decryptRSA(byte[] content, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(content);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

