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
import com.application.fumettibe.Tester;
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

import static org.junit.Assert.assertEquals;

public class AlbiResourceTest extends Tester {

    @Test
    public void getJson() throws JSONException {
        JSONObject res = new JSONObject()
                .put("msg", "Not implemented")
                .put("op", "ok");

        String responseMsg = target.path("/albi").request(MediaType.APPLICATION_JSON_TYPE).get(String.class);

        assertEquals(res.toString(), responseMsg);
    }

    @Test
    public void getJsonParam() throws JSONException {
        String serie = "Berserk";

        JSONObject res = new JSONObject()
                .put("msg", "Not implemented")
                .put("op", "ok")
                .put("arg", serie);

        String responseMsg = target.path(String.format("/albi/%s", serie)).request().get(String.class);

        JSONAssert.assertEquals(responseMsg, res, false);
    }

    @Test
    public void postJson() throws JSONException {
        JSONObject res = new JSONObject()
                .put("msg", "POST request")
                .put("value", "test")
                .put("op", "ok");

        JSONObject req = new JSONObject().put("type", "test");

        String responseMsg = target.path("/albi").request().post(Entity.entity(req.toString(), MediaType.APPLICATION_JSON_TYPE)).readEntity(String.class);

        JSONAssert.assertEquals(responseMsg, res, false);
    }

    @Test
    public void postMalformed() throws JSONException {
        JSONObject res = new JSONObject()
                .put("message", "JSON Error")
                .put("op", "ko");
        String req = "{\"type\": \"test\"";

        Invocation.Builder builder = target.path("/albi").request(MediaType.APPLICATION_JSON_TYPE);
        var invoke = builder.buildPost(Entity.json(req)).invoke();
        var status = invoke.getStatus();
        var responseMsg = new JSONObject(invoke.readEntity(String.class));

        Assert.assertThat(Arrays.asList(500), CoreMatchers.hasItem(status));
        JSONAssert.assertEquals(res, responseMsg, false);
    }

    @Test
    public void delJSon() throws JSONException {
        String serie = "test";

        JSONObject res = new JSONObject()
                .put("msg", "Not implemented")
                .put("op", "ok");

        String responseMsg = target.path(String.format("/albi/%s", serie)).request().delete(String.class);

        assertEquals(res.toString(), responseMsg);
    }
}