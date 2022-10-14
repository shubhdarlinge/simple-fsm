package in.at0m.fsm;

import in.at0m.fsm.exception.InvalidTransitionException;
import in.at0m.fsm.transition.ActionContext;
import in.at0m.fsm.transition.Transition;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A simple implementation of a finite state machine. This FSM works with {@link Transition}(s) that can
 * happen from one state to another upon consuming a particular event. When a transition is performed,
 * the {@link in.at0m.fsm.transition.Action}(s) registered with the transition is also executed.
 *
 * @param <E> The enum type that denotes the events.
 * @param <S> The enum type that denotes the states.
 * @param <T> The type on which the state machine is operating.
 * @author shubhdarlinge
 */
public class SimpleStateMachine<E extends Enum<E>, S extends Enum<S>, T> implements StateMachine<E, S, T> {

    /**
     * The state transition table in form of a map of event and source state to transition object.
     */
    @NonNull
    private final Map<TransitionSource<E, S>, Transition<E, S, T>> transitions;

    /**
     * Indicates the current state of this state machine.
     */
    @Getter
    @NonNull
    private S currentState;

    /**
     * Constructs a {@link SimpleStateMachine} instance with the given {@code initialState}
     * and collection of {@link Transition}. The transitions cannot be updated once the instance is created.
     *
     * @param initialState The initial state of this state machine.
     * @param transitions  The collection of all transitions that can be performed by this state machine.
     */
    @lombok.Builder(builderClassName = "Builder")
    public SimpleStateMachine(@NonNull final S initialState,
                              @NonNull final Collection<Transition<E, S, T>> transitions) {
        this.currentState = initialState;
        this.transitions = Collections.unmodifiableMap(transitions.stream()
                .collect(Collectors.toMap(TransitionSource::fromTransition, transition -> transition)));
    }

    /**
     * Returns a collection of {@link Transition} that can be performed by this state machine.
     *
     * @return A collection of {@link Transition} that can be performed by this state machine.
     */
    public Collection<Transition<E, S, T>> getTransitions() {
        return Collections.unmodifiableCollection(transitions.values());
    }

    /**
     * {@inheritDoc}
     *
     * @param event {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public boolean canConsume(@NonNull final E event) {
        return transitions.containsKey(new TransitionSource<>(event, currentState));
    }

    /**
     * {@inheritDoc}
     * The {@code before} and {@code after} methods for each {@link in.at0m.fsm.transition.Action}
     * registered in the {@link Transition} performed by this method are executed before and after
     * the transition is completed respectively. If the event cannot be consumed, an
     * {@link InvalidTransitionException} is thrown.
     *
     * @param event   {@inheritDoc}
     * @param context {@inheritDoc}
     * @return {@inheritDoc}
     * @throws InvalidTransitionException If the event cannot be consumed.
     */
    @Override
    public S consume(@NonNull final E event, @NonNull final T context) {
        if (!canConsume(event)) {
            throw new InvalidTransitionException(
                    String.format("Transition from state %s not valid for event %s", currentState, event));
        }
        final Transition<E, S, T> transition = transitions.get(new TransitionSource<>(event, currentState));
        final ActionContext<E, S, T> actionContext = new ActionContext<>(
                event, currentState, transition.getToState(), context);
        transition.getActions().forEach(action -> action.before(actionContext));
        currentState = transition.getToState();
        transition.getActions().forEach(action -> action.after(actionContext));
        return currentState;
    }

    /**
     * Indicates the source of a {@link Transition}.
     *
     * @param <E> The enum type that denotes the events.
     * @param <S> The enum type that denotes the states.
     * @author shubhdarlinge
     */
    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    @lombok.Builder(builderClassName = "Builder", toBuilder = true)
    private static class TransitionSource<E extends Enum<E>, S extends Enum<S>> {

        /**
         * The event for which a transition can happen for which this is the source.
         */
        @NonNull
        private final E event;

        /**
         * The state from which a transition can happen for which this is the source.
         */
        @NonNull
        private final S fromState;

        /**
         * Builds and returns a {@link TransitionSource} instance from a given {@link Transition}.
         *
         * @param transition The {@link Transition} from which {@link TransitionSource} needs to be built.
         * @param <E>        The enum type that denotes the events.
         * @param <S>        The enum type that denotes the states.
         * @param <T>        The type on which the state machine is operating.
         * @return An instance of {@link TransitionSource} built from the given {@link Transition}.
         */
        public static <E extends Enum<E>, S extends Enum<S>, T> TransitionSource<E, S> fromTransition(
                final Transition<E, S, T> transition) {
            return new TransitionSource<>(transition.getEvent(), transition.getFromState());
        }
    }

    /**
     * A builder pattern for {@link SimpleStateMachine} to make it easier to construct the object.
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
            this.transitions = new ArrayList<>();
        }

        /**
         * Adds all the transitions given in the collection to the transition map.
         *
         * @param transitions The transitions that need to be registered with this state machine.
         * @return {@code this} instance.
         */
        public Builder<E, S, T> transitions(@NonNull final Collection<Transition<E, S, T>> transitions) {
            this.transitions.addAll(transitions);
            return this;
        }

        /**
         * Adds the given transition to the transition map.
         *
         * @param transition The transition that needs to be registered with this state machine.
         * @return {@code this} instance.
         */
        public Builder<E, S, T> addTransition(@NonNull final Transition<E, S, T> transition) {
            this.transitions.add(transition);
            return this;
        }
    }
}
