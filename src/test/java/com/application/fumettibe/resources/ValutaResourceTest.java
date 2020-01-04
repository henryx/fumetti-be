/*
 * Copyright (C) 2019 Enrico Bianchi <enrico.bianchi@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.application.fumettibe.resources;

import com.application.fumettibe.Main;
import org.eclipse.jetty.server.Server;
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

import static org.junit.Assert.*;

public class ValutaResourceTest {

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
        Invocation.Builder builder = target.path("/valuta").request(MediaType.APPLICATION_JSON_TYPE);
        var invoke = builder.buildGet().invoke();

        var status = invoke.getStatus();
        var responseMsg = invoke.readEntity(JSONObject.class);

        Assert.assertEquals(200, status);
    }
}