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

import com.application.fumettibe.Tester;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;

public class RootResourceTest extends Tester {

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