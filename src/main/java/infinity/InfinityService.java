package infinity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ratpack.exec.Execution;
import ratpack.service.Service;
import ratpack.service.StartEvent;
import ratpack.service.StopEvent;

@Singleton
public class InfinityService implements Service {

    InfinityAction action;

    @Inject
    public InfinityService() {
    }

    @Override
    public void onStart(StartEvent event) throws Exception {
        action = new InfinityAction();
        Execution.fork().start(action);
    }

    @Override
    public void onStop(StopEvent event) throws Exception {

    }
}
