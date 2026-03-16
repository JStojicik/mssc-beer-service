package com.springframework.msscbeerservice.web.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;


@JsonTest
@ActiveProfiles("snake")
public class BeerDtoSnakeTest extends BaseTest{

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testSnake() throws JacksonException {
        BeerDto beerDto = getDto();

        String jsonString = objectMapper.writeValueAsString(beerDto);

        System.out.println(jsonString);

    }
}
