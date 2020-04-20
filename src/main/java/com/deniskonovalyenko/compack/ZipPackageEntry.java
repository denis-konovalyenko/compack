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
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * A zip package entry.
 */
public final class ZipPackageEntry implements PackageEntry {
    /**
     * A zip entry.
     */
    private final ZipEntry entry;

    /**
     * A zip file.
     */
    private final ZipFile file;

    /**
     * Constructs the package entry.
     * @param entry A zip entry
     * @param file A zip file
     */
    public ZipPackageEntry(final ZipEntry entry, final ZipFile file) {
        this.entry = entry;
        this.file = file;
    }

    /**
     * Obtains the package entry name.
     * @return The package entry name
     */
    @Override
    public String name() {
        return this.entry.getName();
    }

    /**
     * Obtains the input stream of a package entry.
     * @return The input stream of the package entry
     * @throws IOException If I/O error occurs
     */
    @Override
    public InputStream stream() throws IOException {
        return this.file.getInputStream(this.entry);
    }
}
