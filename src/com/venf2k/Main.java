package com.venf2k;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException
    {
        String MessageToHash = "Questo è un messaggio da certificare";
        byte[] TimeToken = getTimeToken();
        byte[] SessionId = getSessionId();

        String secureMessage = getStringDigestOfString( MessageToHash, TimeToken, SessionId );
        System.out.println(secureMessage); //Prints 83ee5baeea20b6c21635e4ea67847f66

        byte[] regeneratedMessageToVerify = getDigestOfString(MessageToHash, TimeToken, SessionId);
        System.out.println(BytesToString(regeneratedMessageToVerify)); //Prints 83ee5baeea20b6c21635e4ea67847f66
    }

    /*

    Create Digest of a string
    return String

    SHA1PRNG algorithm is used as cryptographically strong pseudo-random number generator based on the SHA-1 message digest algorithm.
    Note that if a seed is not provided, it will generate a seed from a true random number generator (TRNG).

    */
    private static String getStringDigestOfString(String MessageToHash, byte[] Token, byte[] SessionId)
    {
        String generatedMessage = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Add salt bytes to digest
            md.update(Token);
            //Add Session ID bytes to digest
            md.update(SessionId);
            //Get the hash's bytes
            byte[] bytes = md.digest(MessageToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedMessage = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedMessage;
    }

    /*

    Add salt

    SHA1PRNG algorithm is used as cryptographically strong pseudo-random number generator based on the SHA-1 message digest algorithm.
    Note that if a seed is not provided,
    it will generate a seed from a true random number generator (TRNG).

    */
    private static byte[] getTimeToken() throws NoSuchAlgorithmException, NoSuchProviderException
    {
        //Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        //Create array for salt
        byte[] salt = new byte[16];
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        return salt;
    }

    /*

    Get Session Id

    */
    private static byte[] getSessionId()
    {
        //Always use a SecureRandom generator
        byte[] Id = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        return Id;
    }



    /*

    Create Digest of a string
    return bytes

    SHA1PRNG algorithm is used as cryptographically strong pseudo-random number generator based on the SHA-1 message digest algorithm.
    Note that if a seed is not provided, it will generate a seed from a true random number generator (TRNG).

    */
    private static byte[] getDigestOfString(String MessageToHash, byte[] Token, byte[] SessionId)
    {
        byte[] bytes = new byte[256];
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Add salt bytes to digest
            md.update(Token);
            //Add Session ID bytes to digest
            md.update(SessionId);
            //Get the hash's bytes
            bytes = md.digest(MessageToHash.getBytes());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bytes;
    }


    /*

    Get BytesToString

    */

    private static String BytesToString(byte[] Bytes)
    {
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< Bytes.length ;i++) {
        sb.append(Integer.toString((Bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        //return complete bytes in hex format
        return sb.toString();
    }

    /*

    Create Digest of a buffer
    return bytes

    SHA1PRNG algorithm is used as cryptographically strong pseudo-random number generator based on the SHA-1 message digest algorithm.
    Note that if a seed is not provided, it will generate a seed from a true random number generator (TRNG).

    */
    private static byte[] getDigestOfBuffer(byte[] MessageToHash, byte[] Token, byte[] SessionId)
    {
        byte[] bytes = new byte[256];
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Add salt bytes to digest
            md.update(Token);
            //Add Session ID bytes to digest
            md.update(SessionId);
            //Get the hash's bytes
            bytes = md.digest(MessageToHash);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bytes;
    }

}
