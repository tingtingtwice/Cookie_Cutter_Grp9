package cc2.g9;

import cc2.sim.Point;
import cc2.sim.Shape;
import cc2.sim.Dough;
import cc2.sim.Move;

import java.util.*;

public class Utils {
    public Utils() {
    }
    // public static Boolean isComplement(ArrayList<Move> past11Moves, Move curMove){
    // 	for(int i=0; i<past11Moves.size(); i++){
    // 		Move pastMove = past11Moves.get(i);
    // 		if(pastMove.rotation != curMove.rotation && distance(pastMove.point, curMove.point) >=7 ){
    // 			// return true;
    // 		}
    // 	}
    // 	return false;
    // }
    public static double distance(Point a, Point b){
    	return Math.sqrt(Math.pow(a.i - b.i, 2) + Math.pow(a.j - b.j, 2)); 
    }
    public static Move eachQueueMove(Dough dough, Shape s, int gapOffset, int colOffset ) {
        // ---------- diagonal queue ----------
        for (int i = 2 ; i <= dough.side() ; i=i+gapOffset ){
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
    public static Move getDefenseIndex(Dough dough, Shape[] shapes) {

        Shape[] rotations = shapes[0].rotations();
        Shape s = rotations[2];

        int gapOffset = 5;

        // ---------- diagonal queue ----------
        Move firstQueue =  eachQueueMove(dough, s, gapOffset, 44);
        if (firstQueue != null) return firstQueue;

        // ---------- 2nd to diagonal queue ----------
        Move secondLeftQueue = eachQueueMove(dough, s, gapOffset, 33);
        if (secondLeftQueue != null) return secondLeftQueue;
        Move secondRightQueue = eachQueueMove(dough, s,  gapOffset, 55);
        if (secondRightQueue != null) return secondRightQueue;

        // ---------- 3rd to diagonal queue ----------
        Move thirdLeftQueue = eachQueueMove(dough, s,  gapOffset, 22);
        if (thirdLeftQueue != null) return thirdLeftQueue;
        Move thirdRightQueue = eachQueueMove(dough, s,  gapOffset, 66);
        if (thirdRightQueue != null) return thirdRightQueue;

        // ---------- 4th to diagonal queue ----------
        Move fourthLeftQueue = eachQueueMove(dough, s,  gapOffset, 11);
        if (fourthLeftQueue != null) return fourthLeftQueue;
        Move fourthRightQueue = eachQueueMove(dough, s,  gapOffset, 77);
        if (fourthRightQueue != null) return fourthRightQueue;

        System.out.println("No defensive move found.");
        return null;
    }

}