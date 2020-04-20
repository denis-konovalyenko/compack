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

import java.io.IOException;
import java.io.InputStream;

/**
 * A binary input streams equality assertion.
 */
public final class BinaryInputStreamsEqualityAssertion implements Assertion {
    /**
     * A buffer size.
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * A description.
     */
    private final String description;

    /**
     * An actual input stream.
     */
    private final InputStream actual;

    /**
     * An expected input stream.
     */
    private final InputStream expected;

    /**
     * An actual buffer.
     */
    private final byte[] actualBuffer;

    /**
     * An expected buffer.
     */
    private final byte[] expectedBuffer;

    /**
     * Constructs the assertion with actual and expected default buffers.
     * @param description The description
     * @param actual The actual input stream
     * @param expected The expected input stream
     */
    public BinaryInputStreamsEqualityAssertion(
        final String description,
        final InputStream actual,
        final InputStream expected
    ) {
        this.description = description;
        this.actual = actual;
        this.expected = expected;
        this.actualBuffer = new byte[BUFFER_SIZE];
        this.expectedBuffer = new byte[BUFFER_SIZE];
    }

    /**
     * Affirms the assertion.
     * @throws AssertionError If the affirmation fails
     */
    @Override
    public void affirm() throws AssertionError {
        try {
            long totalFromActual = 0;
            long totalFromExpected = 0;
            int fromActual;
            int fromExpected;
            while ((this.actual.available() > 0)
                && (this.expected.available() > 0)) {
                fromActual = this.actual.read(this.actualBuffer);
                fromExpected = this.expected.read(this.expectedBuffer);
                totalFromActual += fromActual;
                totalFromExpected += fromExpected;
                if (fromActual != fromExpected) {
                    throw new AssertionError(
                        String.format(
                            // @checkstyle LineLength (1 lines)
                            "%s%nExpected number of bytes to be <%d> but was <%d>",
                            this.description,
                            totalFromExpected,
                            totalFromActual
                        )
                    );
                }
                if (fromActual > 0) {
                    new BytesEqualityAssertion(
                        String.format(
                            "%s%nAffirms that byte arrays are equal",
                            this.description
                        ),
                        this.actualBuffer,
                        this.expectedBuffer,
                        BUFFER_SIZE,
                        fromActual
                    ).affirm();
                } else {
                    break;
                }
            }
        } catch (final IOException ex) {
            throw new AssertionError(
                String.format(
                    "%s%nUnable to compare binary contents",
                    this.description
                ),
                ex
            );
        }
    }
}
