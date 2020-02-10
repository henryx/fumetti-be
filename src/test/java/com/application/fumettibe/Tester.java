package com.application.fumettibe;

import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public abstract class Tester {
    private Server server;
    protected WebTarget target;

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
}
