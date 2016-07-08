package com.rzt.utils;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/*
 * PBKDF2 salted password hashing.
 * Author: havoc AT defuse.ca
 * www: http://crackstation.net/hashing-security.htm
 */
public class PasswordHash {

	public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

	// The following constants may be changed without breaking existing hashes.
	public static final int SALT_BYTE_SIZE = 24;
	public static final int HASH_BYTE_SIZE = 24;
	public static final int PBKDF2_ITERATIONS = 1000;

	public static final int ITERATION_INDEX = 0;
	public static final int SALT_INDEX = 1;
	public static final int PBKDF2_INDEX = 2;

	private PasswordHash()
	{
	}

	public static String getSalt()
	{
		// Generate a random salt
		SecureRandom random = new SecureRandom();

		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);
		return toHex(salt);

	}

	/**
	 * Returns a salted PBKDF2 hash of the password.
	 * 
	 * @param password
	 *            the password to hash
	 * @return a salted PBKDF2 hash of the password
	 */
	public static String createHash( String password, String salt ) throws NoSuchAlgorithmException,
			InvalidKeySpecException
	{

		char[] passwordTemp = password.toCharArray();
		byte[] saltTemp = fromHex(salt);
		// Hash the password
		byte[] hash = pbkdf2(passwordTemp, saltTemp, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
		// format iterations:salt:hash
		return toHex(hash);
	}

	/**
	 * Validates a password using a hash.
	 * 
	 * @param password
	 *            the password to check
	 * @param correctHash
	 *            the hash of the valid password
	 * @return true if the password is correct, false if not
	 */
	public static boolean validatePassword( String password, String hashedPassword, String salt )
			throws NoSuchAlgorithmException, InvalidKeySpecException
	{

		String newHash = createHash(password, salt);
		return newHash.equals(hashedPassword);
	}

	/**
	 * Computes the PBKDF2 hash of a password.
	 * 
	 * @param password
	 *            the password to hash.
	 * @param salt
	 *            the salt
	 * @param iterations
	 *            the iteration count (slowness factor)
	 * @param bytes
	 *            the length of the hash to compute in bytes
	 * @return the PBDKF2 hash of the password
	 */
	private static byte[] pbkdf2( char[] password, byte[] salt, int iterations, int bytes )
			throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
		return skf.generateSecret(spec).getEncoded();
	}

	/**
	 * Converts a string of hexadecimal characters into a byte array.
	 * 
	 * @param hex
	 *            the hex string
	 * @return the hex string decoded into a byte array
	 */
	private static byte[] fromHex( String hex )
	{
		byte[] binary = new byte[hex.length() / 2];
		for( int i = 0; i < binary.length; i++ )
		{
			binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return binary;
	}

	/**
	 * Converts a byte array into a hexadecimal string.
	 * 
	 * @param array
	 *            the byte array to convert
	 * @return a length*2 character string encoding the byte array
	 */
	private static String toHex( byte[] array )
	{
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = array.length * 2 - hex.length();
		if( paddingLength > 0 )
			return String.format("%0" + paddingLength + "d", 0) + hex;
		else
			return hex;
	}

}
