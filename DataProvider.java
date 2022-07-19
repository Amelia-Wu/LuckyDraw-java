/**
 * This class is used to provide all the data needed in the app and check the format of documents.
 *
 * @author Xinyi Wu
 *
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataProvider {
    final static int BILL_FILE_COLUMN = 4;
    final static int MEMBER_FILE_COLUMN = 3;
    final static int BILL_ID_LENGTH = 6;
    final static int MEMBER_ID_LENGTH = 6;
    final static int BILL_AMOUNT_INDEX = 2;
    public static List<List<String>> memberRecords = new ArrayList<>();
    public static List<List<String>> billRecords = new ArrayList<>();
    /**
     * 
     * @param memberFile A path to the member file (e.g., members.csv)
     * @param billFile A path to the bill file (e.g., bills.csv)
     * @throws DataAccessException If a file cannot be opened/read
     * @throws DataFormatException If the format of the content is incorrect
     */
     public DataProvider(String memberFile, String billFile) 
                        throws DataAccessException, DataFormatException, IOException {

         //read the member file
         try (BufferedReader br = new BufferedReader(new FileReader(memberFile))) {
             String line;
             while ((line = br.readLine()) != null) {
                 String[] values = line.split(",");
                 memberRecords.add(Arrays.asList(values));

                 //if the member ID format is incorrect, throw DataFormatException
                 if (values[0].length() != MEMBER_ID_LENGTH | !values[0].matches("-?\\d+(\\.\\d+)?")) {
                     throw new DataFormatException("The member ID format is not correct.");

                     //if the member file column is incorrect, throw DataFormatException
                 } else if (values.length != MEMBER_FILE_COLUMN) {
                     throw new DataFormatException("The member column number is not correct.");
                 }
             }
         } catch (IOException e) {
             throw new DataAccessException("The file cannot be opened.");
         }

         //read the bill file
         try (BufferedReader br = new BufferedReader(new FileReader(billFile))) {
             String line;
             while ((line = br.readLine()) != null) {
                 String[] values = line.split(",");
                 billRecords.add(Arrays.asList(values));

                 //if the bill ID format is incorrect, throw DataFormatException
                 if (values[0].length() != BILL_ID_LENGTH | !values[0].matches("-?\\d+(\\.\\d+)?")) {
                     throw new DataFormatException("The bill ID format is not correct.");

                     //if the member ID format is incorrect, throw DataFormatException
                 } else if (!(values[1].length() == MEMBER_ID_LENGTH | values[1].equals(""))) {
                     throw new DataFormatException("The member ID format is not correct.");

                     //if the bill ID format is incorrect, throw DataFormatException
                 } else if (!(values[values.length-1].equals("true") | values[values.length-1].equals("false"))) {
                     throw new DataFormatException("The bill used condition format is not correct.");

                 //if the bill file column is incorrect, throw DataFormatException
                 } else if (values.length != BILL_FILE_COLUMN) {
                    throw new DataFormatException("The bill column number is not correct.");
                 }

                 //if the bill amount format is incorrect, throw DataFormatException
                 try {
                     Float.parseFloat(values[BILL_AMOUNT_INDEX]);
                 } catch (NumberFormatException e){
                     throw new DataFormatException("The bill amount format is not correct.");
                 }
             }
         } catch (IOException e) {
             throw new DataAccessException("The file cannot be opened.");
         }
     }

    /**
     *  get the member information
     *  @param memberId    the memberId of the member
     */
     public Member getMember(String memberId) {
         Member member = new Member(memberId);
         return member;
     }

    /**
     *  get the bill information
     *  @param billId    the billId of the bill
     */
    public Bill getBill(String billId) {
        Bill bill = new Bill(billId);
        return bill;
    }
}

