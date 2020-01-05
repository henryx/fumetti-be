package com.application.fumettibe.db;

import javax.json.Json;
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
    }

    @Override
    public void insert(JsonObject data) throws SQLException, ParseException {
        // Not implemented
    }

    public ArrayList<JsonObject> select() throws NamingException, SQLException {
        ArrayList<JsonObject> values;

        prepare();

        values = new ArrayList<>();
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
        return values;
    }
}
