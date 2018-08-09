package com.example.service.impl;

import akka.NotUsed;
import com.example.service.model.ColorData;
import com.example.service.api.ColorService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class ColorServiceImpl implements ColorService {
    private static final Logger logger = LoggerFactory.getLogger(ColorData.class);
    private static final String DATA_MASTER = ConfigFactory.load().getString("data.file");
    private static final int SUCCESS_CODE = 200;
    private static final int NOT_FOUND_CODE = 404;
    private static final int BACKEND_ERROR_CODE = 500;
    private static final int AUTHORIZATION_FAILURE_CODE = 401;
    private static final String NOT_FOUND = "NOT_FOUND";
    private static final String BACKEND_ERROR = "BACKEND_ERROR";
    private static final String AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE";
    private static final String AUTHORIZATION_KEY = "authorization_key";
    private static final String AUTHORIZATION_VALUE = "7208015cfb39d49fd39b1339f4627281";

    @Override
    public ServiceCall<NotUsed, ColorData> read(String color) {
        return request -> {
            try {
                Optional<ColorData> colorData = getData(color);
                if (colorData.isPresent()) {
                    logger.info("Data retrieved successfully for color {} ..", color);
                    return CompletableFuture.completedFuture(colorData.get());
                } else {

                    return CompletableFuture.completedFuture(getNotFoundResponse(color));
                }
            } catch (IOException e) {
                logger.error("Exception occurred when trying to read json for color {}", color);
                return CompletableFuture.completedFuture(getBackendErrorResponse(color));
            }
        };
    }

    private ColorData getBackendErrorResponse(String color) {
        logger.info("Getting failure response for color {} ..", color);
        return new ColorData() {
            {
                setColor(color);
                setColorCode(BACKEND_ERROR);
                setRgbValues(BACKEND_ERROR);
            }
        };
    }

    private ColorData getNotFoundResponse(String color) {
        logger.info("Getting failure response for color {} ..", color);
        return new ColorData() {
            {
                setColor(color);
                setColorCode(NOT_FOUND);
                setRgbValues(NOT_FOUND);
            }
        };
    }

    private Optional<ColorData> getData(String color) throws IOException {
        List<ColorData> colorDataList = readJsonFile(DATA_MASTER);
        if (colorDataList.isEmpty()) {
            return Optional.empty();
        } else {
            Predicate<ColorData> predicate = colorData -> colorData.getColor().equalsIgnoreCase(color);
            if (colorDataList.stream().noneMatch(predicate)) {
                logger.warn("No data found for color {} ..", color);
                return Optional.empty();
            } else {
                return colorDataList.stream().filter(predicate).findFirst();
            }
        }
    }

    private List<ColorData> readJsonFile(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        logger.info("Attempting to read {} ..", fileName);
        return mapper.readValue(new File(classLoader.getResource(fileName).getPath()),
                    new TypeReference<List<ColorData>>() {
                    });
    }

}
