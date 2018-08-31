package com.jdroid.java.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class StreamUtils {

	public static boolean isEquals(InputStream input1, InputStream input2) {
		try {
			byte[] buffer1 = new byte[1024];
			byte[] buffer2 = new byte[1024];
			try {
				int numRead1 = 0;
				int numRead2 = 0;
				while (true) {
					numRead1 = input1.read(buffer1);
					numRead2 = input2.read(buffer2);
					if (numRead1 > -1) {
						if (numRead2 != numRead1) return false;
						// Otherwise same number of bytes read
						if (!Arrays.equals(buffer1, buffer2)) return false;
						// Otherwise same bytes read, so continue ...
					} else {
						// Nothing more in stream 1 ...
						return numRead2 < 0;
					}
				}
			} finally {
				FileUtils.safeClose(input1);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			FileUtils.safeClose(input2);
		}
	}
}
