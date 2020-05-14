package org.geekbang.time.commonmistakes.sensitivedata.storeidcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CipherService {
    //密钥长度
    public static final int AES_KEY_SIZE = 256;
    //初始化向量长度
    public static final int GCM_IV_LENGTH = 12;
    //GCM身份认证Tag长度
    public static final int GCM_TAG_LENGTH = 16;
    @Autowired
    private CipherRepository cipherRepository;

    //内部加密方法
    public static byte[] doEncrypt(byte[] plaintext, SecretKey key, byte[] iv, byte[] aad) throws Exception {
        //加密算法
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        //Key规范
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        //GCM参数规范
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        //加密模式
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
        //设置aad
        if (aad != null)
            cipher.updateAAD(aad);
        //加密
        byte[] cipherText = cipher.doFinal(plaintext);
        return cipherText;
    }

    //内部解密方法
    public static String doDecrypt(byte[] cipherText, SecretKey key, byte[] iv, byte[] aad) throws Exception {
        //加密算法
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        //Key规范
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        //GCM参数规范
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        //解密模式
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
        //设置aad
        if (aad != null)
            cipher.updateAAD(aad);
        //解密
        byte[] decryptedText = cipher.doFinal(cipherText);
        return new String(decryptedText);
    }

    //加密入口
    public CipherResult encrypt(String data, String aad) throws Exception {
        //加密结果
        CipherResult encryptResult = new CipherResult();
        //密钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        //生成密钥
        keyGenerator.init(AES_KEY_SIZE);
        SecretKey key = keyGenerator.generateKey();
        //IV数据
        byte[] iv = new byte[GCM_IV_LENGTH];
        //随机生成IV
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        //处理aad
        byte[] aaddata = null;
        if (!StringUtils.isEmpty(aad))
            aaddata = aad.getBytes();
        //获得密文
        encryptResult.setCipherText(Base64.getEncoder().encodeToString(doEncrypt(data.getBytes(), key, iv, aaddata)));
        //加密上下文数据
        CipherData cipherData = new CipherData();
        //保存IV
        cipherData.setIv(Base64.getEncoder().encodeToString(iv));
        //保存密钥
        cipherData.setSecureKey(Base64.getEncoder().encodeToString(key.getEncoded()));
        cipherRepository.save(cipherData);
        //返回本地加密ID
        encryptResult.setId(cipherData.getId());
        return encryptResult;
    }

    //解密入口
    public String decrypt(long cipherId, String cipherText, String aad) throws Exception {
        //使用加密ID找到加密上下文数据
        CipherData cipherData = cipherRepository.findById(cipherId).orElseThrow(() -> new IllegalArgumentException("invlaid cipherId"));
        //加载密钥
        byte[] decodedKey = Base64.getDecoder().decode(cipherData.getSecureKey());
        //初始化密钥
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        //加载IV
        byte[] decodedIv = Base64.getDecoder().decode(cipherData.getIv());
        //处理aad
        byte[] aaddata = null;
        if (!StringUtils.isEmpty(aad))
            aaddata = aad.getBytes();
        //解密
        return doDecrypt(Base64.getDecoder().decode(cipherText.getBytes()), originalKey, decodedIv, aaddata);
    }
}
