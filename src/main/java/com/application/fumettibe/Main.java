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
import org.eclipse.jetty.server.AsyncRequestLogWriter;
import org.eclipse.jetty.server.CustomRequestLog;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.naming.NamingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Main class.
 */
public class Main {
    private URI uri;

    public Main() {
        var host = Optional.ofNullable(System.getenv("FUMETTI_HOST")).orElse("localhost");
        var port = Optional.ofNullable(System.getenv("FUMETTI_PORT")).orElse("8000");

        try {
            this.setUri(host, Integer.parseInt(port));
        } catch (URISyntaxException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private BasicDataSource setDatasource() throws NoSuchFieldException {
        BasicDataSource bds = new BasicDataSource();
        var host = Optional.ofNullable(System.getenv("FUMETTI_DB_HOST")).orElse("localhost");
        var port = Optional.ofNullable(System.getenv("FUMETTI_DB_PORT")).orElse("5432");
        var db = Optional.ofNullable(System.getenv("FUMETTI_DB_NAME")).orElse("fumetti");
        var user = Optional.ofNullable(System.getenv("FUMETTI_DB_USER")).orElseThrow(() -> new NoSuchFieldException("Cannot retrieve username for database"));
        var password = Optional.ofNullable(System.getenv("FUMETTI_DB_PASSWORD")).orElseThrow(() -> new NoSuchFieldException("Cannot retrieve password for database"));

        bds.setDriverClassName("org.postgresql.Driver");
        bds.setUrl(String.format("jdbc:postgresql://%s:%s/%s", host, port, db));
        bds.setUsername(user);
        bds.setPassword(password);

        return bds;
    }

    private void setUri(String host, int port) throws URISyntaxException {
        this.uri = new URI("http", null, host, port, null, null, null);
    }

    public String toUrl() {
        return this.uri.toString();
    }

    /**
     * Starts Jetty HTTP server exposing JAX-RS resources defined in this application.
     *
     * @return Jetty HTTP server.
     */
    public Server startServer() throws NamingException, NoSuchFieldException {
        Server server = new Server(new InetSocketAddress(this.uri.getHost(), this.uri.getPort()));
        ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        CustomRequestLog logger = new CustomRequestLog(new AsyncRequestLogWriter(), CustomRequestLog.NCSA_FORMAT);

        ctx.setContextPath("/");
        server.setHandler(ctx);

        ResourceConfig cfg = new ResourceConfig();
        ServletContainer container = new ServletContainer(cfg);

        ServletHolder holder = new ServletHolder(container);
        ctx.addServlet(holder, "/*");

        cfg.packages("com.application.fumettibe.resources");
        cfg.register(CorsFilter.class, 1);

        ResourceHandler resourceHandler = new ResourceHandler();
        /* // Useful for future implementations
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setResourceBase("./static/");
         */

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, ctx, new DefaultHandler()});
        server.setHandler(handlers);
        server.setRequestLog(logger);

        new Resource("jdbc/fumettidb", setDatasource());

        return server;
    }

    /**
     * Main method.
     *
     * @param args Arguments passed at startup
     */
    public static void main(String[] args) throws NamingException, NoSuchFieldException {
        final Server server;
        Main m = new Main();
        server = m.startServer();

        System.out.println(String.format("Jetty app started at %s", m.toUrl()));

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

