package com.ven.social;

import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
public class AppConfiguration extends Configuration {

    @Getter
    @Setter
    @NotNull
    private Integer bufferSize;
}
