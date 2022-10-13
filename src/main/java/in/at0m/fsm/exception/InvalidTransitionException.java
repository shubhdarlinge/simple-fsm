package in.at0m.fsm.exception;

/**
 * Indicates that the transition is invalid.
 *
 * @author shubhdarlinge
 */
public class InvalidTransitionException extends RuntimeException {

    /**
     * {@inheritDoc}
     *
     * @param s {@inheritDoc}
     */
    public InvalidTransitionException(String s) {
        super(s);
    }
}
