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
package com.deniskonovalyenko.compack.assertion;

import com.deniskonovalyenko.compack.PackageEntry;

import java.io.IOException;

/**
 * A package entries equality assertion.
 */
public final class PackageEntriesEqualityAssertion implements Assertion {
    /**
     * A description.
     */
    private final String description;

    /**
     * An actual package entry.
     */
    private final PackageEntry actual;

    /**
     * An expected package entry.
     */
    private final PackageEntry expected;

    /**
     * Constructs an assertion.
     * @param description The description
     * @param actual The actual package entry
     * @param expected The expected package entry
     */
    public PackageEntriesEqualityAssertion(
        final String description,
        final PackageEntry actual,
        final PackageEntry expected
    ) {
        this.description = description;
        this.actual = actual;
        this.expected = expected;
    }

    /**
     * Affirms the assertion.
     * @throws AssertionError If the affirmation fails
     */
    @Override
    public void affirm() throws AssertionError {
        if (!this.actual.name().equals(this.expected.name())) {
            throw new AssertionError(
                String.format(
                    "%s%nExpected entry name to be <%s> but was <%s>",
                    this.description,
                    this.expected.name(),
                    this.actual.name()
                )
            );
        }
        try {
            if (this.actual.name().endsWith(".xml")) {
                new XmlInputStreamsEqualityAssertion(
                    String.format(
                        "%s%nAffirms that the XML contents are equal",
                        this.description
                    ),
                    this.actual.stream(),
                    this.expected.stream()
                ).affirm();
            } else {
                new BinaryInputStreamsEqualityAssertion(
                    String.format(
                        "%s%nAffirms that the binary contents are equal",
                        this.description
                    ),
                    this.actual.stream(),
                    this.expected.stream()
                ).affirm();
            }
        } catch (final IOException ex) {
            throw new AssertionError(
                String.format(
                    "%s%nUnable to compare contents of entries:<%s> and:<%s>",
                    this.description,
                    this.actual.name(),
                    this.expected.name()
                ),
                ex
            );
        }

    }
}
