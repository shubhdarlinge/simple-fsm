package in.at0m.fsm.exception;

/**
 * Indicates that the transition is invalid.
 *
 * @author shubhdarlinge
 */
public class InvalidTransitionException extends RuntimeException {

    /**
     * Creates an instance of InvalidTransitionException with given message.
     *
     * @param s The exception message.
     */
    public InvalidTransitionException(String s) {
        super(s);
    }
}
