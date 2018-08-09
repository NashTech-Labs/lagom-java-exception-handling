package com.example.service.impl;

import com.example.service.model.ColorData;
import mockit.Tested;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ColorServiceImplTest {

    @Tested
    ColorServiceImpl colorService;

    private static final String COLOR = "ORANGE";
    private static final String COLOR_CODE = "#FF5500";
    private static final String RGB_VALUES = "255,85,0";

    @Test
    public void readTest() throws Exception{
        ColorData data = colorService.read(COLOR).invoke()
                .toCompletableFuture().get(10, TimeUnit.SECONDS);

        assertEquals("color code does not match", COLOR_CODE, data.getColorCode());
        assertEquals("RGB values do not match", RGB_VALUES, data.getRgbValues());
    }

}
