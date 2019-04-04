package com.ven.social.resources;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Path;

@Slf4j
@Path("/instagram")
public class InstagramResource extends AbstractFacebookResource {

    public InstagramResource(int bufferSize) {
        super(bufferSize);
    }
}
