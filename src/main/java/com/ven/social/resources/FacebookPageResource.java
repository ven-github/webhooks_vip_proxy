package com.ven.social.resources;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Path;

@Slf4j
@Path("/fb_page")
public class FacebookPageResource extends AbstractFacebookResource {
    public FacebookPageResource(int bufferSize) {
        super(bufferSize);
    }
}
