package com.ven.social.resources;

import com.ven.social.RequestAuthenticationUtil;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Path("/twitter")
public class TwitterResource extends WebhookResource {

    private static final String AUTH_ALGORITHM = "HmacSHA256";
    private static final String AUTH_HEADER_KEY = "sha256=";
    private static final String AUTH_SIGNATURE_FIELD = "X-Twitter-Webhooks-Signature";
    private static final String CONSUMER_SECRET_KEY = "O1BQ6DKMVhDNNBpTrGWlKLXDTeEyHWABAmGMYyq8U";

    public TwitterResource(int bufferSize) {
        super(bufferSize);
    }

    /**
     * This method responds to frequent validations done by Twitter to verify if
     * our Webhook is up or not.
     *
     * @param crcToken Twitter token which has to be validated
     * @return Response json which twitter needed information
     */
    @GET
    public Response challengeResponseCheck(@QueryParam("crc_token") String crcToken) {

        log.info("[GET][Twitter] Twitter Activity API Challenge Response Check, crc_token : {}", crcToken);

        Response finalResponse;
        try {
            String responseToken = buildCRCResponseToken(crcToken);
            finalResponse = Response.ok().entity(responseToken).build();

        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("[GET][Twitter] Error creating digest with crc token.", e);
            finalResponse = Response.status(Response.Status.BAD_REQUEST).build();
        }

        return finalResponse;
    }

    private String buildCRCResponseToken(String crcToken) throws InvalidKeyException, NoSuchAlgorithmException {

        byte[] crcTokenDigest = RequestAuthenticationUtil.getDigest(CONSUMER_SECRET_KEY, crcToken, AUTH_ALGORITHM);
        return new StringBuilder()
                .append("{")
                .append("\"response_token\":\"").append(AUTH_HEADER_KEY).append(Base64.getEncoder().encodeToString(crcTokenDigest))
                .append("\"}")
                .toString();
    }

    @Override
    public Map<String, String> extractRequiredHeaders(HttpHeaders requestHeaders) {

        Map<String, String> hMap = new HashMap<>();
        hMap.put(AUTH_SIGNATURE_FIELD, requestHeaders.getHeaderString(AUTH_SIGNATURE_FIELD));

        return hMap;
    }
}
