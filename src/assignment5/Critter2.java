package assignment4;

/* CRITTERS Critter2.java
 * EE422C Project 4 submission by
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

import java.util.List;

public class Critter2 extends Critter {


    public Critter2(){
        super();
    }

    /**
     * @return ASCII representation of critter
     */
    public String toString() { return "2"; }

    /**
     * Attempts to run in random direction
     * It will only successfully move once
     * For all other attempts, it will not move but still lose energy.
     * tbh it's a dumb critter
     */
    public void doTimeStep() {
        for (int i = 0; i < (Critter.getRandomInt(3) + 1); i++) {
            run(Critter.getRandomInt(7));
        }
    }

    /**
     * Continues attempting to move until at low energy
     * Fortunately it stops before it dies (if the walk energy is really high)
     * Otherwise it would be sad :(
     * If it's lucky enough to win a fight, it can live to walk another day
     * @param opponent String that tells you type of opponent
     */
    public boolean fight(String opponent){
        while(getEnergy() > 15) {
            walk(0);
        }

        return true;
    }

    /**
     * Displays total number of critters, along with average energy
     * @param critters List of Critter2 objects in population
     */
    public static void runStats (List<Critter> critters) {
        System.out.print("Total Critter2's: " + critters.size() + "\t");
        if (critters.size() > 0) {
            int avgNRG = 0;
            for (Critter c : critters) {
                avgNRG += ((Critter1)c).getEnergy();
            }
            avgNRG /= critters.size();
            System.out.print("Avg Energy: " + avgNRG);
        }
        System.out.println();
    }

    @Override
    public CritterShape viewShape() {
        return CritterShape.SQUARE;
    }


    @Override
    public Color viewOutlineColor() {
        return Color.CHARTREUSE;
    }

    @Override
    public Color viewFillColor() {
        return Color.MEDIUMVIOLETRED;
    }

}
