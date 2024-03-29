/**
 * This class is used to generate auto number entries for the lucky numbers competition.
 *
 * @author Xinyi Wu
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class AutoNumbersEntry extends NumbersEntry {
    private final int NUMBER_COUNT = 7;
    private final int MAX_NUMBER = 35;
    /**
     *  create the auto number entry
     *  @param seed    the seed to generate random numbers
     */
    public int[] createNumbers (int seed) {
        ArrayList<Integer> validList = new ArrayList<Integer>();
	    int[] tempNumbers = new int[NUMBER_COUNT];
        for (int i = 1; i <= MAX_NUMBER; i++) {
    	    validList.add(i);
        }
        Collections.shuffle(validList, new Random(seed));
        for (int i = 0; i < NUMBER_COUNT; i++) {
    	    tempNumbers[i] = validList.get(i);
        }
        //sort the temporary numbers
        Arrays.sort(tempNumbers);
        return tempNumbers;
    }
}

