package com.springframework.msscbeerservice.web.controller;

import com.springframework.msscbeerservice.services.BeerServiceImpl;
import com.springframework.msscbeerservice.web.model.BeerDto;
import com.springframework.msscbeerservice.web.model.BeerPagedList;
import com.springframework.msscbeerservice.web.model.BeerStyleEnum;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by jt on 2019-05-12.
 */
@RequestMapping("/api/v1/beer")
@RestController
@RequiredArgsConstructor
public class BeerController {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerServiceImpl beerService;


    @GetMapping(produces = "application/json")
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") @Positive Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false, defaultValue = "25") @Positive Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle) {
        pageNumber = pageNumber < 0 ? DEFAULT_PAGE_NUMBER : pageNumber;
        pageSize = pageSize < 1 ? DEFAULT_PAGE_SIZE : pageSize;
        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize));
        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }
    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){

        return new ResponseEntity<>(beerService.getById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDto> saveNewBeer(@RequestBody @Validated BeerDto beerDto){

        return new ResponseEntity<>(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDto> updateBeerById(@PathVariable UUID beerId, @RequestBody @Validated BeerDto beerDto){

        return new ResponseEntity<>(beerService.updateBeerById(beerId, beerDto), HttpStatus.NO_CONTENT);
    }
}