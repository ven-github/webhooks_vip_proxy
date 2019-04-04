package com.ven.social.resources;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public abstract class AbstractFacebookResource extends WebhookResource {

    private static final String AUTH_SIGNATURE_FIELD = "X-Hub-Signature";

    public AbstractFacebookResource(int bufferSize) {
        super(bufferSize);
    }

    /**
     * This method is called only once while configuring webhook url in our
     * facebook developer application
     *
     * @param hubMode
     * @param verifyToken
     * @param hubChallenge
     * @return hubChallenge in the Response
     */
    @GET
    public Response verify(@QueryParam("hub.mode") String hubMode,
                           @QueryParam("hub.verify_token") String verifyToken,
                           @QueryParam("hub.challenge") String hubChallenge) {

        log.info("[GET][FB] Received Challenge String HubMode {},  HubChallenge {},  Verify Token {}", hubMode, hubChallenge, verifyToken);
        return Response.ok(hubChallenge).build();
    }

    @Override
    public Map<String, String> extractRequiredHeaders(HttpHeaders headers) {
        Map<String, String> hMap = new HashMap<>();
        hMap.put(AUTH_SIGNATURE_FIELD, headers.getHeaderString(AUTH_SIGNATURE_FIELD));

        return hMap;
    }

    public boolean validateVerifyToken(final String verificationToken) {
        return Objects.equals("abc", verificationToken);
    }
}
