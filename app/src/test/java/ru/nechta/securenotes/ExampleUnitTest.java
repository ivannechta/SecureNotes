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
            assertEquals("Encode", AES.decrypt(AES.encrypt("password")), "password");
        }
    }
    @Test
    public void Cypher_MD5_correct1() {
//        Cypher AES=new Cypher();

        assertEquals("Encode1", Cypher.md5("Hel"), "6b6e667a40e816c4da7bb4ab64cbb82b");
        assertEquals("Encode1", Cypher.md5("Hello"), "8b1a9953c4611296a827abf8c47804d7");
        assertEquals("Encode1", Cypher.md5("Hello world"), "3e25960a79dbc69b674cd4ec67a72c62");

    }

}