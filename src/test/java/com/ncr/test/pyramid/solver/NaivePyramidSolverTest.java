package com.ncr.test.pyramid.solver;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ncr.test.pyramid.data.Pyramid;
import com.ncr.test.pyramid.data.PyramidGenerator;
import com.ncr.test.pyramid.data.impl.RandomPyramidGenerator;
import com.ncr.test.pyramid.solver.impl.NaivePyramidSolver;

public class NaivePyramidSolverTest {
	private static final int[][] MISSING_POSITION = {
			{ 5, 9, 8, 4 },
            { 6 },
            { 6, 7, 0, 0 },
            { 3, 0, 0, 0 }
	};
	
	/** same as {@link #FOUR_ROWS_BASIC} but with some irrelevant additional positions */
	private static final int REDUNDANT_POSITION_RESULT = 24;
	private static final int[][] REDUNDANT_POSITION = {
			{ 5, 9, 8, 4, 2 },
            { 6, 4, 5, 0 },
            { 6, 7, 7, 0, 1, 1 },
            { 3, 0, 0, 0 }
	};
	
	private static final int FOUR_ROWS_BASIC_RESULT = 24;
	private static final int[][] FOUR_ROWS_BASIC = {
            { 5, 9, 8, 4 },
            { 6, 4, 5, 0 },
            { 6, 7, 0, 0 },
            { 3, 0, 0, 0 }
    };
	
	private static final int ONE_ROW_RESULT = 5;
	private static final int[][] ONE_ROW = {
            { 5 }
    };
	
	private static final int TWO_ROWS_RESULT = 10;
	private static final int[][] TWO_ROWS = {
            { 6, 7 },
            { 3, 0 }
    };
	
	private static final int SEVEN_ROWS_RESULT = 396;
	private static final int[][] SEVEN_ROWS = {
			{ 42, 35, 51, 15, 29, 44, 75 },
			{ 12, 72, 57, 4, 69, 97, 0 },
			{ 40, 6, 11, 22, 36, 0, 0 },
            { 51, 92, 8, 84, 0, 0, 0 },
            { 26, 14, 55, 0, 0, 0, 0 },
            { 24, 37, 0, 0, 0, 0, 0 },
            { 12, 0, 0, 0, 0, 0, 0 }
    };
	
	private static final int NEGATIVE_VALUES_RESULT = 22;
	private static final int[][] NEGATIVE_VALUES = {
            { 5, 9, 8, 4 },
            { -2, 4, 5, 0 },
            { 6, -1, 0, 0 },
            { 3, 0, 0, 0 }
    };
	
	private static final int PSEUDO_RANDOM_RESULT = 398;
	private static final int PSEUDO_RANDOM_HEIGHT = 5;
	private static final int PSEUDO_RANDOM_MAX_VALUE = 99;
	private static final long RANDOMIZER_SEED = 25321L;
	
	protected PyramidSolver solver;
	
	@Before
    public void setUp() {
        solver = new NaivePyramidSolver();
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void when_null_pyramid_throws_exception() {
		Pyramid nullPyramid = null;
        runTest("Null", nullPyramid, 0);
    }
	
	@Test
    public void when_empty_pyramid_returns_zero() {
        runTest("Empty", new int[0][0], 0);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void when_missing_position_throws_exception() {
        runTest("Missing-position", MISSING_POSITION, 0);
    }
	
	@Test
    public void when_valid_4_rows_computes() {
        runTest("4-row simple", FOUR_ROWS_BASIC, FOUR_ROWS_BASIC_RESULT);
    }
	
	@Test
    public void when_redundant_position_ignores_it() {
        runTest("Redundant-position", REDUNDANT_POSITION, REDUNDANT_POSITION_RESULT);
    }
	
	@Test
    public void when_1_value_returns_that_value() {
        runTest("Single-value", ONE_ROW, ONE_ROW_RESULT);
    }
	
	@Test
    public void when_valid_2_rows_computes() {
        runTest("2-row tiny", TWO_ROWS, TWO_ROWS_RESULT);
    }
	
	@Test
    public void when_valid_7_rows_computes() {
        runTest("7-row solid", SEVEN_ROWS, SEVEN_ROWS_RESULT);
    }
	
	@Test
    public void when_negative_values_computes() {
        runTest("Negative-values", NEGATIVE_VALUES, NEGATIVE_VALUES_RESULT);
    }
	
	@Test
    public void when_5_row_pseudo_random_computes() {
		// ensure pyramid contents
		RandomPyramidGenerator.setRandSeed(RANDOMIZER_SEED);
        final PyramidGenerator generator = new RandomPyramidGenerator(PSEUDO_RANDOM_HEIGHT, PSEUDO_RANDOM_MAX_VALUE);
        final Pyramid pyramid = generator.generatePyramid();
		
		runTest("5-row pseudo-random", pyramid, PSEUDO_RANDOM_RESULT);
    }
	
	/*
	 * Arguably, it could be seen as not good practice to use generalization and parameterization within unit tests
	 * as you don't see 'the exact line' in simple test run output. However, I have generally good experience
	 * doing it this way since typically I would debug by steps in debug mode anyway and not from console output.
	 * And besides this downside, it is always better to be able to alter whatever code by doing changes in least
	 * areas possible, even for unit tests. ...IMHO ;) */
	private void runTest(String identifier, int[][] feed, int expected) {
        Pyramid pyramid = new Pyramid(feed);
        runTest(identifier, pyramid, expected);
    }
	
	private void runTest(String identifier, Pyramid pyramid, int expected) {
        assertEquals(String.format("Max path in %s pyramid", identifier), expected, solver.pyramidMaximumTotal(pyramid));
    }
}
