package com.application.fumettibe.resources;

import com.application.fumettibe.Tester;
import org.hamcrest.CoreMatchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;

public class CollaneResourceTest extends Tester {

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