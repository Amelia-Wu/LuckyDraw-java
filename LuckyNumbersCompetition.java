/*
 * Student name: Xinyi Wu
 * Student ID: 1306826
 * LMS username: wuxw13
 */

/**
 * This class is used to process all the lucky numbers competition and the methods of
 * adding entries and drawing winners.
 *
 * @author Xinyi Wu
 *
 */
import java.util.*;

public class LuckyNumbersCompetition extends Competition {
    final static int USER_ENTRY = 7;
    ArrayList<NumbersEntry> lstAllEntry = new ArrayList<>();
    ArrayList<NumbersEntry> lstWinningEntries = new ArrayList<>();
    ArrayList<String> lstWinningMembers = new ArrayList<>();

    int totalPrize = 0;
    final static int MAX = 35;
    final static int MIN = 1;
    final static int FIRST_PRIZE = 50000;
    final static int SECOND_PRIZE = 5000;
    final static int THIRD_PRIZE = 1000;
    final static int FOURTH_PRIZE = 500;
    final static int FIFTH_PRIZE = 100;
    final static int SIXTH_PRIZE = 50;
    final static int WINNING_SEVEN = 7;
    final static int WINNING_SIX = 6;
    final static int WINNING_FIVE = 5;
    final static int WINNING_FOUR = 4;
    final static int WINNING_THREE = 3;
    final static int WINNING_TWO = 2;
    final static int BILL_AMOUNT_INDEX = 2;
    final static int MAX_MANUAL_ENTRY = 6;

    /**
     *  Allows the user add entries to the competition
     *  @param commandScanner    the scanner to receive the commands
     */
    @Override
    public void addEntries(Scanner commandScanner) {
        String[] lstBillId = getColumn(DataProvider.billRecords, 0);

        int rowBillId = 0;
        String stringBillId = null;
        String stringMemberId = null;

        //get the scanned bill ID
        System.out.println("Bill ID: ");
        stringBillId = commandScanner.nextLine();

        if (!stringBillId.matches("-?\\d+(\\.\\d+)?")){
            System.out.println("Invalid bill id! It must be a 6-digit number. Please try again.");
            addEntries(commandScanner);
        }

        //if the bill ID input is in the valid list
        if (Arrays.asList(lstBillId).contains(stringBillId)) {
            rowBillId = Arrays.asList(lstBillId).indexOf(stringBillId);
            stringMemberId = DataProvider.billRecords.get(rowBillId).get(1);

            if (lstUsedBillId.size() == 0) {
                lstUsedBillId.add(stringBillId);
            } else {
                //if the bill ID has been used, change another bill ID
                if (lstUsedBillId.contains(stringBillId)) {
                    System.out.println("This bill has already been used for a competition. Please try again.");
                    addEntries(commandScanner);
                } else {
                    lstUsedBillId.add(stringBillId);
                }
            }

            if (stringMemberId.equals("")) {
                System.out.println("This bill has no member id. Please try again.");
                addEntries(commandScanner);
            }

        } else {
            System.out.println("This bill does not exist. Please try again.");
            addEntries(commandScanner);
        }

        float billAmount = Float.parseFloat(DataProvider.billRecords.get(rowBillId).get(BILL_AMOUNT_INDEX));
        int eligibleEntry = (int) (billAmount / SINGLE_BILL);
        System.out.println("This bill ($" + billAmount + ") is eligible for " + eligibleEntry + " entries. " +
                "How many manual entries did the customer fill up?: ");

        manualEntryNum(commandScanner, stringBillId, eligibleEntry);
    }
    /**
     *  check if the manual entry number is valid
     *  @param commandScanner    the scanner to receive the commands
     *  @param stringBillId    the bill ID passed from previous method
     *  @param eligibleEntry    the eligible Entry passed from previous method
     */
    public void manualEntryNum(Scanner commandScanner, String stringBillId, int eligibleEntry) {
        int manualEntryNum = 0;
        try {
            manualEntryNum = commandScanner.nextInt();
            if (manualEntryNum < 0 | manualEntryNum > MAX_MANUAL_ENTRY) {
                System.out.println("The number must be in the range from 0 to 6. Please try again.");
                manualEntryNum(commandScanner, stringBillId, eligibleEntry);
            }
        } catch (InputMismatchException e) {
            System.out.println("A number is expected. Please try again.");
            commandScanner.nextLine();
            manualEntryNum(commandScanner, stringBillId, eligibleEntry);
        }
        commandScanner.nextLine();
        manualEntries(commandScanner, stringBillId, manualEntryNum, eligibleEntry);
    }
    /**
     *  check if the manual entry number is valid
     *  @param commandScanner    the scanner to receive the commands
     *  @param stringBillId    the bill ID passed from previous method
     *  @param eligibleEntry    the eligible Entry passed from previous method
     *  @param manualEntryNum    the number of manual entry
     */
    public void manualEntries(Scanner commandScanner, String stringBillId,
                              int manualEntryNum, int eligibleEntry) {

        String[] lstBillId = getColumn(DataProvider.billRecords, 0);
        int rowBillId = Arrays.asList(lstBillId).indexOf(stringBillId);
        String stringMemberId = DataProvider.billRecords.get(rowBillId).get(1);

        if (manualEntryNum > 0) {
            System.out.println("Please enter " + USER_ENTRY +
                    " different numbers (from the range 1 to 35) separated by whitespace.");
            String manualEntryList = commandScanner.nextLine();
            String[] splitEntryList = new String[0];

            try {
                splitEntryList = manualEntryList.split(" ");

                //set the range of the entry
                boolean outOfBound = false;
                for (int i = 0; i < splitEntryList.length; i++) {
                    if (Integer.parseInt(splitEntryList[i]) > MAX) {
                        outOfBound = true;
                    }
                }

                //check if the format of manual entry is valid
                if (splitEntryList.length < USER_ENTRY) {
                    System.out.println("Invalid input! Fewer than 7 numbers are provided. Please try again!");
                    manualEntries(commandScanner, stringBillId, manualEntryNum, eligibleEntry);
                } else if (splitEntryList.length > USER_ENTRY) {
                    System.out.println("Invalid input! More than 7 numbers are provided. Please try again!");
                    manualEntries(commandScanner, stringBillId, manualEntryNum, eligibleEntry);
                } else if (Arrays.stream(splitEntryList).distinct().count() != splitEntryList.length) {
                    System.out.println("Invalid input! All numbers must be different!");
                    manualEntries(commandScanner, stringBillId, manualEntryNum, eligibleEntry);
                } else if (outOfBound) {
                    System.out.println("Invalid input! All numbers must be in the range from 1 to 35!");
                    manualEntries(commandScanner, stringBillId, manualEntryNum, eligibleEntry);
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Numbers are expected. Please try again!");
                manualEntries(commandScanner, stringBillId, manualEntryNum, eligibleEntry);

            } finally {
                System.out.println("The following entries have been added:");

                // convert the String array to int array
                int[] finalEntries = new int[splitEntryList.length];
                for (int i = 0; i < splitEntryList.length; i++) {
                    finalEntries[i] = Integer.parseInt(splitEntryList[i]);
                }
                Arrays.sort(finalEntries);

                //print all the manual entries
                int manualStartEntryId;
                int manualCloseEntryId;

                if (lstAllEntry.size() == 0) {
                    manualStartEntryId = 1;
                } else {
                    manualStartEntryId = lstAllEntry.get(lstAllEntry.size()-1).getEntryId() + 1;
                }
                manualCloseEntryId = manualStartEntryId + manualEntryNum;

                for (int i = manualStartEntryId; i < manualCloseEntryId; i++) {
                    System.out.print("Entry ID: ");
                    System.out.printf("%-7d", i);
                    System.out.print("Numbers:");

                    NumbersEntry numbersEntry = new NumbersEntry();
                    lstAllEntry.add(numbersEntry);
                    numbersEntry.setEntryId(i);
                    numbersEntry.setBillId(stringBillId);
                    numbersEntry.setMemberId(stringMemberId);
                    numbersEntry.setNumbers(finalEntries);

                    for (int num : finalEntries) {
                        System.out.printf("%3d", num);
                    }
                    System.out.print("\n");
                }
            }
        } else if (manualEntryNum == 0) {
            System.out.println("The following entries have been added:");
        }

        //the part of automatic entries
        int[] autoEntries;
        int autoStartEntryId;
        int autoCloseEntryId;
        if (lstAllEntry.size() == 0) {
            autoStartEntryId = 1;
            autoCloseEntryId = Integer.parseInt(String.valueOf(eligibleEntry)) + 1;
        } else {
            autoStartEntryId = lstAllEntry.get(lstAllEntry.size()-1).getEntryId() + 1;
            autoCloseEntryId = autoStartEntryId + Integer.parseInt(String.valueOf(eligibleEntry)) - manualEntryNum;
        }

        //print all the auto entries
        for (int j = autoStartEntryId; j < autoCloseEntryId; j++) {
            System.out.print("Entry ID: ");
            System.out.printf("%-7d", j);
            System.out.print("Numbers:");
            AutoNumbersEntry autoNumEntry = new AutoNumbersEntry();

            // in testing mode, seed is the number of entries in the currently active competition
            if (SimpleCompetitions.lstSimpleCompetition.get(0).getIsTestingMode()) {
                autoEntries = autoNumEntry.createNumbers(j-1);
            } else {
                autoEntries = new int[USER_ENTRY];
                for (int i = 0; i < USER_ENTRY; i++) {
                    Random rand = new Random();
                    //set the range of random numbers as: from 1 to 35
                    int number = rand.nextInt((MAX - MIN) + 1) + MIN;
                    autoEntries[i] = number;
                }
            }

            NumbersEntry numbersEntry = new NumbersEntry();
            lstAllEntry.add(numbersEntry);
            numbersEntry.setEntryId(j);
            numbersEntry.setBillId(stringBillId);
            numbersEntry.setMemberId(stringMemberId);
            numbersEntry.setNumbers(autoEntries);

            for (int autoNum: autoEntries) {
                System.out.printf("%3d", autoNum);
            }
            System.out.print(" [Auto]\n");
        }

        moreEntries(commandScanner);
    }
    /**
     *  check if the user wants to add more entries
     *  @param commandScanner    the scanner to receive the commands
     */
    public void moreEntries(Scanner commandScanner) {
        System.out.println("Add more entries (Y/N)?");
        String addMoreEntries = commandScanner.nextLine();
        if (addMoreEntries.equals("Y")) {
            addEntries(commandScanner);
        } else if(addMoreEntries.equals("N")) {
            this.setEntryNum(lstAllEntry.size());
            SimpleCompetitions.lstSimpleCompetition.get(0).setActiveComp(1);
            SimpleCompetitions.lstSimpleCompetition.get(0).allOptions();
            String newCommand = commandScanner.nextLine();
            SimpleCompetitions.lstSimpleCompetition.get(0).commandAll(newCommand, commandScanner);
        } else {
            System.out.println("Unsupported option. Please try again!");
            moreEntries(commandScanner);
        }
    }
    /**
     *  Allows the user draw winners from the competition
     *  @param commandScanner    the scanner to receive the commands
     */
    @Override
    public void drawWinners(Scanner commandScanner) {

        System.out.println("Competition ID: " + this.getId() + ", Competition Name: " +
                this.getName() + ", Type: LuckyNumbersCompetition");
        System.out.print("Lucky Numbers:");

        //the seed is the competition ID
        int seed = this.getId();
        AutoNumbersEntry autoNumEntry = new AutoNumbersEntry();
        int[] luckyNumbers = autoNumEntry.createNumbers(seed);
        for (int luckyNum: luckyNumbers) {
            System.out.printf("%3d", luckyNum);
        }
        System.out.print(" [Auto]\n");

        int winningEntryCount = 0;
        System.out.println("Winning entries:");

        //get the same numbers in the entry
        int sameNum = 0;
        for (NumbersEntry numbersEntry : lstAllEntry) {
            int[] numbers = numbersEntry.getNumbers();
            sameNum = 0;
            for (int num : numbers) {
                for (int luckyNum : luckyNumbers) {
                    if (num == luckyNum) {
                        sameNum += 1;
                    }
                }
            }

            //if there are same or more than 2 same numbers, this entry is a candidate for prizes
            if (sameNum >= WINNING_TWO) {
                NumbersEntry winningEntry = new NumbersEntry();
                winningEntry.setEntryId(numbersEntry.getEntryId());
                winningEntry.setBillId(numbersEntry.getBillId());
                winningEntry.setMemberId(numbersEntry.getMemberId());
                winningEntry.setNumbers(numbersEntry.getNumbers());
                winningEntry.setSameNum(sameNum);

                //if the winning entry list is empty, update the two lists
                if (lstWinningEntries.size() == 0) {
                    lstWinningEntries.add(winningEntry);
                    winningEntryCount++;
                    lstWinningMembers.add(winningEntry.getMemberId());
                } else {
                    //if the member of the entry already has a candidate entry, compare the same numbers
                    if (lstWinningMembers.contains(winningEntry.getMemberId())) {
                        int memberIndex = lstWinningMembers.indexOf(winningEntry.getMemberId());
                        NumbersEntry previousEntry = lstWinningEntries.get(memberIndex);

                        if (winningEntry.getSameNum() > previousEntry.getSameNum()) {
                            lstWinningEntries.set(memberIndex, winningEntry);
                        }
                    } else {
                        lstWinningEntries.add(winningEntry);
                        winningEntryCount++;
                        lstWinningMembers.add(winningEntry.getMemberId());
                    }
                }
            }
        }

        String[] lstMemberId = getColumn(DataProvider.memberRecords, 0);
        //calculate the prized for each winning entry
        for (NumbersEntry winningEntry : lstWinningEntries) {
            int rowMemberId = Arrays.asList(lstMemberId).indexOf(winningEntry.getMemberId());
            String memberName = DataProvider.memberRecords.get(rowMemberId).get(1);

            int prize = 0;
            if (winningEntry.getSameNum() == WINNING_SEVEN) {
                prize = FIRST_PRIZE;
            } else if(winningEntry.getSameNum() == WINNING_SIX) {
                prize = SECOND_PRIZE;
            } else if (winningEntry.getSameNum() == WINNING_FIVE) {
                prize = THIRD_PRIZE;
            } else if (winningEntry.getSameNum() == WINNING_FOUR) {
                prize = FOURTH_PRIZE;
            } else if (winningEntry.getSameNum() == WINNING_THREE) {
                prize = FIFTH_PRIZE;
            } else if (winningEntry.getSameNum() == WINNING_TWO) {
                prize = SIXTH_PRIZE;
            }
            totalPrize += prize;

            System.out.print("Member ID: " + winningEntry.getMemberId() + ", Member Name: "
                    + memberName + ", Prize: ");
            System.out.printf("%-5d", prize);
            System.out.print("\n");
            System.out.print("--> Entry ID: " + winningEntry.getEntryId() + ", Numbers:");
            for (int winningNum: winningEntry.getNumbers()) {
                System.out.printf("%3d", winningNum);
            }
            System.out.print(" [Auto]\n");
        }

        this.setTotalPrize(totalPrize);
        this.setActiveCondition("no");
        this.setWinningEntryCount(winningEntryCount);
        this.setLstWinningMembers(lstWinningMembers);
        this.setLstUsedBillId(lstUsedBillId);

        SimpleCompetitions.lstSimpleCompetition.get(0).setActiveComp(0);
        SimpleCompetitions.lstSimpleCompetition.get(0).allOptions();
        String newCommand = commandScanner.nextLine();
        SimpleCompetitions.lstSimpleCompetition.get(0).commandAll(newCommand, commandScanner);
    }

}

