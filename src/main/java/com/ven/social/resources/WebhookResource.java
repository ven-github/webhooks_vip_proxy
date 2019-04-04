package com.ven.social.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.EvictingQueue;
import lombok.Data;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class WebhookResource {

    private final EvictingQueue<Event> queue;

    public WebhookResource(final int bufferSize) {

        this.queue = EvictingQueue.create(bufferSize);
    }
    
    /**
     * Authorizes incoming webhook events
     *
     * @param requestHeaders http request header
     * @param eventBody      message payload
     * @return Response if valid or bad request
     */
    @POST
    public Response hook(@Context HttpHeaders requestHeaders, String eventBody) {
        Response response;
        try {
            System.out.println("Event body : " + eventBody);
            queue.add(new Event(eventBody, extractRequiredHeaders(requestHeaders)));
            response = Response.ok().build();

        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    @GET
    public Response getEvents() throws JsonProcessingException {
        Event event = queue.poll();
        if (Objects.nonNull(event)) {
            Response.ResponseBuilder entity = Response.ok().entity(event.getBody());
            event.getHeaders().forEach((k, v) -> entity.header(k, v));

            return entity.build();
        }

        return Response.noContent().build();
    }

    public abstract Map<String, String> extractRequiredHeaders(HttpHeaders headers);

    @Data
    private class Event {
        private String body;
        private Map<String, String> headers;

        Event(String body, Map<String, String> headers) {
            this.body = body;
            this.headers = new HashMap<>(headers);
        }
    }
}
