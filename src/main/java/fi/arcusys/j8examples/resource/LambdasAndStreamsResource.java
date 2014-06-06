package fi.arcusys.j8examples.resource;

import fi.arcusys.j8examples.model.Thing;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.function.Function;

@Path("/lambda")
@Produces(MediaType.APPLICATION_JSON)
public class LambdasAndStreamsResource extends BaseResource {

    public LambdasAndStreamsResource(Map<Long, Thing> database) {
        super(database);
    }

    @Path("/async")
    @GET
    public void asyncListWithLambda(@Suspended final AsyncResponse asyncResponse) {
        new Thread(() -> asyncResponse.resume(database.values())).start();
    }

    @Path("/filtered-and-summed")
    @GET
    public void filteringAndSumming(@Suspended final AsyncResponse asyncResponse) {
        new Thread(() -> asyncResponse.resume(database.values().parallelStream().filter(t ->
            t.getTitle().charAt(0) == 'F').mapToLong(Thing::getId).reduce(0, Long::sum))).start();
        //reduce(0, Long::sum) can be shorted by convenience method LongStream::sum
    }

    @Path("/sorting-infinite")
    @GET
    public int[] sortingTheInfinite() {
        Random rnd = new Random();
        return rnd.ints().limit(30).map(x -> x * 2).sorted().toArray();
    }

    @Path("/summary-statistics")
    @GET
    public Map<String, Object> summaryStatistics() {
        Map<String, Object> obj = new HashMap<>();

        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics stats = primes.stream().mapToInt(x -> x).summaryStatistics();

        obj.put("max", stats.getMax());
        obj.put("min", stats.getMin());
        obj.put("sum", stats.getSum());
        obj.put("average", stats.getAverage());

        return obj;
    }

    @Path("/create-lambda")
    @GET
    public Map<String, Object> createLambda() {
        Map<String, Object> obj = new HashMap<>();

        Function<Integer, Integer> l = x -> x * 2;
        obj.put("calculatedInLambda", Integer.toString(l.apply(4)));

        ThingFuncInterface tfi = createFromThingInterface(t -> t.getTitle());
        obj.put("fromDefaultMethod", tfi.hello());

        return obj;
    }

    public ThingFuncInterface createFromThingInterface(ThingFuncInterface tfi) {
        return tfi;
    }

    @FunctionalInterface
    public interface ThingFuncInterface {
        public String doSomething(Thing t);
        public default String hello() { return "Hello!"; }
    }
}
