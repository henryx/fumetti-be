package com.application.fumettibe.resources;

import com.application.fumettibe.Main;
import org.eclipse.jetty.server.Server;
import org.hamcrest.CoreMatchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CollaneResourceTest {

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
        Invocation.Builder builder = target.path("/collane").request(MediaType.APPLICATION_JSON_TYPE);
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
}