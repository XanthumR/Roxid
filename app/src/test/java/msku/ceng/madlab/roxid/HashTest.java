package msku.ceng.madlab.roxid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import msku.ceng.madlab.roxid.login.Hashing;

public class HashTest {


    @Test
    public void testHashPassword(){
        String input = "OzanUslan";
        String hashedPassword = Hashing.hashPassword(input);

        // I used online converter to assure hashing value
        String expectedOutput = "28fdfd9c028d1994913e1b9367dafe0dd0d3b1b43137abfe8dfb7ad0b40627073c62fd9efa4cda49dfca3a9e19b3ab626484d16f2dbb9699722783eacdd50c46";

        //Asserts that two objects are equal. If they are not, an AssertionError without a message is thrown.
        // If expected and actual are null, they are considered equal.
        assertEquals(expectedOutput, hashedPassword);

    }
}
