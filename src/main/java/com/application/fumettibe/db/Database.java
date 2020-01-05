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

package com.application.fumettibe.db;

import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public abstract class Database implements AutoCloseable{
    protected Connection conn;

    public Database() {
    }

    @Override
    public void close() throws Exception {
        if (this.conn != null) {
            this.conn.close();
        }
    }

    protected void prepare() throws NamingException, SQLException {
        InitialContext ic2 = new InitialContext();
        DataSource ds = (DataSource) ic2.lookup("jdbc/fumettidb");

        this.conn = ds.getConnection();
    }

    public abstract void insert(JsonObject data) throws SQLException, ParseException, NamingException;
    public abstract ArrayList<JsonObject> select() throws SQLException, NamingException;
}
