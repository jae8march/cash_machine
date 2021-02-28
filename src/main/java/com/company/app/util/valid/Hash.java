package com.company.app.util.valid;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Class for hashing and validation form.
 */
public class Hash {
    private static final int STEP = 12;

    private Hash() { }

    /**
     * Evaluates hash of the given string.
     *
     * @param string for hash
     * @return hash of the given string
     */
    public static String hashString(String string) {
        return BCrypt.hashpw(string, BCrypt.gensalt(STEP));
    }

    /**
     * Checks is the given string corresponds to the given hash.
     *
     * @param testString string to test
     * @param hash hash to test
     * @return true if the given string corresponds to the given hash
     */
    public static boolean isValidHash(String testString, String hash) {
        return BCrypt.checkpw(testString, hash);
    }
}
