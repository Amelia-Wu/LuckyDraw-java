/**
 * This class is used to handle data format exception.
 *
 * @author Xinyi Wu
 *
 */

public class DataFormatException extends Exception {
    /**
     *
     * get the error message for DataFormatException
     * @param errorMessage the error message
     */
    public DataFormatException(String errorMessage) {
        super(errorMessage);
    }
}

