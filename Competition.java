/**
 * This class is used to create competitions, which can be two types: lucky numbers
 *  and random picks. The functions of the competitions are adding entries, drawing winners and
 *  generating reports.
 *
 * @author Xinyi Wu
 *
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Competition implements Serializable {
    private String name; //competition name
    private int id; //competition identifier
    private String type; //competition identifier
    private int completedComp;
    private int entryNum; //number of entries
    final static int SINGLE_BILL = 50;
    private String activeCondition;
    private int totalPrize;
    private int winningEntryCount;
    ArrayList<String> lstUsedBillId = new ArrayList<>();
    ArrayList<String> lstWinningMembers = new ArrayList<>();

    /**
     *  the getters and setters for the competition
     * @return return completedComp
     */
    public int getCompletedComp() {
        return completedComp;
    }
    /**
     *  the getters and setters for the competition
     * @param completedComp  completedComp
     */
    public void setCompletedComp(int completedComp) {
        this.completedComp = completedComp;
    }
    /**
     *  the getters and setters for the competition
     *  @return return lstUsedBillId
     */
    public ArrayList<String> getLstUsedBillId() {
        return lstUsedBillId;
    }
    /**
     *  the getters and setters for the competition
     * @param lstUsedBillId  lstUsedBillId
     */
    public void setLstUsedBillId(ArrayList<String> lstUsedBillId) {
        this.lstUsedBillId = lstUsedBillId;
    }
    /**
     *  the getters and setters for the competition
     *  @return return name
     */
    public String getName() {
        return name;
    }
    /**
     *  the getters and setters for the competition
     * @param name  name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     *  the getters and setters for the competition
     *  @return return id
     */
    public int getId() {
        return id;
    }
    /**
     *  the getters and setters for the competition
     * @param id  id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     *  the getters and setters for the competition
     *  @return return winningEntryCount
     */
    public int getWinningEntryCount() {
        return winningEntryCount;
    }
    /**
     *  the getters and setters for the competition
     * @param winningEntryCount  winningEntryCount
     */
    public void setWinningEntryCount(int winningEntryCount) {
        this.winningEntryCount = winningEntryCount;
    }
    /**
     *  the getters and setters for the competition
     *  @return return activeCondition
     */
    public String getActiveCondition() {
        return activeCondition;
    }
    /**
     *  the getters and setters for the competition
     * @param activeCondition  activeCondition
     */
    public void setActiveCondition(String activeCondition) {
        this.activeCondition = activeCondition;
    }
    /**
     *  the getters and setters for the competition
     *  @return return entryNum
     */
    public int getEntryNum() {
        return entryNum;
    }
    /**
     *  the getters and setters for the competition
     * @param entryNum  entryNum
     */
    public void setEntryNum(int entryNum) {
        this.entryNum = entryNum;
    }
    
    /**
     *  the getters and setters for the competition
     * @param type  type
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     *  the getters and setters for the competition
     * @param lstWinningMembers  lstWinningMembers
     */
    public void setLstWinningMembers(ArrayList<String> lstWinningMembers) {
        this.lstWinningMembers = lstWinningMembers;
    }
    /**
     *  the getters and setters for the competition
     *  @return return totalPrize
     */
    public int getTotalPrize() {
        return totalPrize;
    }
    /**
     *  the getters and setters for the competition
     * @param totalPrize  totalPrize
     */
    public void setTotalPrize(int totalPrize) {
        this.totalPrize = totalPrize;
    }

    /**
     *  Allows the user add entries to the competition
     *  @param commandScanner    the scanner to receive the commands
     */
    public abstract void addEntries(Scanner commandScanner);

    /**
     *  Allows the user draw winners from the competition
     *  @param commandScanner    the scanner to receive the commands
     */
    public abstract void drawWinners(Scanner commandScanner);

    /**
     *  Allows the user add entries to the competition
     *  @param billRecords    the records of the bills extracted from data provider
     *  @param index    the index of the column
     *  @return column    return the column that is needed
     */
    public static String[] getColumn(List<List<String>> billRecords, int index){
        String[] column = new String[billRecords.size()];
        for(int i = 0; i < column.length; i++) {
            column[i] = billRecords.get(i).get(index);
        }
        return column;
    }

    /**
     *  Develop a report to summary all the competitions
     *  @param commandScanner    the scanner to receive the commands
     */
    public void report(Scanner commandScanner) {
        //check the number of completed competitions
        completedComp = 0;
        for (Competition comp : SimpleCompetitions.lstSimpleCompetition.get(0).getLstCompetition()) {
            if (comp.getActiveCondition().equals("no")) {
                completedComp += 1;
                this.setCompletedComp(completedComp);
            }
        }

        //print the report of the competitions
        System.out.println("----SUMMARY REPORT----");
        System.out.println("+Number of completed competitions: " + this.getCompletedComp());
        System.out.println("+Number of active competitions: " +
                (SimpleCompetitions.lstSimpleCompetition.get(0).getLstCompetition().size() - this.getCompletedComp()));

        for (Competition comp : SimpleCompetitions.lstSimpleCompetition.get(0).getLstCompetition()) {
            System.out.println();
            System.out.println("Competition ID: " + comp.getId() + ", name: "
                    + comp.getName() + ", active: " + comp.getActiveCondition());
            System.out.println("Number of entries: " + comp.getEntryNum());
            if (comp.getActiveCondition().equals("no")) {
                System.out.println("Number of winning entries: " + comp.getWinningEntryCount());
                System.out.println("Total awarded prizes: " + comp.getTotalPrize());
            }
        }

        SimpleCompetitions.lstSimpleCompetition.get(0).allOptions();
        String newCommand = commandScanner.nextLine();
        SimpleCompetitions.lstSimpleCompetition.get(0).commandAll(newCommand, commandScanner);
    }

}

