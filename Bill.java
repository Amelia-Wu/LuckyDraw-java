/**
 * This class is used to store the information of bills.
 *
 * @author Xinyi Wu
 *
 */

public class Bill {
    private String billId;
    private boolean isUsed;
    /**
     *  the getters and setters for the bill
     * @return  return whether the bill is used
     */
    public boolean isUsed() {
        return isUsed;
    }
    /**
     *  the getters and setters for the bill
     * @param used  if the bill is used
     */
    public void setUsed(boolean used) {
        isUsed = used;
    }
    /**
     *  get the bill ID
     *  @param billId    the bill ID of the bill
     */
    public Bill(String billId) {
        this.billId = billId;
    }
}

