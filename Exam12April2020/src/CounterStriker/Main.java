package CounterStriker;

import CounterStriker.core.Controller;
import CounterStriker.core.ControllerImpl;
import CounterStriker.core.EngineImpl;
import CounterStriker.core.Engine;

public class Main {
    public static void main(String[] args) {
        Engine engine = new EngineImpl();
        engine.run();
    }
}
