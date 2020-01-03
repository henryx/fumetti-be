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

package com.application.fumettibe;

import org.apache.commons.dbcp2.BasicDataSource;
import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.naming.NamingException;

/**
 * Main class.
 */
public class Main {
    // TODO: make URI configurable
    public static final String BASE_URI = "http://localhost:8080/";

    private BasicDataSource setDatasource() {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("org.postgresql.Driver");
        bds.setUrl(String.format("jdbc:postgresql://%s:%s/%s", "localhost", 5432, "testdb"));
        bds.setUsername("root");
        bds.setPassword("password");

        return bds;
    }

    /**
     * Starts Jetty HTTP server exposing JAX-RS resources defined in this application.
     *
     * @return Jetty HTTP server.
     */
    public Server startServer() throws NamingException {
        Server server = new Server(8080);
        ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

        ctx.setContextPath("/");
        server.setHandler(ctx);

        ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/*");
        serHol.setInitOrder(1);
        serHol.setInitParameter("jersey.config.server.provider.packages", "com.application.fumettibe");

        ResourceHandler resourceHandler = new ResourceHandler();
        /* // Useful for future implementations
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
         */

        resourceHandler.setResourceBase("/Users/pivotal/workspace/jersey-jetty-jndi-example/src/main/webapp");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, ctx , new DefaultHandler()});
        server.setHandler(handlers);

        new Resource("jdbc/fumettidb", setDatasource());

        return server;
    }

    /**
     * Main method.
     *
     * @param args Arguments passed at startup
     */
    public static void main(String[] args) throws NamingException {
        final Server server;
        Main m = new Main();
        server = m.startServer();

        System.out.println(String.format("Jetty app started at %s", BASE_URI));

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.destroy();
        }
    }
}

