package com.kickstart;

import com.kickstart.serialization.ResponseProcessors;
import com.strategicgains.repoexpress.exception.DuplicateItemException;
import com.strategicgains.repoexpress.exception.ItemNotFoundException;
import com.strategicgains.restexpress.Format;
import com.strategicgains.restexpress.Parameters;
import com.strategicgains.restexpress.RestExpress;
import com.strategicgains.restexpress.exception.BadRequestException;
import com.strategicgains.restexpress.exception.ConflictException;
import com.strategicgains.restexpress.exception.NotFoundException;
import com.strategicgains.restexpress.pipeline.SimpleConsoleLogMessageObserver;
import com.strategicgains.restexpress.plugin.route.RoutesMetadataPlugin;
import com.strategicgains.restexpress.util.Environment;
import com.strategicgains.syntaxe.ValidationException;
import org.jboss.netty.handler.codec.http.HttpMethod;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main
{
	public static void main(String[] args) throws Exception
	{
        Configuration config = loadEnvironment(args);
        RestExpress server = new RestExpress()
                .setBaseUrl("http://localhost:4040")
                .setName(config.getName())
                .setPort(config.getPort())
                .setDefaultFormat(Format.XML)
                .putResponseProcessor(Format.XML, ResponseProcessors.xml())
                .putResponseProcessor(Format.WRAPPED_XML, ResponseProcessors.wrappedXml())
                .addMessageObserver(new SimpleConsoleLogMessageObserver());


        defineRoutes(server, config);

        if (config.getWorkerCount() > 0)
        {
            server.setExecutorThreadCount(config.getWorkerCount());
        }

        if (config.getExecutorThreadCount() > 0)
        {
            server.setExecutorThreadCount(config.getExecutorThreadCount());
        }

        new RoutesMetadataPlugin().register(server)
                .parameter(Parameters.Cache.MAX_AGE, 86400);	// Cache for 1 day (24 hours).

        mapExceptions(server);
        server.bind(4040);
        server.awaitShutdown();
    }

    /**
     * @param server
     * @param config
     */
    private static void defineRoutes(RestExpress server, Configuration config)
    {
        server.uri("/welcome", config.getUsersController())
                .action("read", HttpMethod.GET)
                .method(HttpMethod.POST);

        server.uri("/users/name/{name}.{format}", config.getUsersController())
                .action("readAll", HttpMethod.GET);

        server.uri("/conversion/InchToFeet/{value}.{format}", config.getConversionController())
                .action("convertInchToFeet", HttpMethod.GET)
                .action("convertInchToFeet", HttpMethod.POST);


        server.uri("/conversion/FeetToInch/{value}.{format}", config.getConversionController())
                .action("convertFeetToInch", HttpMethod.GET)
                .method(HttpMethod.POST);


    }

    /**
     * @param server
     */
    private static void mapExceptions(RestExpress server)
    {
    	server
    	.mapException(ItemNotFoundException.class, NotFoundException.class)
    	.mapException(DuplicateItemException.class, ConflictException.class)
    	.mapException(ValidationException.class, BadRequestException.class);
    }

    private static Configuration loadEnvironment(String[] args)
            throws FileNotFoundException, IOException
    {
        if (args.length > 0)
        {
            return Environment.from(args[0], Configuration.class);
        }

        return Environment.fromDefault(Configuration.class);
    }
}
