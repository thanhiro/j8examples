package fi.arcusys.j8examples.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public Map<String, Object> index() {
        adder.increment();
        String base64Str = java.util.Base64.getEncoder().encodeToString("index".getBytes());

        Map<String, Object> obj = new HashMap<>();
        obj.put("base64", base64Str);
        obj.put("counter", Long.toString(adder.sum()));

        Optional<String> opt = Optional.ofNullable("MaybeEmpty");
        opt.ifPresent(s -> obj.put("optionalValueHere", s));

        obj.put("urls", URLS);
        return obj;
    }
}
