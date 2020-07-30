package ru.nechta.securenotes;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void Cypher_correct() {
        Cypher AES=new Cypher();
        for (int i=0;i<10;i++) {
            AES.secret = "Hello"+i;
            assertEquals("Encode", AES.decrypt(AES.encrypt("password")), ""+i);
        }
    }
}