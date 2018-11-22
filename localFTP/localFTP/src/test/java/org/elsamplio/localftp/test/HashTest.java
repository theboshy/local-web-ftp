/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elsamplio.localftp.test;

import org.junit.Test;
import static org.junit.Assert.*;
import org.elsamplio.localftp.util.HashPw;

/**
 *
 * @author Sergio
 */
public class HashTest {

    public HashTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hashPw() {
        System.out.println(HashPw.hashPw("Sergio25"));
    }
}
