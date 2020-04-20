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

import com.deniskonovalyenko.compack.assertion.Assertion;
import com.deniskonovalyenko.compack.assertion.BytesEqualityAssertion;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * A test suite for {@link BytesEqualityAssertion}.
 */
public class BytesEqualityAssertionTest {
    /**
     * Fails when bytes are different.
     */
    @Test
    public final void fails() {
        final Assertion assertion = new BytesEqualityAssertion(
            "Affirms that byte arrays are equal",
            new byte[] {12, 34, 56},
            new byte[] {12, 34, 35},
            3,
            3
        );
        Assertions.assertThatThrownBy(() -> assertion.affirm())
            .isInstanceOf(AssertionError.class)
            .hasMessageContaining(
                "Affirms that byte arrays are equal"
            );
    }

    /**
     * Passes when bytes are equal.
     */
    @Test
    public final void passes() {
        new BytesEqualityAssertion(
            "Affirms that byte arrays are equal",
            new byte[] {12, 34, 56},
            new byte[] {12, 34, 56},
            3,
            3
        ).affirm();
    }

    /**
     * Passes when bytes with maximum to affirm are equal.
     */
    @Test
    public final void passesWithMaxBytesToAffirm() {
        new BytesEqualityAssertion(
            "Affirms that byte arrays are equal",
            new byte[] {12, 34, 56},
            new byte[] {12, 34, 35},
            3,
            2
        ).affirm();
    }
}
