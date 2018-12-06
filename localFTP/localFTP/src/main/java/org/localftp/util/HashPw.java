/*
 * 
 */
package org.localftp.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Sergio
 */
public class HashPw {

    public static String hashPw(String pasword) {
        return BCrypt.hashpw(pasword, BCrypt.gensalt(12));
    }
}
