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

import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.DifferenceEvaluator;
import org.xmlunit.diff.DifferenceEvaluators;

import java.io.InputStream;

/**
 * An XML input streams equality assertion.
 */
public final class XmlInputStreamsEqualityAssertion implements Assertion {
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
     * A difference evaluator.
     * @see DifferenceEvaluator
     */
    private final DifferenceEvaluator differenceEvaluator;

    /**
     * Constructs the assertion.
     * XML prolog differences are ignored.
     * @param description The description
     * @param actual The actual input stream
     * @param expected The expected input stream
     */
    public XmlInputStreamsEqualityAssertion(
        final String description,
        final InputStream actual,
        final InputStream expected
    ) {
        this(
            description,
            actual,
            expected,
            DifferenceEvaluators.chain(
                DifferenceEvaluators.Default,
                DifferenceEvaluators.ignorePrologDifferences()
            )
        );
    }

    /**
     * Constructs the assertion.
     * @param description The description
     * @param actual The actual input stream
     * @param expected The expected input stream
     * @param differenceEvaluator The difference evaluator
     */
    // @checkstyle ParameterNumber (1 lines)
    public XmlInputStreamsEqualityAssertion(
        final String description,
        final InputStream actual,
        final InputStream expected,
        final DifferenceEvaluator differenceEvaluator
    ) {
        this.description = description;
        this.actual = actual;
        this.expected = expected;
        this.differenceEvaluator = differenceEvaluator;
    }

    /**
     * Affirms the assertion.
     * @throws AssertionError If the affirmation fails
     */
    @Override
    public void affirm() throws AssertionError {
        final Diff diff = DiffBuilder.compare(Input.fromStream(this.expected))
            .withTest(Input.fromStream(this.actual))
            .withDifferenceEvaluator(this.differenceEvaluator)
            .checkForIdentical()
            .build();
        if (diff.hasDifferences()) {
            throw new AssertionError(
                String.format(
                    "%s%n%s",
                    this.description,
                    diff.toString()
                )
            );
        }
    }
}
