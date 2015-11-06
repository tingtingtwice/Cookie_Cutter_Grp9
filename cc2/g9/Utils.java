package cc2.g9;

import cc2.sim.Point;
import cc2.sim.Shape;
import cc2.sim.Dough;
import cc2.sim.Move;

import java.util.*;

public class Utils {
    public Utils() {
    }
    public static Move eachQueueMove(Dough dough, Shape s, int gapOffset, int colOffset, int row, boolean flag4 ) {
        // ---------- diagonal queue ----------
    	int i = gapOffset-1;
    	if(flag4)
    	{
    		switch(row)
    		{
    		case 2: i = 2; break;
    		case 3: i = 0; break;
    		case 4: i = 2;
    		}
    	}
        for (; i <= dough.side() ; i=i+gapOffset ){
            int j = (-1)*i + colOffset;
            if (j >= 0){
                Point thisPt = new Point(i, j);
                Move thisMv = new Move(0, 2, thisPt);
                if (dough.cuts(s, thisPt)){
                    // System.out.println("Wow we found a defensive move!" + thisMv);
                    return thisMv;
                }
            }
        }
        return null;
    }
    public static Move fillInQueueMove(Dough dough, Shape s) {
        int[] colOffsets = {44, 33, 55, 22, 66, 11, 77};
        int[] densityOffsets = {3, 2, 1};
        
        for ( int densityOffset : densityOffsets) {
            for (int colOffset : colOffsets) {
                for (int i = 0; i <= dough.side(); i+= densityOffset) {
                    int j = (-1)*i + colOffset;
                    if (j >=0) {
                        Point thisPt = new Point(i, j);
                        Move thisMv = new Move(0, 2, thisPt);
                        if (dough.cuts(s, thisPt)){
                            // System.out.println("Wow we found a perfect fill in!" + thisMv);
                            return thisMv;
                        } 
                    }
                }
            }
        }
        return null;
    }
    
    public static two_tuple getSize(Shape[] shapes, int size)
    {
    	int range_i, range_j, min_i, max_i, min_j, max_j;
		min_i = 10000; min_j = 10000;
		max_i = -1; max_j = -1;
		for (Shape s : shapes)
			if (s.size() == size)
			{
				for (Point p : s)
				{
					if (p.i > max_i) max_i = p.i;
					if (p.i < min_i) min_i = p.i;
					if (p.j > max_j) max_j = p.j;
					if (p.j < min_j) min_j = p.j;
				}
			}
		range_i = max_i - min_i + 1;
		range_j = max_j - min_j + 1;
		return new two_tuple(range_i, range_j);
    }
    
    public static int getGapOffset(Shape[] opponent_shapes)
    {
    	two_tuple range = getSize(opponent_shapes, 11);
    	int longLeg = Math.max(range.range_i, range.range_j);
    	if (longLeg >= 9) return 5 - (11-longLeg);
    	else return Math.min(range.range_i, range.range_j);
    }
    
    
    public static Move getDefenseIndex(Dough dough, Shape[] shapes, Shape[] opponent_shapes ) {

        Shape[] rotations = shapes[0].rotations();
        Shape s = rotations[2];
        

        int gapOffset = getGapOffset(opponent_shapes);
        boolean flag4 = gapOffset == 4 ? true : false;

        // ---------- diagonal queue ----------
        Move firstQueue =  eachQueueMove(dough, s, gapOffset, 44, 1, false);
        if (firstQueue != null) return firstQueue;

        // ---------- 2nd to diagonal queue ----------
        Move secondLeftQueue = eachQueueMove(dough, s, gapOffset, 33, 2, flag4);
        if (secondLeftQueue != null) return secondLeftQueue;
        Move secondRightQueue = eachQueueMove(dough, s,  gapOffset, 55, 2, flag4);
        if (secondRightQueue != null) return secondRightQueue;

        // ---------- 3rd to diagonal queue ----------
        Move thirdLeftQueue = eachQueueMove(dough, s,  gapOffset, 22, 3, flag4);
        if (thirdLeftQueue != null) return thirdLeftQueue;
        Move thirdRightQueue = eachQueueMove(dough, s,  gapOffset, 66, 3, flag4);
        if (thirdRightQueue != null) return thirdRightQueue;

        // ---------- 4th to diagonal queue ----------
        Move fourthLeftQueue = eachQueueMove(dough, s,  gapOffset, 11, 4, flag4);
        if (fourthLeftQueue != null) return fourthLeftQueue;
        Move fourthRightQueue = eachQueueMove(dough, s,  gapOffset, 77, 4, flag4);
        if (fourthRightQueue != null) return fourthRightQueue;


        // ---------- filling in each queue ----------
        Move fillGap = fillInQueueMove(dough, s);
        if (fillGap != null) return fillGap;

        System.out.println("No defensive move found.");
        return null;
    }

}
