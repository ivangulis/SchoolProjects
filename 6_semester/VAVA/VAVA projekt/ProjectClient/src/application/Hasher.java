package application;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * Class, that is responsible for hashing passwords and generating salt for them.
 * @author Ivan Gulis
 */
public class Hasher {
	
	/** Edited from http://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/ 
	 * Hash password */
	public String getHash(String password, String salt) throws NoSuchAlgorithmException { //catching and logging on the other side
		StringBuilder sb = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(salt.getBytes());
        byte[] bytes = md.digest(password.getBytes());
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
		return sb.toString();
	}
	
	/** Generate random salt */
	public String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException { //catching and logging on the other side
	    SecureRandom sr = null;
	    byte[] salt = new byte[16];
		sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		sr.nextBytes(salt);
	    return salt.toString();
	}
	
}
