/**
 * This class is used to create all the entries, which can be two types: numbers entry
 *  and auto numbers entry.
 *
 * @author Xinyi Wu
 *
 */

import java.io.Serializable;

public class Entry implements Serializable {
    private int entryId;
    private String billId;
    private String memberId;
    private int sameNum;
    private int prizes;

    /**
     *  the getters and setters for the entry
     * @return return the prizes
     */
    public int getPrizes() {
        return prizes;
    }
    /**
     *  the getters and setters for the entry
     * @param prizes  prizes
     */
    public void setPrizes(int prizes) {
        this.prizes = prizes;
    }
    /**
     *  the getters and setters for the entry
     *  @return return the entryId
     */
    public int getEntryId() {
        return entryId;
    }
    /**
     *  the getters and setters for the entry
     * @param entryId  entryId
     */
    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }
    /**
     *  the getters and setters for the entry
     *  @return return the billId
     */
    public String getBillId() {
        return billId;
    }
    /**
     *  the getters and setters for the entry
     * @param billId  billId
     */
    public void setBillId(String billId) {
        this.billId = billId;
    }
    /**
     *  the getters and setters for the entry
     *  @return return the memberId
     */
    public String getMemberId() {
        return memberId;
    }
    /**
     *  the getters and setters for the entry
     * @param memberId  memberId
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    /**
     *  the getters and setters for the entry
     *  @return return the sameNum
     */
    public int getSameNum() {
        return sameNum;
    }
    /**
     *  the getters and setters for the entry
     * @param sameNum  sameNum
     */
    public void setSameNum(int sameNum) {
        this.sameNum = sameNum;
    }

}

