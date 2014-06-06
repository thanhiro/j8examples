package fi.arcusys.j8examples.resource;

import fi.arcusys.j8examples.model.Thing;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Map;

@Path("/things")
@Produces(MediaType.APPLICATION_JSON)
public class ThingsWithDateAndTimeResource extends BaseResource {
    public ThingsWithDateAndTimeResource(Map<Long, Thing> database) {
        super(database);
    }

    @GET
    public Collection<Thing> list() {
        return database.values();
    }

    @Path("/{id}")
    @GET
    public Thing get(@PathParam("id") Long id) {
        return database.get(id);
    }
}
