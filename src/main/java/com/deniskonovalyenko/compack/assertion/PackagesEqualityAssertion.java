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

import com.deniskonovalyenko.compack.Package;
import com.deniskonovalyenko.compack.PackageEntry;

import java.util.Iterator;

/**
 * A packages equality assertion.
 */
public final class PackagesEqualityAssertion implements Assertion {
    /**
     * A description.
     */
    private final String description;

    /**
     * An actual package.
     */
    private final Package actual;

    /**
     * An expected package.
     */
    private final Package expected;

    /**
     * Constructs the assertion.
     * @param description The description
     * @param actual The actual package
     * @param expected The expected package
     */
    public PackagesEqualityAssertion(
        final String description,
        final Package actual,
        final Package expected
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
        if (this.actual.numberOfEntries() != this.expected.numberOfEntries()) {
            throw new AssertionError(
                String.format(
                    "%s%nExpected number of entries to be <%d> but was <%d>",
                    this.description,
                    this.expected.numberOfEntries(),
                    this.actual.numberOfEntries()
                )
            );
        }
        final Iterator<PackageEntry> actualEntries = this.actual.iterator();
        final Iterator<PackageEntry> expectedEntries =
            this.expected.iterator();
        while (actualEntries.hasNext() && expectedEntries.hasNext()) {
            new PackageEntriesEqualityAssertion(
                String.format(
                    "%s%nAffirms that entries are equal",
                    this.description
                ),
                actualEntries.next(),
                expectedEntries.next()
            ).affirm();
        }
    }
}
