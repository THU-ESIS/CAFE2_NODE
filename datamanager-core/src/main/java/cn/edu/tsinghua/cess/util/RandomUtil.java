package cn.edu.tsinghua.cess.util;

import java.security.SecureRandom;

/**
 * Created by yanke on 2015-12-05 16:36
 */
public class RandomUtil {

    private static SecureRandom randomSource = new SecureRandom();
    private static String stringTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static int stringTableLength = stringTable.length();

    public static String randomString(int length) {
        return randomSequence(stringTable, stringTableLength, length);
    }

    private static String randomSequence(String table, int tableLength, int targetSequenceLength) {
        char[] sequence = new char[targetSequenceLength];

        for (int i = 0; i < targetSequenceLength; i++) {
            int index = randomSource.nextInt(tableLength);
            sequence[i] = table.charAt(index);
        }

        return new String(sequence);
    }

}
