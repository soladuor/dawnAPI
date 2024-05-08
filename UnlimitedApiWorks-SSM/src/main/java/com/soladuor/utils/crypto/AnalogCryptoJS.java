package com.soladuor.utils.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * 模拟 CryptoJS AES 加密解密
 *
 * @author soladuor
 */
public class AnalogCryptoJS {

    private static final Charset utf8 = StandardCharsets.UTF_8;

    /**
     * 辅助方法：连接多个字节数组
     *
     * @param arrays byte 数组
     * @return 连接后的 byte 数组
     */
    public static byte[] connectByteArray(byte[]... arrays) {
        int length = 0;
        for (byte[] array : arrays) {
            length += array.length;
        }
        byte[] result = new byte[length];
        int offset = 0;
        for (byte[] array : arrays) {
            // 参数：原数组，复制起始点，结果数组，粘贴起始点，复制的长度
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * 辅助方法：生成长度为 8 的随机 salt
     */
    public static byte[] generateSalt() {
        // SecureRandom 是比 Random 更满足加密要求的强随机数生成器
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * 根据 salt 和秘钥生成 key 和 iv
     *
     * @param salt       salt
     * @param passphrase 密码短语
     * @return 由 key 和 iv 组成的数组
     */
    public static byte[][] getKeyAndIv(byte[] salt, String passphrase) throws NoSuchAlgorithmException {
        byte[] passphraseBytes = passphrase.getBytes(utf8);
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        // 计算 MD5 哈希值
        byte[] hash1 = md5.digest(connectByteArray(passphraseBytes, salt));
        byte[] hash2 = md5.digest(connectByteArray(hash1, passphraseBytes, salt));
        byte[] hash3 = md5.digest(connectByteArray(hash2, passphraseBytes, salt));

        // 生成 key 和 iv
        // key = hash1 + hash2
        byte[] key = connectByteArray(hash1, hash2);
        // iv = hash3
        byte[] iv = hash3;
        return new byte[][]{key, iv};
    }

    /**
     * CBC 模式的 AES 加密
     *
     * @param plaintext  明文
     * @param passphrase 密码短语
     * @return 加密后的结果字符串
     */
    public static String encrypt_AES_CBC(String plaintext, String passphrase) {
        try {
            // 将明文编码为字节串
            byte[] textBytes = plaintext.getBytes(utf8);
            // 进行填充
            // paddedText = pad(text_bytes, AES.block_size)

            // 获取 salt
            byte[] salt = generateSalt(); // 生成长度为 8 的随机 salt
            // 获取 key 和 iv
            byte[][] keyAndIV = getKeyAndIv(salt, passphrase);
            byte[] key = keyAndIV[0];
            byte[] iv = keyAndIV[1];

            // 创建 CBC 模式的 AES 加密器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            // AES 加密
            byte[] cipherText = cipher.doFinal(textBytes);
            // 添加 Salted__ 和 salt 前缀
            byte[] saltedCipherText = connectByteArray("Salted__".getBytes(utf8), salt, cipherText);

            // 返回 base64 编码的密文
            return Base64.getEncoder().encodeToString(saltedCipherText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * CBC 模式的 AES 解密
     *
     * @param ciphertext 加密文本
     * @param passphrase 密码短语
     * @return 解密后的结果字符串
     */
    public static String decrypt_AES_CBC(String ciphertext, String passphrase) {
        try {
            // 将 base64 编码的密文解码为字节串
            byte[] ciphertextBytes = Base64.getDecoder().decode(ciphertext);

            // 从密文中提取 salt
            byte[] salt = Arrays.copyOfRange(ciphertextBytes, 8, 16);
            // 获取 iv 和 key
            byte[][] keyAndIV = getKeyAndIv(salt, passphrase);
            byte[] key = keyAndIV[0];
            byte[] iv = keyAndIV[1];

            // 去除前缀与 salt
            byte[] ciphertextWithoutSalt = Arrays.copyOfRange(ciphertextBytes, 16, ciphertextBytes.length);

            // 创建 AES 解密器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            // AES 解密（自动去除填充）
            byte[] decryptedBytes = cipher.doFinal(ciphertextWithoutSalt);

            // 去除填充，转为 utf-8 字符串
            return new String(decryptedBytes, utf8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
