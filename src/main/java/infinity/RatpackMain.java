package infinity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import ratpack.config.ConfigData;
import ratpack.config.internal.DefaultConfigDataBuilder;
import ratpack.guice.Guice;
import ratpack.handling.RequestLogger;
import ratpack.jackson.Jackson;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

import java.util.Collections;

public class RatpackMain {

    public static void main(String... args) throws Exception {
        RatpackServer.start(server -> {

            ObjectMapper objectMapper = DefaultConfigDataBuilder.newDefaultObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

            server
                .serverConfig(config ->
                    config.port(Integer.parseInt(System.getProperty("ratpack.port", "8080")))
                )
                .registry(Guice.registry(bindings -> {
                    ServerConfig configData = bindings.getServerConfig();
                    bindings.bindInstance(ConfigData.class, configData);

                    bindings.moduleConfig(
                        InfinityModule.class,
                        new InfinityModule.Config().infiniteFork(Boolean.getBoolean("infiniteFork"))
                    );

                    bindings.bindInstance(ObjectMapper.class, objectMapper);

                }))
                .handlers(chain -> {
                    chain.all(RequestLogger.ncsa());
                    chain.get("ping", (ctx) ->
                        ctx.render(Jackson.json(Collections.singletonMap("response", "pong")))
                    );

                });
        });
    }
}
