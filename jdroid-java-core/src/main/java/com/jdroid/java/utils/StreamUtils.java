package com.jdroid.java.utils;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.exception.UnexpectedException;

import org.slf4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class StreamUtils {

	private static final Logger LOGGER = LoggerUtils.getLogger(StreamUtils.class);

	private static final int BUFFER_SIZE = 16384;

	/**
	 * Reads the inputStream and returns a byte array with all the information
	 *
	 * @param inputStream The inputStream to be read
	 * @return A byte array with all the inputStream's information
	 * @throws IOException The exception thrown when an error reading the inputStream happens
	 */
	public static byte[] readAsBytes(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		InputStream is = new BufferedInputStream(inputStream);
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			int cnt = is.read(buffer);
			while (cnt != -1) {
				outputStream.write(buffer, 0, cnt);
				cnt = is.read(buffer);
			}
			return outputStream.toByteArray();
		} finally {
			safeClose(is);
		}
	}

	public static List<String> readLines(InputStream inputStream) {
		BufferedReader reader = null;
		try {
			List<String> lines = Lists.INSTANCE.newArrayList();
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			return lines;
		} catch (IOException e) {
			throw new UnexpectedException("Error reading the stream", e);
		} finally {
			safeClose(reader);
		}
	}

	/**
	 * Receives an InputStream and iterates over all its lines and returns a String.
	 *
	 * @param in the InputStream to be converted
	 * @return The content of the file as String
	 */
	public static String toString(InputStream in) {
		return toString(in, true);
	}

	public static String toString(InputStream in, Boolean closeStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder contentBuilder = new StringBuilder();
		String text = null;

		// repeat until all lines are read
		try {
			Boolean firstLine = true;
			while ((text = reader.readLine()) != null) {
				if (!firstLine) {
					contentBuilder.append(System.getProperty("line.separator"));
				}
				firstLine = false;
				contentBuilder.append(text);
			}
		} catch (IOException e) {
			throw new UnexpectedException("Error reading the stream", e);
		} finally {
			if (closeStream) {
				safeClose(in);
			}
		}
		return contentBuilder.toString();
	}

	/**
	 * @param source the source {@link InputStream}
	 * @param destin the destin {@link OutputStream}
	 * @param closeOutputStream
	 */
	public static void copyStream(InputStream source, OutputStream destin, Boolean closeOutputStream) {
		try {
			int count = 0;
			byte[] buffer = new byte[StreamUtils.BUFFER_SIZE];
			while ((count = source.read(buffer, 0, StreamUtils.BUFFER_SIZE)) != -1) {
				destin.write(buffer, 0, count);
			}
		} catch (IOException e) {
			throw new UnexpectedException("Error copying file", e);
		} finally {
			if (closeOutputStream) {
				safeClose(destin);
			}
		}
	}

	/**
	 * @param source the source {@link InputStream}
	 * @param destin the destin {@link OutputStream}
	 */
	public static void copyStream(InputStream source, OutputStream destin) {
		copyStream(source, destin, true);
	}

	/**
	 * @param source the source {@link InputStream}
	 * @return the input stream that can be reset {@link ByteArrayInputStream}
	 */
	public static ByteArrayInputStream copy(InputStream source) {
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		copyStream(source, tmp, true);
		return new ByteArrayInputStream(tmp.toByteArray());
	}

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
				StreamUtils.safeClose(input1);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			StreamUtils.safeClose(input2);
		}
	}

	public static void safeClose(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				LOGGER.warn("Exception thrown when trying to close the closeable", e);
			}
		}
	}
}
