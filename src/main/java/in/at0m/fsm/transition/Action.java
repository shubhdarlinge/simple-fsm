package in.at0m.fsm.transition;

/**
 * An action that should be performed while transitioning from one state to another.
 *
 * @param <E> The enum type that denotes the events.
 * @param <S> The enum type that denotes the states.
 * @param <T> The type on which the state machine is operating.
 * @author shubhdarlinge
 */
public interface Action<E extends Enum<E>, S extends Enum<S>, T> {

    /**
     * Called <em>before</em> the transition is completed.
     *
     * @param actionContext The context information for transition.
     */
    void before(ActionContext<E, S, T> actionContext);

    /**
     * Called <em>after</em> the transition is completed.
     *
     * @param actionContext The context information for transition.
     */
    void after(ActionContext<E, S, T> actionContext);
}
