package fi.arcusys.j8examples.conf;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class MyConfiguration extends Configuration {
    @NotEmpty
    private String foo;

    public String getFoo() {
        return foo;
    }

    @JsonProperty
    public void setFoo(String foo) {
        this.foo = foo;
    }
}
