package com.ncr.test.pyramid.solver.impl;

import com.ncr.test.pyramid.data.Pyramid;
import com.ncr.test.pyramid.solver.PyramidSolver;

/**
 * TASK: There is something wrong here. A few things actually... 
 */
// 0. The only 'real' bug in here (making the algorithm incorrect in its basic walkthrough) was returning 0
//    instead of the 'leaf' value.
// 1. Naming. Could be more precise and self-descriptive.
// 2. currentNodeValue (f.k.a. myValue) can be retrieved in one place and then either returned or added
//    to the max value returning from recursion again in one place.
// 3. Missing handling of 'special' or invalid input, like null pyramid, empty pyramid or an incomplete one.
//    Of course, how exactly should the program handle these situations would be up to a more precise specification.
//    It's questionable whether to deal with all the 'special' cases first (via early returns) and leave the 'main'
//    workflow at the end or to structure the function differently.
public class NaivePyramidSolver implements PyramidSolver {
    @Override
    public long pyramidMaximumTotal(Pyramid pyramid) {
    	if (pyramid == null)
    	{
    		throw new IllegalArgumentException("Null was supplied as input for NaivePyramidSolver.");
    	}
    	if (pyramid.getRows() == 0)
    	{
    		return 0;
    	}
    	if (!isPyramidComplete(pyramid))
    	{
    		throw new IllegalArgumentException("An incomplete pyramid was supplied as input for NaivePyramidSolver.");
    	}
        return getMaximumTotalAbove(pyramid.getRows() - 1, 0, pyramid);
    }
    
    private long getMaximumTotalAbove(int row, int column, Pyramid pyramid) {
    	int currentNodeValue = pyramid.get(row, column);
    	
        if (row == 0) return currentNodeValue;
        
        long leftMaximumTotal  = getMaximumTotalAbove(row - 1, column, pyramid);
        long rightMaximumTotal = getMaximumTotalAbove(row - 1, column + 1, pyramid);
        return currentNodeValue + Math.max(leftMaximumTotal, rightMaximumTotal);
    }
    
	/**
	 * If we DO WANT to use this crazy recursion, better first cheaply check whether
	 * the input data is valid . . .
	 * 
	 * @param pyramid
	 * @return <b>{@code true}</b> if {@code pyramid} is complete and valid<br>
	 *         <b>{@code false}</b> otherwise
	 */
    private boolean isPyramidComplete(Pyramid pyramid)
    {
    	int rows = pyramid.getRows();
    	int[][] data = pyramid.getData();
    	for (int i = 1; i <= rows; i++)
    	{
    		int[] row = data[rows - i];
    		if (row.length < i)
    		{
    			return false;
    		}
    	}
    	return true;
    }
}