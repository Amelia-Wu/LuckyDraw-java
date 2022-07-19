/**
 * This class is used to handle data access exception.
 *
 * @author Xinyi Wu
 *
 */
public class DataAccessException extends Exception {
    /**
     *
     * get the error message for DataAccessException
     * @param errorMessage the error message
     */
    public DataAccessException(String errorMessage) {
        super(errorMessage);
    }
}

