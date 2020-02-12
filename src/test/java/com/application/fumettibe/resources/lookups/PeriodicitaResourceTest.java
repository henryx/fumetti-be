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

package com.application.fumettibe.resources.lookups;

import com.application.fumettibe.Tester;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;

public class PeriodicitaResourceTest extends Tester {

    @Test
    public void getJson() throws JSONException {
        Invocation.Builder builder = target.path("/periodicita").request(MediaType.APPLICATION_JSON_TYPE);
        var invoke = builder.buildGet().invoke();

        var status = invoke.getStatus();

        MatcherAssert.assertThat(Arrays.asList(200, 400), CoreMatchers.hasItem(status));

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