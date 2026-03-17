package com.springframework.msscbeerservice.services.inventory;

import com.springframework.msscbeerservice.bootstrap.BeerLoader;
import com.springframework.msscbeerservice.services.inventory.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
@Slf4j
class BeerInventoryServiceRestTemplateImplTest {

    @Autowired
    private BeerInventoryService beerInventoryService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getOnhandInventory() {
        Integer qoh = beerInventoryService.getOnHandInventory(BeerLoader.BEER_1_UUID);
        log.info("Quantity On Hand: {}", qoh);
    }
}
