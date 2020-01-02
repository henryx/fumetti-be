package com.application.fumettibe.resources;

import com.application.fumettibe.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class RootResourceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() {
        Main m = new Main();

        // start the server
        server = m.startServer();

        // create the client
        Client c = ClientBuilder.newClient();

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() {
        server.shutdownNow();
    }

    @Test
    public void getJson() throws JSONException {
        JSONObject res = new JSONObject()
                .put("msg", "Fumetti REST API")
                .put("op", "ok");

        String responseMsg = target.path("/").request(MediaType.APPLICATION_JSON_TYPE).get(String.class);

        JSONAssert.assertEquals(responseMsg, res, false);
    }

    @Test
    public void notFound() throws JSONException {
        Invocation.Builder builder = target.path("/notfound").request(MediaType.APPLICATION_JSON_TYPE);
        var invoke = builder.buildGet().invoke();

        var status = invoke.getStatus();
        var responseMsg = invoke.readEntity(String.class);

        JSONObject resp = new JSONObject()
                .put("message", "Not found")
                .put("op", "ko");

        Assert.assertEquals(404, status);
        JSONAssert.assertEquals(responseMsg, resp, false);
    }
}