package org.elsamplio.localftp.test;

import org.junit.Test;
import static org.junit.Assert.*;
import org.localftp.util.HashPw;

/**
 *
 * @author Sergio
 */
public class HashTest {

    public HashTest() {
    }

    @Test
    public void hashPw() {
        System.out.println(HashPw.hashPw("Sergio25"));
    }
}
