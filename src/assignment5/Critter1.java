
package assignment5;

/* CRITTERS Critter1.java
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

import javafx.scene.paint.Color;


public class Critter1 extends Critter {


    public Critter1(){
        super();
    }

    /**
     * @return ASCII representation of critter
     */
    public String toString() { return "1"; }


    /** Makes baby in random direction */
    public void doTimeStep() {

        Critter1 baby = null;
        try {
            baby = this.getClass().newInstance();
        }
        catch (InstantiationException | IllegalAccessException e){}

        reproduce(baby, Critter.getRandomInt(7));
    }


    /**
     *  Makes baby in random direction
     * Then fights (to the death)
     * @param opponent String that tells you type of opponent
     */
    public boolean fight(String opponent){

        Critter1 baby = null;
        try {
            baby = this.getClass().newInstance();
        }
        catch (InstantiationException | IllegalAccessException e){}


        int num = Critter.getRandomInt(7);

        look(num, false);
        reproduce(baby, num);

        return true;
    }

    /**
     * Displays total number of critters, along with average energy
     * @param critters List of Critter1 objects in population
     * @return String to be printed
     */
    public static String runStats (List<Critter> critters) {
        int avgNRG = 0;
        String s = "";
        s += "" + critters.size() + " critters as follows -- ";
    
        if (critters.size() > 0) {
            for (Critter c : critters) {
                avgNRG += ((Critter1)c).getEnergy();
            }
            avgNRG /= critters.size();
        }
        s += "Avg Energy: " + avgNRG + "\n";
    
        return s;
    }

    @Override
    public CritterShape viewShape() {
        return CritterShape.TRIANGLE;
    }


    @Override
    public Color viewOutlineColor() {
        return Color.MAROON;
    }

    @Override
    public Color viewFillColor() {
        return Color.DARKCYAN;
    }
}
