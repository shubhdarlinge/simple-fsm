package in.at0m.fsm;

/**
 * An action that should be performed while transitioning from one state to another.
 * When an action is run, it is provided with a context parameter which is an instance
 * of the type the state machine is operating on.
 *
 * @param <T> The type on which the state machine is operating.
 * @author shubhdarlinge
 */
public interface Action<T> {

    /**
     * The method called when this action is performed.
     *
     * @param context The context parameter.
     */
    void run(T context);
}
