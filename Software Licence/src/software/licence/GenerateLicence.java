/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.licence;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import java.util.Random;


/**
 *
 * @author 6
 */
public class GenerateLicence {
  

    public static void main(String[] args) {
        String userName = "Max Headroom";
        String productKey = "ABCD";
        String versionNumber = "4";

        final String licenseKey = createLicenseKey(userName, productKey, versionNumber);
        System.out.println("licenseKey = " + licenseKey);

    }

    public static String createLicenseKey(String userName, String productKey, String versionNumber) {
        final String s = userName + "|" + productKey + "|" + versionNumber;
        final HashFunction hashFunction = Hashing.sha1();
        final HashCode hashCode = hashFunction.hashString(s);
        final String upper = hashCode.toString().toUpperCase();
        return group(upper);
    }

    private static String group(String s) {
        String result = "";
        for (int i=0; i < s.length(); i++) {
            if (i%6==0 && i > 0) {
                result += '-';
            }
            result += s.charAt(i);
        }
        return result;
    }

    static String getGenerateString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}


