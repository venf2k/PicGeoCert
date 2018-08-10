import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class DigestExample
{
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException
    {
        String MessageToHash = "Un messaggio da certificare";
        byte[] getToken = getToken();
        byte[] DroneId = getSessionId();
        
         
        String secureMessage = getDigest(MessageToHash, salt, DroneId);
        System.out.println(secureMessage); //Prints 83ee5baeea20b6c21635e4ea67847f66
         
        String regeneratedMessageToVerify = getDigest(MessageToHash, salt, DroneId);
        System.out.println(regeneratedMessageToVerify); //Prints 83ee5baeea20b6c21635e4ea67847f66
    }

    /*
    
    Create Digest of a string

    SHA1PRNG algorithm is used as cryptographically strong pseudo-random number generator based on the SHA-1 message digest algorithm.
    Note that if a seed is not provided, 
    it will generate a seed from a true random number generator (TRNG).
    
    */     
    private static String getDigest(String MessageToHash, byte[] Token, byte[] SessionId)
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
    private static byte[] getToken() throws NoSuchAlgorithmException, NoSuchProviderException
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
    
    Get a session id

    */
    private static byte[] getSessionId() 
    {
        //Always use a SecureRandom generator
        byte[] Id = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        return Id;
    }
}
