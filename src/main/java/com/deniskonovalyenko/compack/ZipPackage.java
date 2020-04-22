/*
 * MIT License
 *
 * Copyright (c) 2020 Denis Konovalyenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.deniskonovalyenko.compack;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

/**
 * A zip package.
 */
public final class ZipPackage implements Package {
    /**
     * A zip file.
     */
    private final ZipFile zipFile;

    /**
     * Constructs the zip package from a provided path.
     * @param path The path
     * @throws IOException If an I/O error occurs
     */
    public ZipPackage(final Path path) throws IOException {
        this(new ZipFile(path.toFile()));
    }

    /**
     * Constructs the zip package.
     * @param zipFile A zip file
     */
    public ZipPackage(final ZipFile zipFile) {
        this.zipFile = zipFile;
    }

    /**
     * Closes the package.
     * @throws IOException If I/O error occurs
     */
    @Override
    public void close() throws IOException {
        this.zipFile.close();
    }

    /**
     * Returns the package entries iterator.
     * @return The package entries iterator
     */
    @Override
    public Iterator<PackageEntry> iterator() {
        final List<PackageEntry> entries = this.zipFile.stream()
            .map(e -> new ZipPackageEntry(e, this.zipFile))
            .collect(Collectors.toList());
        return entries.iterator();
    }

    /**
     * Obtains the number of entries.
     * @return The number of entries
     */
    @Override
    public int numberOfEntries() {
        return this.zipFile.size();
    }
}
