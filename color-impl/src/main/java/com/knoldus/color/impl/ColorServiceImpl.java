package com.knoldus.color.impl;

import akka.NotUsed;
import com.knoldus.common.exceptions.BaseTransportException;
import com.knoldus.common.exceptions.ColorCodeUndefined;
import com.knoldus.common.exceptions.DataNotLoaded;
import com.knoldus.common.exceptions.InternalServerError;
import com.knoldus.common.exceptions.ParameterRequired;
import com.knoldus.common.exceptions.ServiceUnreachable;
import com.knoldus.common.model.ColorData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knoldus.color.api.ColorService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ColorServiceImpl implements ColorService {
    private static final Logger logger = LoggerFactory.getLogger(ColorData.class);
    private static final String CONTENT_FILE = ConfigFactory.load().getString("content.file");
    private static final String VALIDATION_FILE = ConfigFactory.load().getString("validation.file");
    private static List<String> definedColors;

    @Override
    public ServiceCall<NotUsed, ColorData> read(String color) {
        return request -> {

            //If input parameter is empty
            if (color.isEmpty()) {
                throw new ParameterRequired();
            }

            //In case data is not initialized (first request)
            if (!Optional.ofNullable(definedColors).isPresent()) {
                definedColors = readJsonFile(VALIDATION_FILE, String.class);
            }

            //If data getContent from master source is empty
            if (definedColors.isEmpty()) {
                throw new DataNotLoaded();
            }

            //If data getContent from master source does not contain requested key
            if (definedColors.stream().noneMatch(definedColor -> definedColor.equalsIgnoreCase(color))) {
                throw new ColorCodeUndefined(); //ColorCodeUndefined
            }

            //If the requested color is valid, we call a color that gets detailed content for that key
            return hitExternalService(color)
                    .thenApply(colorData -> {
                        logger.info("Data retrieved successfully for color {} ..", color);
                        return colorData;
                    })
                    //If something goes wrong while trying to call the external service
                    .exceptionally(exception -> {
                        logger.info("Internal server error", exception.getMessage());
                        throw new InternalServerError(exception);
                    });
        };
    }

    private CompletionStage<ColorData> hitExternalService(String color) {
        return CompletableFuture.supplyAsync(() -> readJsonFile(CONTENT_FILE, ColorData.class)
                .stream().filter(colorData -> colorData.getColor().equalsIgnoreCase(color)).findFirst()
                .<ServiceUnreachable>orElseThrow(() -> new ServiceUnreachable()));
    }

    private <T> List<T> readJsonFile(String fileName, Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        logger.info("Attempting to getContent {} ..", fileName);
        try {
            return mapper.readValue(new File(classLoader.getResource(fileName).getPath()),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass));
        } catch (IOException e) {
            throw new BaseTransportException(TransportErrorCode.fromHttp(500), e); //Define this
        }
    }

}
