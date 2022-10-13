package in.at0m.fsm;

/**
 * The finite state machine interface.
 *
 * @param <E> The enum type that denotes the events.
 * @param <S> The enum type that denotes the states.
 * @param <T> The type on which the state machine is operating.
 * @author shubhdarlinge
 */
public interface StateMachine<E extends Enum<E>, S extends Enum<S>, T> {

    /**
     * Returns the current state of the state machine.
     *
     * @return The current state of the state machine.
     */
    S getCurrentState();

    /**
     * Returns {@code true} if the given event can be consumed. {@code false} otherwise.
     *
     * @param event The event which is being consumed.
     * @return {@code true} if the given event can be consumed. {@code false} otherwise.
     */
    boolean canConsume(E event);

    /**
     * Consumes the given event along with the context information and returns the new
     * state to which the state machine moves to.
     *
     * @param event   The event which is being consumed.
     * @param context The context information.
     * @return The new state to which the state machine moves to.
     */
    S consume(E event, T context);
}
