package in.at0m.fsm.transition;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * An instance of this class denotes a possible transition that the state machine may undergo.
 * A transition may have an action or a set of actions that need to be performed before the transition can happen.
 * A Transition's uniqueness is identified by its {@code event} and the {@code fromState}.
 *
 * @param <E> The enum type that denotes the events.
 * @param <S> The enum type that denotes the states.
 * @param <T> The type on which the state machine is operating.
 * @author shubhdarlinge
 */
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(builderClassName = "Builder", toBuilder = true)
public class Transition<E extends Enum<E>, S extends Enum<S>, T> {

    /**
     * The event for which this transition can happen.
     */
    @NonNull
    @EqualsAndHashCode.Include
    private final E event;

    /**
     * The state from which this transition can happen.
     */
    @NonNull
    @EqualsAndHashCode.Include
    private final S fromState;

    /**
     * The state to which this transition will lead.
     */
    @NonNull
    private final S toState;

    /**
     * The list of actions that need to be performed before completing this transition.
     * This list is always unmodifiable.
     */
    @NonNull
    private final List<Action<T>> actions;

    /**
     * Constructs a {@link Transition} instance for the given {@code event} from the
     * given {@code fromState} to the {@code toState} without any actions.
     *
     * @param event     The event for which this transition can happen.
     * @param fromState The state from which this transition can happen.
     * @param toState   The state to which this transition will lead.
     */
    public Transition(@NonNull final E event, @NonNull final S fromState, @NonNull final S toState) {
        this(event, fromState, toState, null);
    }

    /**
     * Constructs a {@link Transition} instance for the given {@code event} from the
     * given {@code fromState} to the {@code toState} with the given {@code actions}.
     *
     * @param event     The event for which this transition can happen.
     * @param fromState The state from which this transition can happen.
     * @param toState   The state to which this transition will lead.
     * @param actions   The actions that need to be performed before completing this transition.
     */
    public Transition(@NonNull final E event, @NonNull final S fromState,
                      @NonNull final S toState, final List<Action<T>> actions) {
        this.event = event;
        this.fromState = fromState;
        this.toState = toState;
        this.actions = actions == null ? Collections.emptyList() : Collections.unmodifiableList(actions);
    }

    /**
     * A builder pattern for {@link Transition} to make it easier to construct the object.
     *
     * @param <E> The enum type that denotes the events.
     * @param <S> The enum type that denotes the states.
     * @param <T> The type on which the state machine is operating.
     * @author shubhdarlinge
     */
    public static class Builder<E extends Enum<E>, S extends Enum<S>, T> {

        /**
         * Constructs a {@link Builder}.
         */
        private Builder() {
            actions = new ArrayList<>();
        }

        /**
         * Adds all the actions given in the collection to the list of actions for this builder.
         *
         * @param actions The actions to be added.
         * @return {@code this} instance.
         */
        public Builder<E, S, T> actions(@NonNull final Collection<Action<T>> actions) {
            this.actions.addAll(actions);
            return this;
        }

        /**
         * Adds an action to the list of actions for this builder.
         *
         * @param action The action to be added.
         * @return {@code this} instance.
         */
        public Builder<E, S, T> addAction(@NonNull final Action<T> action) {
            this.actions.add(action);
            return this;
        }
    }
}
