package infinity;

import ratpack.guice.ConfigurableModule;

public class InfinityModule extends ConfigurableModule<InfinityModule.Config> {

    @Override
    protected void configure() {
        bind(InfinityService.class);
    }

    public static class Config {
        private boolean infiniteFork = false;

        public boolean isInfiniteFork() {
            return infiniteFork;
        }

        public Config infiniteFork(boolean infiniteFork) {
            this.infiniteFork = infiniteFork;
            return this;
        }
    }
}
