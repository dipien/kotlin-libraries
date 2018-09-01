package com.jdroid.java.utils;

import com.jdroid.java.exception.UnexpectedException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum Hasher {

	SHA_1("SHA-1"),
	SHA_512("SHA-512");

	private String algorithm;

	private Hasher(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * Algorithm that returns a Hashed string of the value given as parameter
	 *
	 * @param value the string to hash
	 * @return String hashed value.
	 */
	public String hash(String value) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.reset();
			messageDigest.update(value.getBytes());
			byte[] hashed = messageDigest.digest();
			return EncryptionUtils.toHex(hashed);
		} catch (NoSuchAlgorithmException e) {
			throw new UnexpectedException(e);
		}
	}

	public Boolean isSupported() {
		try {
			MessageDigest.getInstance(algorithm);
			return true;
		} catch (NoSuchAlgorithmException e) {
			return false;
		}
	}

	public static Hasher getSupportedHasher() {
		if (Hasher.SHA_512.isSupported()) {
			return Hasher.SHA_512;
		} else {
			return Hasher.SHA_1;
		}
	}
}
