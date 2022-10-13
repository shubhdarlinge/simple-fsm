package in.at0m.fsm;

import in.at0m.fsm.exception.InvalidTransitionException;
import in.at0m.fsm.transition.Action;
import in.at0m.fsm.transition.ActionContext;
import in.at0m.fsm.transition.Transition;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleStateMachineTest {

    @Test
    public void getTransitionsTest() {
        TrafficLight trafficLight = new TrafficLight();
        Assertions.assertEquals(TrafficLight.buildTransitions().size(), trafficLight.getTransitions().size());
    }

    @Test
    public void conConsumeTest() {
        TrafficLight trafficLight = new TrafficLight();

        Assertions.assertTrue(trafficLight.canConsume(TrafficLight.Event.GO_WARNING));
        Assertions.assertFalse(trafficLight.canConsume(TrafficLight.Event.GO));
        Assertions.assertFalse(trafficLight.canConsume(TrafficLight.Event.STOP_WARNING));
        Assertions.assertFalse(trafficLight.canConsume(TrafficLight.Event.STOP));

        trafficLight.consume(TrafficLight.Event.GO_WARNING);

        Assertions.assertFalse(trafficLight.canConsume(TrafficLight.Event.GO_WARNING));
        Assertions.assertTrue(trafficLight.canConsume(TrafficLight.Event.GO));
        Assertions.assertFalse(trafficLight.canConsume(TrafficLight.Event.STOP_WARNING));
        Assertions.assertFalse(trafficLight.canConsume(TrafficLight.Event.STOP));
    }

    @Test
    public void consumeTest() {
        TrafficLight trafficLight = new TrafficLight();
        Assertions.assertSame(TrafficLight.Color.RED_YELLOW, trafficLight.consume(TrafficLight.Event.GO_WARNING));
        Assertions.assertSame(TrafficLight.Color.GREEN, trafficLight.consume(TrafficLight.Event.GO));
        Assertions.assertSame(TrafficLight.Color.GREEN_YELLOW, trafficLight.consume(TrafficLight.Event.STOP_WARNING));
        Assertions.assertSame(TrafficLight.Color.RED, trafficLight.consume(TrafficLight.Event.STOP));

        Assertions.assertThrows(InvalidTransitionException.class, () -> trafficLight.consume(TrafficLight.Event.GO));
        Assertions.assertEquals(4, trafficLight.getCount());
    }

    @Test
    public void builderTest() {
        List<Transition<TrafficLight.Event, TrafficLight.Color, TrafficLight>> transitions
                = TrafficLight.buildTransitions();
        Transition<TrafficLight.Event, TrafficLight.Color, TrafficLight> transition = transitions.remove(0);

        SimpleStateMachine<TrafficLight.Event, TrafficLight.Color, TrafficLight> trafficLight
                = SimpleStateMachine.<TrafficLight.Event, TrafficLight.Color, TrafficLight>builder()
                .initialState(TrafficLight.Color.RED)
                .transitions(transitions)
                .addTransition(transition)
                .build();
        Assertions.assertEquals(TrafficLight.Color.RED, trafficLight.getCurrentState());
        Assertions.assertEquals(TrafficLight.buildTransitions().size(), trafficLight.getTransitions().size());
    }

    @Test
    public void nullParamTest() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new SimpleStateMachine<TrafficLight.Event, TrafficLight.Color, TrafficLight>(
                        null, Collections.emptyList()));

        Assertions.assertThrows(NullPointerException.class,
                () -> new SimpleStateMachine<TrafficLight.Event, TrafficLight.Color, TrafficLight>(
                        TrafficLight.Color.RED, null));

        SimpleStateMachine<TrafficLight.Event, TrafficLight.Color, TrafficLight> trafficLight
                = new SimpleStateMachine<>(TrafficLight.Color.RED, Collections.emptyList());
        Assertions.assertThrows(NullPointerException.class, () -> trafficLight.canConsume(null));
        Assertions.assertThrows(NullPointerException.class, () -> trafficLight.consume(null, new TrafficLight()));
        Assertions.assertThrows(NullPointerException.class, () -> trafficLight.consume(TrafficLight.Event.GO, null));

        Assertions.assertThrows(NullPointerException.class, () -> SimpleStateMachine
                .<TrafficLight.Event, TrafficLight.Color, TrafficLight>builder().transitions(null));

        Assertions.assertThrows(NullPointerException.class, () -> SimpleStateMachine
                .<TrafficLight.Event, TrafficLight.Color, TrafficLight>builder().addTransition(null));
    }

    /**
     * The state machine for this class looks like this:
     * +---+                            +----------+
     * |RED|--------GO_WARNING------->>>|RED_YELLOW|
     * +---+                            +----------+
     * ^                                     |
     * ^                                     |
     * |                                     |
     * |                                     |
     * STOP                                  GO
     * |                                     |
     * |                                     |
     * |                                     V
     * |                                     V
     * +------------+                      +-----+
     * |GREEN_YELLOW|<<<---STOP_WARNING----|GREEN|
     * +------------+                      +-----+
     */
    private static class TrafficLight extends SimpleStateMachine<TrafficLight.Event, TrafficLight.Color, TrafficLight> {

        @Getter
        private int count;

        public TrafficLight() {
            super(Color.RED, buildTransitions());
            count = 0;
        }

        private static List<Transition<Event, Color, TrafficLight>> buildTransitions() {
            List<Action<Event, Color, TrafficLight>> incrementAction = Collections.singletonList(new IncrementAction());
            List<Transition<Event, Color, TrafficLight>> transitions = new ArrayList<>();
            transitions.add(new Transition<>(Event.GO_WARNING, Color.RED, Color.RED_YELLOW, incrementAction));
            transitions.add(new Transition<>(Event.GO, Color.RED_YELLOW, Color.GREEN, incrementAction));
            transitions.add(new Transition<>(Event.STOP_WARNING, Color.GREEN, Color.GREEN_YELLOW, incrementAction));
            transitions.add(new Transition<>(Event.STOP, Color.GREEN_YELLOW, Color.RED, incrementAction));
            return transitions;
        }

        public Color consume(Event event) {
            return super.consume(event, this);
        }

        public enum Event {
            GO_WARNING, GO, STOP_WARNING, STOP
        }

        public enum Color {
            RED, RED_YELLOW, GREEN, GREEN_YELLOW
        }

        private static class IncrementAction implements Action<Event, Color, TrafficLight> {

            @Override
            public void before(ActionContext<Event, Color, TrafficLight> actionContext) {
                actionContext.getData().count += 2;
            }

            @Override
            public void after(ActionContext<Event, Color, TrafficLight> actionContext) {
                actionContext.getData().count--;
            }
        }
    }
}
