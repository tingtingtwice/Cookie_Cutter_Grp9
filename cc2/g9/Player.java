package cc2.g9;

import cc2.sim.Point;
import cc2.sim.Shape;
import cc2.sim.Dough;
import cc2.sim.Move;

import java.util.*;

public class Player implements cc2.sim.Player {

	private boolean[] row_2 = new boolean [0];

	private Random gen = new Random();

	public Shape cutter(int length, Shape[] shapes, Shape[] opponent_shapes)
	{
		// check if first try of given cutter length
		Point[] cutter = new Point [length];
		if (row_2.length != cutter.length - 1) {
			// save cutter length to check for retries
			row_2 = new boolean [cutter.length - 1];
			for (int i = 0 ; i != cutter.length ; ++i)
				cutter[i] = new Point(i, 0);
		} else {
			// pick a random cell from 2nd row but not same
			int i;
			do {
				i = gen.nextInt(cutter.length - 1);
			} while (row_2[i]);
			row_2[i] = true;
			cutter[cutter.length - 1] = new Point(i, 1);
			for (i = 0 ; i != cutter.length - 1 ; ++i)
				cutter[i] = new Point(i, 0);
		}
		return new Shape(cutter);
	}

	public Move cut(Dough dough, Shape[] shapes, Shape[] opponent_shapes)
	{
		// prune larger shapes if initial move
		if (dough.uncut()) {
			int min = Integer.MAX_VALUE;
			for (Shape s : shapes)
				if (min > s.size())
					min = s.size();
			for (int s = 0 ; s != shapes.length ; ++s)
				if (shapes[s].size() != min)
					shapes[s] = null;
		}
		// find all valid cuts
		ArrayList <Move> moves = new ArrayList <Move> ();
		ArrayList <Move> moves11 = new ArrayList <Move> ();
		ArrayList <Move> moves8 = new ArrayList <Move> ();
		ArrayList <Move> moves5 = new ArrayList <Move> ();
		for (int i = 0 ; i != dough.side() ; ++i)
			for (int j = 0 ; j != dough.side() ; ++j) {
				Point p = new Point(i, j);
				for (int si = 0 ; si != shapes.length ; ++si) {
					if (shapes[si] == null) continue;
					Shape[] rotations = shapes[si].rotations();
					for (int ri = 0 ; ri != rotations.length ; ++ri) {
						Shape s = rotations[ri];
						if (dough.cuts(s, p)){
							if(shapes[si].size() == 11){
								moves11.add(new Move(si,ri,p));
							}
							else if(shapes[si].size() == 8){
								moves8.add(new Move(si,ri,p));
							}
							else{
								moves5.add(new Move(si,ri,p));
							}
						}
					}
				}
			}
		int this_index;
		Move this_move;
		// return a cut randomly
		System.out.println("size of 11: " + moves11.size());
		System.out.println("size of 8: " + moves8.size());
		System.out.println("size of 5: " + moves5.size());
		if(moves11.size()>0){
			this_index = gen.nextInt(moves11.size());
			this_move =  moves11.get(this_index);
			moves11.remove(this_index);
			return this_move;			
		}
		else if(moves8.size()>0){
			this_index = gen.nextInt(moves8.size());
			this_move =  moves8.get(this_index);
			moves8.remove(this_index);
			return this_move;						
		}
		else {
			this_index = gen.nextInt(moves5.size());
			this_move =  moves5.get(this_index);
			moves5.remove(this_index);
			return this_move;			
		}
	}
}
