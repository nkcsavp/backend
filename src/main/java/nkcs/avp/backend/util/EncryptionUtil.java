package nkcs.avp.backend.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class EncryptionUtil {
    public static final String KEY = "SHA";

    public static String getResult(String inputStr) {
        BigInteger bigInteger = null;
        try {
            MessageDigest md = MessageDigest.getInstance(KEY);
            byte[] inputData = inputStr.getBytes();
            md.update(inputData);
            bigInteger = new BigInteger(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bigInteger.toString(16);
    }


}