package in.at0m.fsm.transition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class TransitionTest {

    private static final TestEvent EVENT = TestEvent.TRANSITION;
    private static final TestState FROM_STATE = TestState.FROM;
    private static final TestState TO_STATE = TestState.TO;
    private static final List<Action<TestEvent, TestState, String>> ACTIONS = Collections.nCopies(
            3, new TestAction());

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
                .actions(ACTIONS)
                .addAction(ACTIONS.get(0))
                .build();
        Transition<TestEvent, TestState, String> transition2 = new Transition<>(
                EVENT, FROM_STATE, TO_STATE, Collections.nCopies(ACTIONS.size() + 1, ACTIONS.get(0)));
        assertTransitionFullyEquals(transition1, transition2);
    }

    @Test
    public void nullParamTest() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new Transition<TestEvent, TestState, String>(null, FROM_STATE, TO_STATE, null));
        Assertions.assertThrows(NullPointerException.class,
                () -> new Transition<TestEvent, TestState, String>(EVENT, null, TO_STATE, null));
        Assertions.assertThrows(NullPointerException.class,
                () -> new Transition<TestEvent, TestState, String>(EVENT, FROM_STATE, null, null));

        Assertions.assertThrows(NullPointerException.class,
                () -> Transition.<TestEvent, TestState, String>builder().actions(null));
        Assertions.assertThrows(NullPointerException.class,
                () -> Transition.<TestEvent, TestState, String>builder().addAction(null));
    }

    private void assertTransitionFullyEquals(
            final Transition<?, ?, ?> transition1, final Transition<?, ?, ?> transition2) {
        Assertions.assertSame(transition1.getEvent(), transition2.getEvent());
        Assertions.assertSame(transition1.getFromState(), transition2.getFromState());
        Assertions.assertSame(transition1.getToState(), transition2.getToState());
        Assertions.assertEquals(transition1.getActions(), transition2.getActions());
    }

    private enum TestEvent {
        TRANSITION
    }

    private enum TestState {
        FROM, TO
    }

    private static class TestAction implements Action<TestEvent, TestState, String> {

        @Override
        public void before(ActionContext<TestEvent, TestState, String> actionContext) {

        }

        @Override
        public void after(ActionContext<TestEvent, TestState, String> actionContext) {

        }
    }
}
