package infinity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ratpack.exec.Execution;
import ratpack.func.Action;
import ratpack.service.Service;
import ratpack.service.StartEvent;
import ratpack.service.StopEvent;

@Singleton
public class InfinityService implements Service {

    private final InfinityModule.Config config;
    private Action<Execution> action;

    @Inject
    public InfinityService(InfinityModule.Config config) {
        this.config = config;
    }

    @Override
    public void onStart(StartEvent event) throws Exception {
        if (config.isInfiniteFork()) {
            // Infinitely forks executions works as expected.
            action = new InfinityForkAction();
        } else {
            // Infinitely recursive Promise causes heap exhaustion.
            action = new InfinityRecursionAction();
        }

        Execution.fork().start(action);
    }

    @Override
    public void onStop(StopEvent event) throws Exception {

    }
}
