/*-
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

import com.deniskonovalyenko.compack.assertion.Assertion;
import com.deniskonovalyenko.compack.assertion.PackagesEqualityAssertion;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipFile;

/**
 * A test suite for {@link PackagesEqualityAssertion}.
 */
public class PackagesEqualityAssertionTest {
    /**
     * Fails when packages are different.
     * @throws URISyntaxException If {@link URL#toURI()} fails
     * @throws IOException If {@link ZipFile} construction fails
     */
    @Test
    public final void fails() throws IOException, URISyntaxException {
        final Path inputPath = Paths.get(
            this.getClass().getResource("/").toURI()
        );
        final Assertion assertion = new PackagesEqualityAssertion(
            "Affirms that packages are equal",
            new ZipPackage(
                new ZipFile(
                    new File(inputPath.resolve("second.zip").toUri()),
                    ZipFile.OPEN_READ
                )
            ),
            new ZipPackage(
                new ZipFile(
                    new File(inputPath.resolve("first.zip").toUri()),
                    ZipFile.OPEN_READ
                )
            )
        );
        Assertions.assertThatThrownBy(() -> assertion.affirm())
            .isInstanceOf(AssertionError.class)
            .hasMessageContaining(
                "Affirms that packages are equal"
            );
    }

    /**
     * Passes when packages are equal.
     * @throws URISyntaxException If {@link URL#toURI()} fails
     * @throws IOException If {@link ZipFile} construction fails
     */
    @Test
    public final void passes() throws IOException, URISyntaxException {
        final Path inputPath = Paths.get(
            this.getClass().getResource("/").toURI()
        );
        new PackagesEqualityAssertion(
            "Affirms that packages are equal",
            new ZipPackage(
                new ZipFile(
                    new File(inputPath.resolve("second.zip").toUri()),
                    ZipFile.OPEN_READ
                )
            ),
            new ZipPackage(
                new ZipFile(
                    new File(inputPath.resolve("second.zip").toUri()),
                    ZipFile.OPEN_READ
                )
            )
        ).affirm();
    }
}
