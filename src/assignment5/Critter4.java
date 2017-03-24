package assignment5;

import javafx.scene.paint.Color;

/* CRITTERS Critter4.java
 * EE422C Project 5 submission by
 * Hamza Khatri
 * hak533
 * 16220
 * Slip days used: <0>
 * Mohammad Kedwaii
 * mak3799
 * 16238
 * Slip days used: <0>
 * Spring 2017
 */

public class Critter4 extends Critter{
	
	private int dir;
	private int confidence; // determines fighting/reproduction
	private int intellect; // determines fleeing
	
	public Critter4(){
		dir = Critter.getRandomInt(8);
		confidence = Critter.getRandomInt(100);
		intellect = Critter.getRandomInt(100);
	}
	
	@Override
	/**
	 * @return name of critter on the board.
	 */
	public String toString(){
		return "4";
	}
	
	@Override
	public void doTimeStep() {
		walk(dir);
		if(confidence > 90)
			reproduce(new Critter4(), Critter.getRandomInt(8));
		if(intellect > 70)
			look(dir,true);
		else if (intellect > 40)
			look(dir,false);
	}
	
	@Override
	/**
	 * Decides to fight if its confident. Will waste energy if not intellectual enough.
	 * @param oponent The opponent that the critter will be fighting
	 * @return If the critter wants to fight or not
	 */
	public boolean fight(String oponent) {
		if(confidence > 60){
			if(intellect < 70)
				run(dir);
			return true;
		}
		else{
			if(intellect < 70)
				run(dir);
			return false;
		}
	}
	
	/**
	 * Displays total # critters, avg confidence, and avg intellect.
	 * @param critters List of Critter4 objects in the population
	 */
	public static void runStats(java.util.List<Critter> critters){
		System.out.print("Total Critter4's: " + critters.size() + "\t");
		if(critters.size() > 0){
			int avgconf = 0;
			int avgint = 0;
			for(Critter c: critters){
				avgconf += ((Critter4)c).confidence;
				avgint += ((Critter4)c).intellect;
			}
			avgconf /= critters.size();
			avgint /= critters.size();
			System.out.print("Avg Confidence: " + avgconf + "\t");
			System.out.print("Avg Intellect: " + avgint);
		}
		System.out.println();
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.DIAMOND;
	}
	
	@Override
	public Color viewColor(){
		return Color.INDIANRED;
	}
}
