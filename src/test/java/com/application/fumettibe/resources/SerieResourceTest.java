package com.application.fumettibe.resources;

import com.application.fumettibe.Main;
import org.eclipse.jetty.server.Server;
import org.hamcrest.CoreMatchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;

public class SerieResourceTest {

    private Server server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        Main m = new Main();

        // start the server
        server = m.startServer();
        server.setStopAtShutdown(true);
        server.start();

        // create the client
        Client c = ClientBuilder.newClient();

        target = c.target(m.toUrl());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void getJson() throws JSONException {
        Invocation.Builder builder = target.path("/serie").request(MediaType.APPLICATION_JSON_TYPE);
        var invoke = builder.buildGet().invoke();

        var status = invoke.getStatus();

        Assert.assertThat(Arrays.asList(200, 400), CoreMatchers.hasItem(status));

        var responseMsg = new JSONObject(invoke.readEntity(String.class));
        Assert.assertTrue(responseMsg.has("op"));

        if (status == 200) {
            Assert.assertTrue(responseMsg.has("data"));
        } else {
            Assert.assertTrue(responseMsg.has("motivation"));
            Assert.assertTrue(responseMsg.has("msg"));
        }
    }

    @Test
    public void postJson() throws JSONException {
        JSONObject res = new JSONObject()
                .put("msg", "POST request")
                .put("value", "test")
                .put("op", "ok");

        JSONObject req = new JSONObject().put("type", "test");

        String responseMsg = target
                .path("/serie")
                .request()
                .post(Entity.entity(req.toString(),
                        MediaType.APPLICATION_JSON_TYPE)).readEntity(String.class);

        JSONAssert.assertEquals(responseMsg, res, false);
    }
}