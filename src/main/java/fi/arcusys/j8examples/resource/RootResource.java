package fi.arcusys.j8examples.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.concurrent.atomic.LongAdder;

import static fi.arcusys.j8examples.conf.Constants.*;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RootResource {

    private final LongAdder adder;

    public RootResource(LongAdder adder) {
        this.adder = adder;
    }


    @GET
    // Here I'd like to have import aliasing feature
    public Map<String, Object> index(@QueryParam("reset") com.google.common.base.Optional<String> reset) {
        adder.increment();
        String base64Str = java.util.Base64.getEncoder().encodeToString("index".getBytes());

        if (reset.isPresent()) {
            adder.reset();
        }

        Map<String, Object> obj = new HashMap<>();
        obj.put("base64", base64Str);
        obj.put("counter", Long.toString(adder.sum()));

        Optional<String> opt = Optional.ofNullable("MaybeEmpty"); //Optional.empty();
        opt.ifPresent(s -> obj.put("optionalValue", s));

        obj.put("urls", URLS);
        return obj;
    }

}
