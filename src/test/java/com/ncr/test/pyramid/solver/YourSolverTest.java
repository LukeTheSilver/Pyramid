package com.ncr.test.pyramid.solver;

import com.ncr.test.pyramid.data.Pyramid;
import com.ncr.test.pyramid.data.PyramidGenerator;
import com.ncr.test.pyramid.data.impl.RandomPyramidGenerator;
import com.ncr.test.pyramid.solver.impl.YourSolver;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YourSolverTest {
    private static final int MAX_DEPTH = 100;

    private static final int[][] SAMPLE_DATA = {
            { 5, 9, 8, 4 },
            { 6, 4, 5, 0 },
            { 6, 7, 0, 0 },
            { 3, 0, 0, 0 }
    };
    private static final int[][] DEMO_DATA = {
            { 59, 207, 98, 95 },
            { 87,   1, 70,  0 },
            { 36,  41,  0,  0 },
            { 23,   0,  0,  0 }
    };

    protected PyramidSolver solver;

    @Before
    public void setUp() {
        solver = new YourSolver();
    }

    @Test
    public void solverHandlesSampleData() {
        Pyramid pyramid = new Pyramid(SAMPLE_DATA);
        assertEquals("Max path in Sample pyramid", 24, solver.pyramidMaximumTotal(pyramid));
    }

    @Test
    public void solverHandlesDemoData() {
        Pyramid pyramid = new Pyramid(DEMO_DATA);
        assertEquals("Max path in Demo pyramid", 353, solver.pyramidMaximumTotal(pyramid));
    }

    @Test
    public void solverSurvivesLargeData() {
        PyramidGenerator generator = new RandomPyramidGenerator(MAX_DEPTH, 1000);
        Pyramid pyramid = generator.generatePyramid();
        assertTrue("Max path in a large pyramid not positive", solver.pyramidMaximumTotal(pyramid) > 0L);
    }

    @Test
    public void solverHandlesRandomData() {
        RandomPyramidGenerator.setRandSeed(25321L);  // ensure pyramid contents
        final PyramidGenerator generator = new RandomPyramidGenerator(5, 99);
        final Pyramid pyramid = generator.generatePyramid();

        assertEquals("Max path in 'random' pyramid", 398, this.solver.pyramidMaximumTotal(pyramid));
    }
    
    /* #=======================================#
     * | ...copied from NaivePyramidSolverTest |
     * #---------------------------------------#
     */
    /* of course, in 'real situation' this would be handled differently.
     * now, I just added it in here to have a little bit more coverage ;) */
    
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
