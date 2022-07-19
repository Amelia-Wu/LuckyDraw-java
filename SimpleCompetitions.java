/*
 * Student name: Xinyi Wu
 * Student ID: 1306826
 * LMS username: wuxw13
 */

/**
 * This class is used to start the app as the first step and process all the commands.
 *
 * @author Xinyi Wu
 *
 */
import java.io.*;
import java.util.*;

public class SimpleCompetitions {
    private static final int ROW = 20;
    private final static int CHOICE_1 = 1;
    private final static int CHOICE_2 = 2;
    private final static int CHOICE_3 = 3;
    private final static int CHOICE_4 = 4;
    private final static int CHOICE_5 = 5;
    private final static int BILL_AMOUNT_INDEX = 2;
    public ArrayList<Competition> lstCompetition = new ArrayList<>();
    public static ArrayList<SimpleCompetitions> lstSimpleCompetition = new ArrayList<>();
    public ArrayList<String> lstUsedBillId = new ArrayList<>();
    int competitionId;
    boolean isTestingMode = false;
    int activeComp;

    /**
     *  the getters and setters for the Simple Competition
     * @return return the lstCompetition
     */
    public ArrayList<Competition> getLstCompetition() {
        return lstCompetition;
    }
    /**
     *  the getters and setters for the Simple Competition
     *  @return return the isTestingMode
     */
    public boolean getIsTestingMode() {
        return isTestingMode;
    }
    /**
     *  the getters and setters for the Simple Competition
     */
    public void setIsTestingMode() {
        this.isTestingMode = true;
    }
    /**
     *  the getters and setters for the Simple Competition
     * @param activeComp  activeComp
     */
    public void setActiveComp(int activeComp) {
        this.activeComp = activeComp;
    }

    /**
     * Main program that uses the main SimpleCompetitions class
     *
     * @param args main program arguments
     */
    public static void main(String[] args) {

        //Create an object of the SimpleCompetitions class
        SimpleCompetitions sc = new SimpleCompetitions();
        lstSimpleCompetition.add(sc);
        sc.startApp();
    }

    /**
     *  Start the app in the main SimpleCompetitions class
     */
    public void startApp() {
        Scanner commandScanner = new Scanner(System.in);
        Title();
        chooseLoad(commandScanner);

        System.out.println("Member file: ");
        String memberFile = commandScanner.nextLine();

        System.out.println("Bill file: ");
        String billFile = commandScanner.nextLine();

        //read the data in member file and bill file
        try {
            DataProvider dataProvider = new DataProvider(memberFile, billFile);
        } catch (DataAccessException | IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (DataFormatException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        allOptions();
        String newCommand = commandScanner.nextLine();
        commandAll(newCommand, commandScanner);
    }

    /**
     *  print all the available options
     */
    public void allOptions() {
        System.out.println("Please select an option. Type 5 to exit.");
        System.out.println("1. Create a new competition");
        System.out.println("2. Add new entries");
        System.out.println("3. Draw winners");
        System.out.println("4. Get a summary report");
        System.out.println("5. Exit");
    }

    /**
     *  Process all the new commands
     *  @param commandScanner    the scanner to receive the commands
     *  @param newCommand    the command entered by the user
     */
    public void commandAll(String newCommand, Scanner commandScanner) {
        try {
            while (Integer.parseInt(newCommand) != CHOICE_5) {

                //add new competition when CHOICE_1 is chosen
                if (Integer.parseInt(newCommand) == CHOICE_1) {
                    addNewCompetition(commandScanner);

                } else if (Integer.parseInt(newCommand) == CHOICE_2) {
                    if (lstCompetition.size() == 0) {
                        System.out.println("There is no active competition. Please create one!");
                    } else {
                        if (lstCompetition.get(lstCompetition.size() - 1).getCompletedComp()
                                == lstCompetition.size()) {
                            System.out.println("There is no active competition. Please create one!");
                        } else {
                            lstCompetition.get(lstCompetition.size() - 1).addEntries(commandScanner);
                            lstSimpleCompetition.get(0).setActiveComp(1);
                            lstCompetition.get(lstCompetition.size() - 1).setActiveCondition("yes");
                        }
                    }
                    allOptions();
                    newCommand = commandScanner.nextLine();
                    commandAll(newCommand, commandScanner);

                } else if (Integer.parseInt(newCommand) == CHOICE_3) {
                    if (lstCompetition.size() == 0) {
                        System.out.println("There is no active competition. Please create one!");
                    } else {
                        if (lstCompetition.get(lstCompetition.size() - 1).getCompletedComp()
                                == lstCompetition.size()) {
                            System.out.println("There is no active competition. Please create one!");
                        } else if (lstCompetition.get(lstCompetition.size() - 1).getEntryNum() == 0) {
                            System.out.println("The current competition has no entries yet!");
                            lstSimpleCompetition.get(0).setActiveComp(1);
                            lstCompetition.get(lstCompetition.size() - 1).setActiveCondition("yes");
                        } else {
                            lstCompetition.get(lstCompetition.size() - 1).drawWinners(commandScanner);
                            lstSimpleCompetition.get(0).setActiveComp(0);
                            lstCompetition.get(lstCompetition.size() - 1).setActiveCondition("no");
                        }
                    }
                    allOptions();
                    newCommand = commandScanner.nextLine();
                    commandAll(newCommand, commandScanner);
                } else if (Integer.parseInt(newCommand) == CHOICE_4) {

                    if (lstCompetition.size() == 0) {
                        System.out.println("No competition has been created yet!");
                    } else {
                        lstCompetition.get(lstCompetition.size() - 1).report(commandScanner);
                    }
                    allOptions();
                    newCommand = commandScanner.nextLine();
                    commandAll(newCommand, commandScanner);
                } else {
                    System.out.println("Unsupported option. Please try again!");
                    newCommand = commandScanner.nextLine();
                    commandAll(newCommand, commandScanner);
                }
                break;
            }

            if (Integer.parseInt(newCommand) == CHOICE_5) {
                exit(commandScanner);
            }
        } catch (NumberFormatException e) {
            System.out.println("A number is expected. Please try again.");
            allOptions();
            newCommand = commandScanner.nextLine();
            commandAll(newCommand, commandScanner);
        }
    }

    /**
     *  Create a new competition when this method is called
     *  @param commandScanner    the scanner to receive the commands
     */
    public void addNewCompetition(Scanner commandScanner) {
        //check if there is an active competition
        if (lstCompetition.size() > 0) {
            if (lstCompetition.get(lstCompetition.size() - 1).getCompletedComp()
                    != lstCompetition.size()) {
                System.out.println("There is an active competition. SimpleCompetitions does not support concurrent competitions!");
                allOptions();
                String newCommand = commandScanner.nextLine();
                commandAll(newCommand, commandScanner);
            }
        }

        //initialize the competition ID
        if (lstCompetition.size() == 0) {
            competitionId = 0;
        } else {
            competitionId = lstCompetition.get(lstCompetition.size()-1).getId();
        }

        //choose the type of competition
        System.out.println("Type of competition (L: LuckyNumbers, R: RandomPick)?:");
        String compType = commandScanner.nextLine();
        if (compType.equals("L")) {
            System.out.println("Competition name: ");
            String name = commandScanner.nextLine();

            LuckyNumbersCompetition competition = new LuckyNumbersCompetition();
            lstCompetition.add(competition);
            competition.setName(name);
            competitionId += 1;
            competition.setId(competitionId);
            competition.setType("L");
            competition.setActiveCondition("yes");
            competition.setLstUsedBillId(lstUsedBillId);

            System.out.println("A new competition has been created!");
            System.out.println("Competition ID: " + lstCompetition.get(lstCompetition.size() - 1).getId() + ", Competition Name: " +
                    lstCompetition.get(lstCompetition.size() - 1).getName() + ", Type: LuckyNumbersCompetition");

        } else if (compType.equals("R")) {
            System.out.println("Competition name: ");
            String name = commandScanner.nextLine();

            RandomPickCompetition competition = new RandomPickCompetition();
            lstCompetition.add(competition);
            competition.setName(name);
            competitionId += 1;
            competition.setId(competitionId);
            competition.setType("R");
            competition.setActiveCondition("yes");
            competition.setLstUsedBillId(lstUsedBillId);

            System.out.println("A new competition has been created!");
            System.out.println("Competition ID: " + competition.getId() + ", Competition Name: " +
                    competition.getName() + ", Type: RandomPickCompetition");
        } else {
            System.out.println("Invalid competition type! Please choose again.");
            addNewCompetition(commandScanner);
        }
        allOptions();
        String newCommand = commandScanner.nextLine();
        commandAll(newCommand, commandScanner);
    }

    /**
     *  Exit the app when this method is called
     *  @param commandScanner    the scanner to receive the commands
     */
    public void exit(Scanner commandScanner) {
        System.out.println("Save competitions to file? (Y/N)?");
        String saveChoice = commandScanner.nextLine();

        if (saveChoice.equals("N")) {
            //the file will not be saved if user chooses N
        } else if (saveChoice.equals("Y")) {
            //save the file
            System.out.println("File name:");
            String saveName = commandScanner.nextLine();
            if (lstCompetition.size() > 0) {
                saveFile(saveName);
                updateBillCsv();
                System.out.println("Competitions have been saved to file.");
                System.out.println("The bill file has also been automatically updated.");
            } else {
                System.out.println("There is no competition to save.");
            }

        } else {
            System.out.println("Unsupported option. Please try again!");
            exit(commandScanner);
        }
        System.out.println("Goodbye!");
        System.exit(0);
    }

    /**
     *  Print the title
     */
    public void Title() {
        System.out.println("----WELCOME TO SIMPLE COMPETITIONS APP----");
    }

    /**
     *  Allows the user to load the saved file
     *  @param commandScanner    the scanner to receive the commands
     */
    public void chooseLoad(Scanner commandScanner) {

        System.out.println("Load competitions from file? (Y/N)?");
        String loadChoice = commandScanner.nextLine();

        if (loadChoice.equals("N")) {
            chooseMode(commandScanner);
        } else if (loadChoice.equals("Y")) {
            //load the file
            System.out.println("File name:");
            String savedFileName = commandScanner.nextLine();
            readSavedFile(savedFileName);

            //set the mode as testing mode
            lstSimpleCompetition.get(0).setIsTestingMode();
            //update the list of used bill ID
            if (lstCompetition.size() > 0) {
                lstUsedBillId = lstCompetition.get(lstCompetition.size()-1).getLstUsedBillId();
            }
        } else {
            System.out.println("Unsupported option. Please try again!");
            chooseLoad(commandScanner);
        }
    }

    /**
     *  Save the file
     *  @param savedFileName    the name of the saved file
     */
    public void saveFile(String savedFileName) {
        ArrayList<Competition> savedLstComp = lstCompetition;
        try {
            ObjectOutputStream objectOut = null;
            FileOutputStream out = new FileOutputStream(savedFileName);
            objectOut = new ObjectOutputStream(out);
            objectOut.writeObject(savedLstComp);

            // close the stream
            objectOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *  read the saved file
     *  @param savedFileName    the name of the saved file
     */
    public void readSavedFile(String savedFileName) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(savedFileName));
            //update the list of competition
            lstCompetition = (ArrayList<Competition>) ois.readObject();

        } catch (Exception e) {
            System.out.println("No file found.");
            System.exit(0);
        }
    }
    /**
     *  update the bill.csv file
     */
    public void updateBillCsv() {
        try {
            //get the first three columns in the bills.csv
            List<String> lstBillIdArray = Arrays.asList(Competition.getColumn(DataProvider.billRecords, 0));
            List<String> lstMemberId = Arrays.asList(Competition.getColumn(DataProvider.billRecords, 1));
            List<String> lstBillAmount = Arrays.asList(Competition.getColumn(DataProvider.billRecords, BILL_AMOUNT_INDEX));

            //create a new list to store the updated information
            List<String> lstIsBillUsed = Arrays.asList(new String[ROW]);

            ArrayList<String> usedBillId = lstCompetition.get(lstCompetition.size()-1).getLstUsedBillId();

            FileWriter csvWriter = new FileWriter("bills.csv");

            //update if the bill ID has been used
            for (int i = 0; i < ROW; i++) {
                for(String bill : usedBillId){
                    if (lstBillIdArray.get(i).equals(bill)){
                        lstIsBillUsed.set(i, "true");
                    } else {
                        lstIsBillUsed.set(i, "false");
                    }
                }
                //rewrite bills.csv
                csvWriter.append(String.join(",", lstBillIdArray.get(i),
                        lstMemberId.get(i), lstBillAmount.get(i), lstIsBillUsed.get(i)));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }
    /**
     *  Allows the user to choose a mode (testing or normal)
     *  @param commandScanner    the scanner to receive the commands
     */
    public void chooseMode(Scanner commandScanner) {
        System.out.println("Which mode would you like to run? (Type T for Testing, and N for Normal mode):");
        String modeChoice = commandScanner.nextLine();

        if (modeChoice.equals("T")) {
            lstSimpleCompetition.get(0).setIsTestingMode();
        } else if (modeChoice.equals("N")) {
            //the default mode is normal mode
        } else {
            System.out.println("Invalid mode! Please choose again.");
            chooseMode(commandScanner);
        }
    }
}

