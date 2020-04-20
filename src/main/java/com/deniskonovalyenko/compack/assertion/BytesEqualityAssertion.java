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

import java.nio.charset.StandardCharsets;

/**
 * A bytes equality assertion.
 */
public final class BytesEqualityAssertion implements Assertion {
    /**
     * A description.
     */
    private final String description;

    /**
     * Actual bytes.
     */
    private final byte[] actual;

    /**
     * Expected bytes.
     */
    private final byte[] expected;

    /**
     * A size of bytes.
     */
    private final int bytesSize;

    /**
     * Maximum number of bytes to affirm.
     */
    private final int maxBytesToAffirm;

    /**
     * Constructs the assertion.
     * @param description The description
     * @param actual The actual bytes
     * @param expected The expected bytes
     * @param bytesSize The size of bytes
     * @param maxBytesToAffirm The maximum number of bytes to affirm
     */
    // @checkstyle ParameterNumber (1 lines)
    public BytesEqualityAssertion(
        final String description,
        final byte[] actual,
        final byte[] expected,
        final int bytesSize,
        final int maxBytesToAffirm
    ) {
        this.description = description;
        this.actual = actual;
        this.expected = expected;
        this.bytesSize = bytesSize;
        this.maxBytesToAffirm = maxBytesToAffirm;
    }

    /**
     * Affirms the assertion.
     * @throws AssertionError If the affirmation fails
     */
    @Override
    public void affirm() throws AssertionError {
        for (int i = 0; i < this.maxBytesToAffirm; i++) {
            if (this.actual[i] != this.expected[i]) {
                final int start = Math.max(i - 20, 0);
                final int extra;
                if (i < this.bytesSize - 11) {
                    extra = 10;
                } else {
                    extra = 1;
                }
                final String actualString = new String(
                    this.actual,
                    start,
                    i - start + extra,
                    StandardCharsets.UTF_8
                );
                final String expectedString = new String(
                    this.expected,
                    start,
                    i - start + extra,
                    StandardCharsets.UTF_8
                );
                throw new AssertionError(
                    String.format(
                        "%s%nExpected bytes to be <%s> but was <%s>",
                        this.description,
                        expectedString,
                        actualString
                    )
                );
            }
        }
    }
}
