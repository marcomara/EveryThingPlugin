package it.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Codecs {
    public static String calcSHA1String( File file ) throws IOException, NoSuchAlgorithmException {
        return bytesToHexString( calcSHA1( file ) );
    }
    public static byte[] calcSHA1( File file ) throws IOException, NoSuchAlgorithmException {
        FileInputStream fileInputStream = new FileInputStream( file );
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);
        byte[] bytes = new byte[1024];
        while (digestInputStream.read(bytes) > 0);
        byte[] resultByteArry = digest.digest();
        digestInputStream.close();
        return resultByteArry;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int value = b & 0xFF;
            if (value < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(value).toUpperCase());
        }
        return sb.toString();
    }
}
