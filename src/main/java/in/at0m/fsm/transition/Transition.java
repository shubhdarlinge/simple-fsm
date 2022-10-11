package in.at0m.fsm.transition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
public class Transition<E extends Enum<E>, S extends Enum<S>, T> {

    /**
     * The event for which this transition can happen.
     */
    private final E event;

    /**
     * The state from which this transition can happen.
     */
    private final S fromState;

    /**
     * The state to which this transition will lead.
     */
    private final S toState;

    /**
     * The list of actions that need to be performed before completing this transition.
     * This list is always unmodifiable.
     */
    private final List<Action<T>> actions;

    /**
     * Constructs a {@link Transition} instance for the given {@code event} from the
     * given {@code fromState} to the {@code toState} without any actions.
     *
     * @param event     The event for which this transition can happen.
     * @param fromState The state from which this transition can happen.
     * @param toState   The state to which this transition will lead.
     */
    public Transition(final E event, final S fromState, final S toState) {
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
    public Transition(final E event, final S fromState, final S toState, final List<Action<T>> actions) {
        Objects.requireNonNull(event, "Event cannot be null");
        Objects.requireNonNull(fromState, "FromState cannot be null");
        Objects.requireNonNull(toState, "ToState cannot be null");
        this.event = event;
        this.fromState = fromState;
        this.toState = toState;
        this.actions = actions == null ? Collections.emptyList() : Collections.unmodifiableList(actions);
    }

    /**
     * Returns an instance of {@link Builder}. Individual methods of the builder
     * must be used to construct the {@link Transition} object.
     *
     * @param <E> The enum type that denotes the events.
     * @param <S> The enum type that denotes the states.
     * @param <T> The type on which the state machine is operating.
     * @return An instance of {@link Builder}.
     */
    public static <E extends Enum<E>, S extends Enum<S>, T> Builder<E, S, T> builder() {
        return new Builder<>();
    }

    /**
     * Returns the {@code event}.
     *
     * @return The {@code event}.
     */
    public E getEvent() {
        return event;
    }

    /**
     * Returns the {@code fromState}.
     *
     * @return The {@code fromState}.
     */
    public S getFromState() {
        return fromState;
    }

    /**
     * Returns the {@code toState}.
     *
     * @return The {@code toState}.
     */
    public S getToState() {
        return toState;
    }

    /**
     * Returns the list of actions.
     *
     * @return the list of actions.
     */
    public List<Action<T>> getActions() {
        return actions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transition)) return false;
        Transition<?, ?, ?> that = (Transition<?, ?, ?>) o;
        return this.event == that.event && this.fromState == that.fromState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, fromState);
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
         * The list of actions that need to be performed before completing this builder's transition.
         */
        private final List<Action<T>> actions;

        /**
         * The event for which this builder's transition can happen.
         */
        private E event;

        /**
         * The state from which this builder's transition can happen.
         */
        private S fromState;

        /**
         * The state to which this builder's transition will lead.
         */
        private S toState;

        /**
         * Constructs a {@link Builder}.
         */
        private Builder() {
            actions = new ArrayList<>();
        }

        /**
         * Sets the {@code event} for this builder.
         *
         * @param event The event for which this builder's transition can happen.
         * @return {@code this} instance.
         */
        public Builder<E, S, T> event(E event) {
            Objects.requireNonNull(event, "Event cannot be null");
            this.event = event;
            return this;
        }

        /**
         * Sets the {@code fromState} for this builder.
         *
         * @param fromState The state from which this builder's transition can happen.
         * @return {@code this} instance.
         */
        public Builder<E, S, T> fromState(S fromState) {
            Objects.requireNonNull(fromState, "FromState cannot be null");
            this.fromState = fromState;
            return this;
        }

        /**
         * Sets the {@code toState} for this builder.
         *
         * @param toState The state to which this builder's transition will lead.
         * @return {@code this} instance.
         */
        public Builder<E, S, T> toState(S toState) {
            Objects.requireNonNull(toState, "ToState cannot be null");
            this.toState = toState;
            return this;
        }

        /**
         * Adds an action to the list of actions for this builder.
         *
         * @param action The action to be added.
         * @return {@code this} instance.
         */
        public Builder<E, S, T> addAction(Action<T> action) {
            Objects.requireNonNull(action, "Action cannot be null");
            this.actions.add(action);
            return this;
        }

        /**
         * Adds all the actions given in the collection to the list of actions for this builder.
         *
         * @param actions The actions to be added.
         * @return {@code this} instance.
         */
        public Builder<E, S, T> addActions(Collection<Action<T>> actions) {
            Objects.requireNonNull(actions, "Actions cannot be null");
            this.actions.addAll(actions);
            return this;
        }

        /**
         * Builds and returns the {@link Transition} instance with parameters given to the builder instance.
         *
         * @return An instance of {@link Transition}.
         */
        public Transition<E, S, T> build() {
            return new Transition<>(event, fromState, toState, actions);
        }
    }
}
