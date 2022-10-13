package in.at0m.fsm.transition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Context data provided to the Action executed during transition.
 *
 * @param <E> The enum type that denotes the events.
 * @param <S> The enum type that denotes the states.
 * @param <T> The type on which the state machine is operating.
 * @author shubhdarlinge
 */
@Getter
@AllArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
public class ActionContext<E extends Enum<E>, S extends Enum<S>, T> {

    /**
     * The event that triggered the {@link Transition} leading to creation of this {@link ActionContext}.
     */
    private final E event;

    /**
     * The state from which the {@link Transition} leading to creation of this {@link ActionContext} started.
     */
    private final S currentState;

    /**
     * The state to which the {@link Transition} leading to creation of this {@link ActionContext} will lead.
     */
    private final S nextState;

    /**
     * The data in context of the {@link Transition} leading to creation of this {@link ActionContext}.
     */
    private final T data;
}
