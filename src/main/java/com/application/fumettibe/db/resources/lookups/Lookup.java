package com.application.fumettibe.db.resources.lookups;

import com.application.fumettibe.db.Database;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.NamingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

public abstract class Lookup extends Database {
    protected String query;

    public Lookup() {
        super();
    }

    @Override
    public void insert(JsonObject data) throws SQLException, ParseException {
        // Not implemented
    }

    public JsonArray select() throws NamingException, SQLException {
        JsonArrayBuilder values;

        prepare();

        values = Json.createArrayBuilder();
        try (Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                JsonObject data = Json.createObjectBuilder()
                        .add("id", res.getInt(1))
                        .add("name", res.getString(2))
                        .build();

                values.add(data);
            }
        }
        return values.build();
    }
}
