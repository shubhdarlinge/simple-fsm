package in.at0m.fsm.transition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class TransitionTest {

    private static final TestEvent EVENT = TestEvent.TRANSITION;
    private static final TestState FROM_STATE = TestState.FROM;
    private static final TestState TO_STATE = TestState.TO;
    private static final int ACTION_LIST_SIZE = 3;
    private static final List<Action<String>> ACTIONS = Collections.nCopies(ACTION_LIST_SIZE, (context) -> {
    });
    private static final String EVENT_NULL_MESSAGE = "Event cannot be null";
    private static final String FROM_STATE_NULL_MESSAGE = "FromState cannot be null";
    private static final String TO_STATE_NULL_MESSAGE = "ToState cannot be null";
    private static final String ACTION_NULL_MESSAGE = "Action cannot be null";
    private static final String ACTIONS_NULL_MESSAGE = "Actions cannot be null";

    @Test
    public void Transition_withValidValues_shouldNotThrowException() {
        Transition<TestEvent, TestState, String> transition1 = new Transition<>(EVENT, FROM_STATE, TO_STATE);
        Assertions.assertSame(EVENT, transition1.getEvent());
        Assertions.assertSame(FROM_STATE, transition1.getFromState());
        Assertions.assertSame(TO_STATE, transition1.getToState());
        Assertions.assertTrue(transition1.getActions().isEmpty());

        Transition<TestEvent, TestState, String> transition2 = new Transition<>(EVENT, FROM_STATE, TO_STATE, ACTIONS);
        Assertions.assertEquals(ACTIONS.size(), transition2.getActions().size());
    }

    @Test
    public void Transition_withNullValues_shouldThrowException() {
        NullPointerException npe1 = Assertions.assertThrows(
                NullPointerException.class, () -> new Transition<>(null, FROM_STATE, TO_STATE));
        Assertions.assertEquals(EVENT_NULL_MESSAGE, npe1.getMessage());

        NullPointerException npe2 = Assertions.assertThrows(
                NullPointerException.class, () -> new Transition<>(EVENT, null, TO_STATE));
        Assertions.assertEquals(FROM_STATE_NULL_MESSAGE, npe2.getMessage());

        NullPointerException npe3 = Assertions.assertThrows(
                NullPointerException.class, () -> new Transition<>(EVENT, FROM_STATE, null));
        Assertions.assertEquals(TO_STATE_NULL_MESSAGE, npe3.getMessage());
    }

    @Test
    public void equalsTest() {
        Transition<TestEvent, TestState, String> transition1 = new Transition<>(EVENT, FROM_STATE, TO_STATE);
        Transition<TestEvent, TestState, String> transition2 = new Transition<>(EVENT, FROM_STATE, TO_STATE, ACTIONS);
        Transition<TestEvent, TestState, String> transition3 = new Transition<>(EVENT, FROM_STATE, FROM_STATE, ACTIONS);
        Transition<TestEvent, TestState, String> transition4 = new Transition<>(EVENT, TO_STATE, TO_STATE, ACTIONS);

        Assertions.assertEquals(transition1, transition1);
        Assertions.assertNotEquals(transition1, new Object());
        Assertions.assertEquals(transition1, transition2);
        Assertions.assertEquals(transition1, transition3);
        Assertions.assertNotEquals(transition1, transition4);
        Assertions.assertNotEquals(transition2, transition4);
        Assertions.assertNotEquals(transition3, transition4);
    }

    @Test
    public void hashCodeTest() {
        Transition<TestEvent, TestState, String> transition1 = new Transition<>(EVENT, FROM_STATE, TO_STATE);
        Transition<TestEvent, TestState, String> transition2 = new Transition<>(EVENT, FROM_STATE, TO_STATE, ACTIONS);
        Transition<TestEvent, TestState, String> transition3 = new Transition<>(EVENT, FROM_STATE, FROM_STATE, ACTIONS);
        Transition<TestEvent, TestState, String> transition4 = new Transition<>(EVENT, TO_STATE, TO_STATE, ACTIONS);

        Assertions.assertEquals(transition1.hashCode(), transition2.hashCode());
        Assertions.assertEquals(transition1.hashCode(), transition3.hashCode());
        Assertions.assertNotEquals(transition1.hashCode(), transition4.hashCode());
        Assertions.assertNotEquals(transition2.hashCode(), transition4.hashCode());
        Assertions.assertNotEquals(transition3.hashCode(), transition4.hashCode());
    }

    @Test
    public void builderTest() {
        Transition<TestEvent, TestState, String> transition1 = Transition.<TestEvent, TestState, String>builder()
                .event(EVENT)
                .fromState(FROM_STATE)
                .toState(TO_STATE)
                .addActions(ACTIONS)
                .addAction(ACTIONS.get(0))
                .build();
        Transition<TestEvent, TestState, String> transition2 = new Transition<>(
                EVENT, FROM_STATE, TO_STATE, Collections.nCopies(ACTIONS.size() + 1, ACTIONS.get(0)));
        Assertions.assertTrue(fullEquals(transition1, transition2));

        NullPointerException npe1 = Assertions.assertThrows(
                NullPointerException.class, () -> Transition.builder().event(null));
        Assertions.assertEquals(EVENT_NULL_MESSAGE, npe1.getMessage());

        NullPointerException npe2 = Assertions.assertThrows(
                NullPointerException.class, () -> Transition.builder().fromState(null));
        Assertions.assertEquals(FROM_STATE_NULL_MESSAGE, npe2.getMessage());

        NullPointerException npe3 = Assertions.assertThrows(
                NullPointerException.class, () -> Transition.builder().toState(null));
        Assertions.assertEquals(TO_STATE_NULL_MESSAGE, npe3.getMessage());

        NullPointerException npe4 = Assertions.assertThrows(
                NullPointerException.class, () -> Transition.builder().addAction(null));
        Assertions.assertEquals(ACTION_NULL_MESSAGE, npe4.getMessage());

        NullPointerException npe5 = Assertions.assertThrows(
                NullPointerException.class, () -> Transition.builder().addActions(null));
        Assertions.assertEquals(ACTIONS_NULL_MESSAGE, npe5.getMessage());
    }

    private boolean fullEquals(final Transition<?, ?, ?> transition1, final Transition<?, ?, ?> transition2) {
        if (transition1 == null && transition2 == null) {
            return true;
        }
        if (transition1 == null || transition2 == null) {
            return false;
        }
        return transition1.getEvent() == transition2.getEvent()
                && transition1.getFromState() == transition2.getFromState()
                && transition1.getToState() == transition2.getToState()
                && transition1.getActions().equals(transition2.getActions());
    }
}
