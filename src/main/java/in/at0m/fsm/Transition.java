package in.at0m.fsm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * An instance of this class denotes a possible transition that the state machine may undergo.
 * A transition may have an action or a set of actions that need to be performed before the transition can happen.
 *
 * @param <T> The type on which the state machine is operating.
 * @author shubhdarlinge
 */
public class Transition<T> {

    /**
     * The state from which this transition can happen.
     */
    private final String fromState;

    /**
     * The state to which this transition will lead.
     */
    private final String toState;

    /**
     * The list of actions that need to be performed before completing this transition.
     * This list is always unmodifiable.
     */
    private final List<Action<T>> actions;

    /**
     * Constructs a {@link Transition} instance from the given {@code fromState} to the
     * {@code toState} without any actions.
     *
     * @param fromState The state from which this transition can happen.
     * @param toState   The state to which this transition will lead.
     */
    public Transition(final String fromState, final String toState) {
        this(fromState, toState, null);
    }

    /**
     * Constructs a {@link Transition} instance from the given {@code fromState} to the
     * {@code toState} with the given {@code actions}.
     *
     * @param fromState The state from which this transition can happen.
     * @param toState   The state to which this transition will lead.
     * @param actions   The actions that need to be performed before completing this transition.
     */
    public Transition(final String fromState, final String toState, final List<Action<T>> actions) {
        Objects.requireNonNull(fromState, "FromState cannot be null");
        Objects.requireNonNull(toState, "ToState cannot be null");
        this.fromState = fromState;
        this.toState = toState;
        this.actions = actions == null ? Collections.emptyList() : Collections.unmodifiableList(actions);
    }

    /**
     * Returns an instance of {@link Builder}. Individual methods of the builder
     * must be used to construct the {@link Transition} object.
     *
     * @param clazz The type on which the state machine is operating.
     * @param <T>   The type on which the state machine is operating.
     * @return An instance of {@link Builder}.
     */
    public static <T> Builder<T> builder(Class<T> clazz) {
        return new Builder<>();
    }

    /**
     * Returns the {@code fromState}.
     *
     * @return The {@code fromState}.
     */
    public String getFromState() {
        return fromState;
    }

    /**
     * Returns the {@code toState}.
     *
     * @return The {@code toState}.
     */
    public String getToState() {
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
        Transition<?> that = (Transition<?>) o;
        return fromState.equals(that.fromState) && toState.equals(that.toState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromState, toState);
    }

    /**
     * A builder pattern for {@link Transition} to make it easier to construct the object.
     *
     * @param <T> The type on which the state machine is operating.
     * @author shubhdarlinge
     */
    public static class Builder<T> {

        /**
         * The list of actions that need to be performed before completing this builder's transition.
         */
        private final List<Action<T>> actions;

        /**
         * The state from which this builder's transition can happen.
         */
        private String fromState;

        /**
         * The state to which this builder's transition will lead.
         */
        private String toState;

        /**
         * Constructs a {@link Builder}.
         */
        private Builder() {
            actions = new ArrayList<>();
        }

        /**
         * Sets the {@code fromState} for this builder.
         *
         * @param fromState The state from which this builder's transition can happen.
         * @return {@code this} instance.
         */
        public Builder<T> fromState(String fromState) {
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
        public Builder<T> toState(String toState) {
            Objects.requireNonNull(toState, "ToState cannot be null");
            this.toState = toState;
            return this;
        }

        /**
         * Adds an action to the list of actions.
         *
         * @param action The action to be added.
         * @return {@code this} instance.
         */
        public Builder<T> addAction(Action<T> action) {
            Objects.requireNonNull(action, "Action cannot be null");
            this.actions.add(action);
            return this;
        }

        /**
         * Adds all the actions given in the collection to the list of actions.
         *
         * @param actions The actions to be added.
         * @return {@code this} instance.
         */
        public Builder<T> addActions(Collection<Action<T>> actions) {
            Objects.requireNonNull(actions, "Actions cannot be null");
            this.actions.addAll(actions);
            return this;
        }

        /**
         * Builds and returns the {@link Transition} instance with parameters given to the builder instance.
         *
         * @return An instance of {@link Transition}.
         */
        public Transition<T> build() {
            return new Transition<>(fromState, toState, actions);
        }
    }
}
