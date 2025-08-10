package com.test;

import lombok.extern.java.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@Log
public class SHA256_Calculator {
    public static String getFileSHA256(String filePath) throws NoSuchAlgorithmException {
        // 创建 SHA-256 摘要计算器
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // 分块读取文件（每次4KB）
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        }

        // 获取最终的哈希值（字节数组）
        byte[] hashBytes = digest.digest();

        // 转换为十六进制字符串
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b)); // 小写十六进制
        }
        return sb.toString();
    }
}
