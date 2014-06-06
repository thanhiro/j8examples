package fi.arcusys.j8examples;

import com.codahale.metrics.health.HealthCheck;
import fi.arcusys.j8examples.conf.MyConfiguration;
import fi.arcusys.j8examples.model.Thing;
import fi.arcusys.j8examples.resource.LambdasAndStreamsResource;
import fi.arcusys.j8examples.resource.NashornResource;
import fi.arcusys.j8examples.resource.RootResource;
import fi.arcusys.j8examples.resource.ThingsWithDateAndTimeResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.lang.Thread.*;

public class MyApplication extends Application<MyConfiguration> {

    public static void main(String[] args) throws Exception {
        new MyApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<MyConfiguration> bootstrap) {
    }

    @Override
    public void run(MyConfiguration configuration,
                    Environment environment) {

        environment.jersey().register(createDb());

        // Can't use Lambda here :(
        environment.healthChecks().register("tst", new HealthCheck() {
            @Override
            protected Result check() throws Exception {
                return Result.healthy();
            }
        });

        Map<Long, Thing> db = createDb();

        environment.jersey().register(new RootResource(new LongAdder()));
        environment.jersey().register(new ThingsWithDateAndTimeResource(db));
        environment.jersey().register(new LambdasAndStreamsResource(db));
        environment.jersey().register(new NashornResource());
    }


    private Map<Long, Thing> createDb() {
        Map<Long, Thing> db = new ConcurrentHashMap<>();

        // Using short method reference to method of an
        // arbitrary object of a particular type.
        // Same as: .map(s -> s.toUpperCase())
        //List<String> nu = titles.stream().map(s -> s.toUpperCase());
        List<String> titles = Arrays.asList("lorem", "ipsum", "foo", "bar", "arcusys", "fudeco").
            stream().map(String::toUpperCase).collect(Collectors.toList());

        // Streams and lambda/closures in action
        LongStream.rangeClosed(1, 4)
            .forEach(i -> {
                try {
                    sleep(2000);
                } catch (InterruptedException ignore) {}
                // Closure access to outside scope db
                db.put(i, new Thing(i, titles.get((int) i - 1), Instant.now()));
            });

        return db;
    }

}
