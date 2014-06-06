package fi.arcusys.j8examples.resource;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/nashorn")
@Produces(MediaType.TEXT_PLAIN)
public class NashornResource {
    @GET
    public String index() throws ScriptException, NoSuchMethodException {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("nashorn");
        Invocable inv = (Invocable) engine;

        engine.eval("function getMessage () { " +
            "return \"This is from JavaScript\" }");
        return (String) inv.invokeFunction("getMessage");
    }
}
