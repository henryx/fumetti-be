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

package com.application.fumettibe.db.resources;

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

public class DbCollane extends Database {
    public DbCollane() {
        super();
    }

    @Override
    public void insert(JsonObject data) throws NamingException, SQLException, ParseException {
    }

    @Override
    public JsonArray select() throws SQLException, NamingException {
        JsonArrayBuilder values;
        String query = "SELECT id_collana, nome FROM collane ORDER BY nome";

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
