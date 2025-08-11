package com.ncr.test.pyramid.solver.impl;

import com.ncr.test.pyramid.data.Pyramid;
import com.ncr.test.pyramid.solver.PyramidSolver;

/**
 * TASK: This is your 3rd task.
 * Please implement the class to satisfy the interface.
 * ----------------------------------------------------
 * The implementation changes the content of the processed data structure.
 * This was not explicitly forbidden in the task description.
 * In case the original data structure has to stay intact, there's nothing
 * easier than to clone it e.g. via Arrays.copyOf ... in which case I would
 * add the functionality to the Pyramid data structure itself - to provide
 * a copy of its data.
 */
public class YourSolver implements PyramidSolver {

    @Override
    public long pyramidMaximumTotal(Pyramid pyramid) {
    	if (pyramid == null)
    	{
    		throw new IllegalArgumentException("Null was supplied as input for YourSolver.");
    	}
    	
    	int rows = pyramid.getRows();
    	if (rows == 0)
    	{
    		return 0;
    	}
    	
    	int[][] data = pyramid.getData();
    	
    	if (rows == 1)
    	{
    		return data[0][0];
    	}
    	
    	// From second row on, add the higher of the 'child' values to the node value.
    	// Like that, each processed node is always going to carry the maximum total
    	// for 'its sub-pyramid'.
    	for (int i = 1; i < rows; i++)
    	{
    		if (data[i].length < rows - i)
    		{
    			throw new IllegalArgumentException("An incomplete pyramid was supplied as input for YourSolver.");
    		}
    		for (int j = 0; j < rows - i; j++)
    		{
    			data[i][j] += Math.max(data[i-1][j], data[i-1][j+1]);
    		}
    	}
    	// At the end, the top (or bottom, if you like) of the pyramid holds the maximum total
    	// for the whole pyramid.
    	return data[rows - 1][0];
    }
    
}
