package infinity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.exec.Blocking;
import ratpack.exec.Execution;
import ratpack.exec.Operation;
import ratpack.exec.Promise;
import ratpack.func.Action;

import java.util.UUID;

public class InfinityRecursionAction implements Action<Execution> {

    private static final Logger LOG = LoggerFactory.getLogger(InfinityRecursionAction.class);

    public InfinityRecursionAction() {
    }

    @Override
    public void execute(Execution execution) throws Exception {
        Operation.of(this::loop)
            .onError(e -> LOG.error("Unexpected error", e))
            .then();
    }

    private void loop() {
        poll().then(this::loop);
    }

    private Operation poll() {
        return getFoo().operation(this::useFoo);
    }

    private Promise<Foo> getFoo() {
        return Blocking.get(Foo::new);
    }

    private void useFoo(Foo foo) {
        Blocking.exec(() -> LOG.debug("foo={}", foo.name));
    }

    private static class Foo {
        String name = UUID.randomUUID().toString();
    }
}
