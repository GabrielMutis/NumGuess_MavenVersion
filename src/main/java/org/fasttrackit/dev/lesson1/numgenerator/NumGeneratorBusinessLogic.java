package org.fasttrackit.dev.lesson1.numgenerator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by condor on 29/11/14.
 * FastTrackIT, 2015
 */


/* didactic purposes

Some items are intentionally not optimized or not coded in the right way

FastTrackIT 2015

*/

public class NumGeneratorBusinessLogic {


    private static final Logger LOGGER = Logger.getLogger( NumGeneratorBusinessLogic.class.getName() );


    private static final int MAX_NUMBER = 6;

    private boolean isFirstTime = true;
    private boolean successfulGuess;
    private int numberOfGuesses;
    private int generatedNumber;
    private String hint;

    private long counter_guess_start;

    public double getCounter_guess_stop() {
        return counter_guess_stop;
    }

    private double counter_guess_stop;


    public boolean isMinimalScore() {
        return isMinimalScore;
    }

    private boolean isMinimalScore;

    public NumGeneratorBusinessLogic(){
        resetNumberGenerator();
    }

    public boolean getSuccessfulGuess(){
        return successfulGuess;
    }

    public String getHint(){
        return hint;
    }

    public int getNumGuesses(){
        return numberOfGuesses;
    }

    public boolean isFirstTime(){
        return isFirstTime;
    }

    public void resetNumberGenerator(){
        isFirstTime = true;
        numberOfGuesses = 0;
        hint = "";
        counter_guess_start=0L;
        isMinimalScore=false;
    }

    public boolean determineGuess(int guessNumber){
        if (isFirstTime) {
            generatedNumber = NumGenerator.generate(MAX_NUMBER);
            System.out.println("gennr:"+generatedNumber);
            isFirstTime = false;

            // start counter_guess
            counter_guess_start=System.currentTimeMillis();

        }
        numberOfGuesses++;
        if (guessNumber == generatedNumber) {
            hint="";
            successfulGuess = true;
            counter_guess_stop=(System.currentTimeMillis()-counter_guess_start)/1000.0;

            // to do for increment 3
            // write this in a singleton
            // see if this is the best result
            // print smething if I am the best so far

            MyListOfHallOfFame hallOfFameList = MyListOfHallOfFame.getInstance();
            hallOfFameList.addItem(counter_guess_stop);

            // see if I am the best
            HallOfFame minHall= hallOfFameList.getMinScore();
            if(Double.compare(minHall.getScore(),counter_guess_stop)==0) // the right way to compare floating point numbers
            {
                // I am the new winner
                System.out.println("I am the new winner, the minimal score:"+minHall.getScore());
                LOGGER.log(Level.FINER, "I am the new winner, the minimal score:"+minHall.getScore());
                isMinimalScore=true;

            }


            // send mail all the time
            LOGGER.log(Level.FINE,"start - sending email business logic");
            SendMail sm = new SendMail(numberOfGuesses, guessNumber, counter_guess_stop, "ionel_condor@yahoo.com");
            sm.sendEmail();
            LOGGER.log(Level.FINE,"stop - sending email business logic");


        } else if (guessNumber < generatedNumber) {
            hint = "higher";
            successfulGuess = false;
        } else if (guessNumber > generatedNumber) {
            hint = "lower";
            successfulGuess = false;
        }
        return successfulGuess;
    }


}