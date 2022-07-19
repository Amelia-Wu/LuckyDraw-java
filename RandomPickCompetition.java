/**
 * This class is used to process all the random pick competition and the methods of
 * adding entries and drawing winners.
 *
 * @author Xinyi Wu
 *
 */

import java.util.*;

public class RandomPickCompetition extends Competition {
    private final int FIRST_PRIZE = 50000;
    private final int SECOND_PRIZE = 5000;
    private final int THIRD_PRIZE = 1000;
    private final int[] prizes = {FIRST_PRIZE, SECOND_PRIZE, THIRD_PRIZE};
    private final int MAX_WINNING_ENTRIES = 3;
    final static int BILL_AMOUNT_INDEX = 2;
    ArrayList<Entry> lstAllEntry = new ArrayList<>();
    ArrayList<Entry> lstWinningEntries = new ArrayList<>();
    ArrayList<String> lstWinningMembers = new ArrayList<>();
    int totalPrize = 0;
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
        //if the format of the entry is correct, continue the process
        float billAmount = Float.parseFloat(DataProvider.billRecords.get(rowBillId).get(BILL_AMOUNT_INDEX));
        int eligibleEntry = (int) (billAmount / SINGLE_BILL);
        System.out.println("This bill ($" + billAmount + ") is eligible for " +
                eligibleEntry + " entries.");
        System.out.println("The following entries have been automatically generated:");

        //the logic of automatic entries processing
        int startEntryId;
        int closeEntryId;

        if (lstAllEntry.size() == 0) {
            startEntryId = 1;
        } else {
            startEntryId = lstAllEntry.get(lstAllEntry.size()-1).getEntryId() + 1;
        }
        closeEntryId = startEntryId + eligibleEntry - 1;

        for (int i = startEntryId; i <= closeEntryId; i++) {
            System.out.print("Entry ID: ");
            System.out.printf("%-6d", i);
            System.out.print("\n");

            Entry entry = new Entry();
            lstAllEntry.add(entry);
            entry.setEntryId(i);
            entry.setBillId(stringBillId);
            entry.setMemberId(stringMemberId);
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
    public void drawWinners(Scanner commandScanner) {

        System.out.println("Competition ID: " + this.getId() + ", Competition Name: " +
                this.getName() + ", Type: RandomPickCompetition");
        System.out.println("Winning entries:");

        //get the random generator
        Random randomGenerator = null;
        if (SimpleCompetitions.lstSimpleCompetition.get(0).getIsTestingMode()) {
            randomGenerator = new Random(this.getId());
        } else {
            randomGenerator = new Random();
        }

        //the logic of adding winning entries
        int winningEntryCount = 0;
        while (winningEntryCount < MAX_WINNING_ENTRIES) {
            int winningEntryIndex = randomGenerator.nextInt(lstAllEntry.size());

            Entry winningEntry = lstAllEntry.get(winningEntryIndex);

            if (winningEntry.getPrizes() == 0) {
                int currentPrize = prizes[winningEntryCount];
                //totalPrize += currentPrize;
                winningEntry.setPrizes(currentPrize);
                winningEntryCount++;
            }

            if (lstWinningEntries.size() == 0) {
                lstWinningEntries.add(winningEntry);
                lstWinningMembers.add(winningEntry.getMemberId());
            } else {
                //if the member of the entry already has a candidate entry, compare the same numbers
                if (lstWinningMembers.contains(winningEntry.getMemberId())) {
                    int memberIndex = lstWinningMembers.indexOf(winningEntry.getMemberId());
                    Entry previousEntry = lstWinningEntries.get(memberIndex);

                    if (winningEntry.getSameNum() > previousEntry.getSameNum()) {
                        lstWinningEntries.set(memberIndex, winningEntry);
                    }
                } else {
                    lstWinningEntries.add(winningEntry);
                    lstWinningMembers.add(winningEntry.getMemberId());
                }
            }
        }

        //print the winning entries
        String[] lstMemberId = getColumn(DataProvider.memberRecords, 0);
        for (int i = lstWinningEntries.size()-1; i > -1; i--) {
            int rowMemberId = Arrays.asList(lstMemberId).indexOf(lstWinningEntries.get(i).getMemberId());
            String memberName = DataProvider.memberRecords.get(rowMemberId).get(1);
            System.out.print("Member ID: " + lstWinningEntries.get(i).getMemberId() + ", Member Name: "
                    + memberName + ", Entry ID: " + lstWinningEntries.get(i).getEntryId() + ", Prize: ");
            System.out.printf("%-5d", lstWinningEntries.get(i).getPrizes());
            System.out.print("\n");
            totalPrize += lstWinningEntries.get(i).getPrizes();
        }

        this.setTotalPrize(totalPrize);
        this.setActiveCondition("no");
        this.setWinningEntryCount(lstWinningEntries.size());
        this.setLstWinningMembers(lstWinningMembers);
        this.setLstUsedBillId(lstUsedBillId);

        SimpleCompetitions.lstSimpleCompetition.get(0).setActiveComp(0);
        SimpleCompetitions.lstSimpleCompetition.get(0).allOptions();
        String newCommand = commandScanner.nextLine();
        SimpleCompetitions.lstSimpleCompetition.get(0).commandAll(newCommand, commandScanner);
    }
}

