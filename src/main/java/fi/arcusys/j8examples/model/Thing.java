package fi.arcusys.j8examples.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

public class Thing {
    private long id;

    @Length(max = 255)
    private String title;

    private Instant moment;

    public Thing() {
    }

    public Thing(long id, String title, Instant moment) {
        this.id = id;
        this.title = title;
        this.moment = moment;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getTitle() {
        return title;
    }

    @JsonProperty
    public Instant getMoment() {
        return moment;
    }

    @JsonProperty
    public String getISO8601DateTime() {
        return moment.toString();
    }

    @JsonProperty
    public String getPrettyDateTime() {
        DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale("fi"));
        return moment.atZone(ZoneId.of("Europe/Helsinki")).format(f);
    }

    @JsonProperty
    public Date toOldDate() {
        return Date.from(moment);
    }

    @JsonProperty
    public Year year() {
        return Year.now(Clock.fixed(moment, ZoneId.of("Europe/Helsinki")));
    }

    @JsonProperty
    public Long durationSinceCreation() {
        return Duration.between(moment, Instant.now()).getSeconds();
    }

}
