package infinity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.exec.Blocking;
import ratpack.exec.Execution;
import ratpack.exec.Promise;
import ratpack.func.Action;

import java.util.UUID;

public class InfinityForkAction implements Action<Execution> {

    private static final Logger LOG = LoggerFactory.getLogger(InfinityForkAction.class);

    public InfinityForkAction() {
    }

    @Override
    public void execute(Execution execution) throws Exception {
        poll()
            .result(r -> {
                if (r.isSuccess()) {
                    Execution.fork().start(this);
                    return;
                }

                Throwable error = r.getThrowable();
                LOG.error("Unexpected error", error);
            });
    }

    private Promise<Void> poll() {
        return getFoo()
            .flatMap(this::useFoo);
    }

    private Promise<Foo> getFoo() {
        return Blocking.get(Foo::new);
    }

    private Promise<Void> useFoo(Foo foo) {
        return Blocking.get(() -> {
            LOG.debug("foo={}", foo.name);
            return null;
        });
    }

    private static class Foo {
        String name = UUID.randomUUID().toString();
    }
}
