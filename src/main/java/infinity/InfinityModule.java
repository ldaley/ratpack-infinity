package infinity;

import ratpack.guice.ConfigurableModule;

public class InfinityModule extends ConfigurableModule<InfinityModule.Config> {

    @Override
    protected void configure() {
        bind(InfinityService.class);
    }

    public static class Config {

    }
}
