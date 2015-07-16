package com.complexible.common.nio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import com.google.common.io.ByteSource;

/**
 * <p>Utility class for working with {@link Path}</p>
 *
 * @author  Michael Grove
 * @since   5.0
 * @version 5.0
 */
public final class Paths2 {
	private Paths2() {
		throw new AssertionError();
	}

	public static ByteSource asByteSource(final Path thePath) {
		return new ByteSource() {
			@Override
			public InputStream openStream() throws IOException {
				// will this work for non-standard FileSystem implementations such as s3?
				return Files.newInputStream(thePath);
			}
		};
	}

	public static void deleteOnExit(final Path thePath) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					delete(thePath);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void delete(final Path thePath) throws IOException {
		delete(thePath, true /* delete root */);
	}

	public static void deleteContents(final Path thePath) throws IOException {
		delete(thePath, false /* delete root */);
	}

	private static void delete(final Path thePath, final boolean theDeleteRoot) throws IOException {
		if (Files.exists(thePath)) {
			Files.walkFileTree(thePath, new SimpleFileVisitor<Path>() {

				/**
				 * {@inheritDoc}
				 */
				@Override
				public FileVisitResult postVisitDirectory(final Path theDir, final IOException theException)
					throws IOException {

					if (!theDir.equals(thePath) || (theDir.equals(thePath) && theDeleteRoot)) {
						Files.deleteIfExists(theDir);
					}

					return super.postVisitDirectory(theDir, theException);
				}

				/**
				 * {@inheritDoc}
				 */
				@Override
				public FileVisitResult visitFile(final Path theFile, final BasicFileAttributes theAttrs)
					throws IOException {

					Files.deleteIfExists(theFile);

					return super.visitFile(theFile, theAttrs);
				}
			});
		}
	}
}