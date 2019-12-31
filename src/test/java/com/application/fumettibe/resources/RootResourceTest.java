package com.application.fumettibe.resources;

import com.application.fumettibe.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.*;

public class RootResourceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    @Test
    public void getJson() {
        JsonObject res = Json.createObjectBuilder()
                .add("msg", "Fumetti REST API")
                .add("op", "ok")
                .build();

        String responseMsg = target.path("/").request(MediaType.APPLICATION_JSON_TYPE).get(String.class);

        assertEquals(res.toString(), responseMsg);
    }
}