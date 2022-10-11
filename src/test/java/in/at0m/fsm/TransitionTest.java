package in.at0m.fsm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class TransitionTest {

    private static final TestState FROM_STATE = TestState.FROM;
    private static final TestState TO_STATE = TestState.TO;
    private static final int ACTION_LIST_SIZE = 3;
    private static final List<Action<String>> ACTIONS = Collections.nCopies(ACTION_LIST_SIZE, (context) -> {
    });
    private static final String FROM_STATE_NULL_MESSAGE = "FromState cannot be null";
    private static final String TO_STATE_NULL_MESSAGE = "ToState cannot be null";
    private static final String ACTION_NULL_MESSAGE = "Action cannot be null";
    private static final String ACTIONS_NULL_MESSAGE = "Actions cannot be null";

    @Test
    public void Transition_withValidValues_shouldNotThrowException() {
        Transition<TestState, String> transition1 = new Transition<>(FROM_STATE, TO_STATE);
        Assertions.assertEquals(FROM_STATE, transition1.getFromState());
        Assertions.assertEquals(TO_STATE, transition1.getToState());
        Assertions.assertTrue(transition1.getActions().isEmpty());

        Transition<TestState, String> transition2 = new Transition<>(FROM_STATE, TO_STATE, ACTIONS);
        Assertions.assertEquals(ACTIONS.size(), transition2.getActions().size());
    }

    @Test
    public void Transition_withNullValues_shouldThrowException() {
        NullPointerException npe1 = Assertions.assertThrows(
                NullPointerException.class, () -> new Transition<>(null, TO_STATE));
        Assertions.assertEquals(FROM_STATE_NULL_MESSAGE, npe1.getMessage());

        NullPointerException npe2 = Assertions.assertThrows(
                NullPointerException.class, () -> new Transition<>(FROM_STATE, null));
        Assertions.assertEquals(TO_STATE_NULL_MESSAGE, npe2.getMessage());
    }

    @Test
    public void equalsTest() {
        Transition<TestState, String> transition1 = new Transition<>(FROM_STATE, TO_STATE);
        Transition<TestState, String> transition2 = new Transition<>(FROM_STATE, TO_STATE, ACTIONS);
        Transition<TestState, String> transition3 = new Transition<>(TO_STATE, TO_STATE, ACTIONS);

        Assertions.assertEquals(transition1, transition1);
        Assertions.assertNotEquals(transition1, new Object());
        Assertions.assertEquals(transition1, transition2);
        Assertions.assertNotEquals(transition2, transition3);
        Assertions.assertNotEquals(transition3, transition1);
    }

    @Test
    public void hashCodeTest() {
        Transition<TestState, String> transition1 = new Transition<>(FROM_STATE, TO_STATE);
        Transition<TestState, String> transition2 = new Transition<>(FROM_STATE, TO_STATE, ACTIONS);
        Transition<TestState, String> transition3 = new Transition<>(TO_STATE, TO_STATE, ACTIONS);

        Assertions.assertEquals(transition1.hashCode(), transition2.hashCode());
        Assertions.assertNotEquals(transition2.hashCode(), transition3.hashCode());
        Assertions.assertNotEquals(transition3.hashCode(), transition1.hashCode());
    }

    @Test
    public void builderTest() {
        Transition<TestState, String> transition1 = Transition.<TestState, String>builder()
                .fromState(FROM_STATE)
                .toState(TO_STATE)
                .addActions(ACTIONS)
                .addAction(ACTIONS.get(0))
                .build();
        Transition<TestState, String> transition2 = new Transition<>(FROM_STATE, TO_STATE);
        Assertions.assertEquals(transition1, transition2);
        Assertions.assertEquals(ACTION_LIST_SIZE + 1, transition1.getActions().size());

        NullPointerException npe1 = Assertions.assertThrows(
                NullPointerException.class, () -> Transition.builder().fromState(null));
        Assertions.assertEquals(FROM_STATE_NULL_MESSAGE, npe1.getMessage());

        NullPointerException npe2 = Assertions.assertThrows(
                NullPointerException.class, () -> Transition.builder().toState(null));
        Assertions.assertEquals(TO_STATE_NULL_MESSAGE, npe2.getMessage());

        NullPointerException npe3 = Assertions.assertThrows(
                NullPointerException.class, () -> Transition.builder().addAction(null));
        Assertions.assertEquals(ACTION_NULL_MESSAGE, npe3.getMessage());

        NullPointerException npe4 = Assertions.assertThrows(
                NullPointerException.class, () -> Transition.builder().addActions(null));
        Assertions.assertEquals(ACTIONS_NULL_MESSAGE, npe4.getMessage());
    }
}
