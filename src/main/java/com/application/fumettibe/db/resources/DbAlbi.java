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

import javax.json.JsonObject;
import javax.naming.NamingException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DbAlbi extends Database {
    public DbAlbi() {
        super();
    }

    @Override
    public void insert(JsonObject data) throws NamingException, SQLException, ParseException {
        String query = "INSERT INTO albi(id_serie, numero_albo, data_pubblicazione, prezzo_copertina, id_valuta, id_rilegatura, id_stato_conservazione, note) VALUES(?,?,?,?,?,?,?,?)";

        prepare();

        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(data.getString("date"));

            pstmt.setInt(1, data.getInt("serie"));
            pstmt.setInt(2, data.getInt("numero"));
            pstmt.setTimestamp(3, new Timestamp(date.getTime()));
            pstmt.setInt(4, data.getInt("prezzo"));
            pstmt.setInt(5, data.getInt("valuta"));
            pstmt.setInt(6, data.getInt("rilegatura"));
            pstmt.setInt(7, data.getInt("conservazione"));
            pstmt.setString(8, data.getString("note"));

            pstmt.executeUpdate();
        }
    }

    @Override
    public ArrayList<JsonObject> select() {
        return null;
    }
}
