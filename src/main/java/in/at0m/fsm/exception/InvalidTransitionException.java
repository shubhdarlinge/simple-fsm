package in.at0m.fsm.exception;

/**
 * Indicates that the transition is invalid.
 *
 * @author shubhdarlinge
 */
public class InvalidTransitionException extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    public InvalidTransitionException() {
    }

    /**
     * {@inheritDoc}
     *
     * @param s {@inheritDoc}
     */
    public InvalidTransitionException(String s) {
        super(s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s         {@inheritDoc}
     * @param throwable {@inheritDoc}
     */
    public InvalidTransitionException(String s, Throwable throwable) {
        super(s, throwable);
    }

    /**
     * {@inheritDoc}
     *
     * @param throwable {@inheritDoc}
     */
    public InvalidTransitionException(Throwable throwable) {
        super(throwable);
    }
}
