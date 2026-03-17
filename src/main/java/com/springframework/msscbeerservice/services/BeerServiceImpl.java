package com.springframework.msscbeerservice.services;

import com.springframework.msscbeerservice.domain.Beer;
import com.springframework.msscbeerservice.repositories.BeerRepository;
import com.springframework.msscbeerservice.web.controller.NotFounException;
import com.springframework.msscbeerservice.web.mappers.BeerMapper;
import com.springframework.msscbeerservice.web.model.BeerDto;
import com.springframework.msscbeerservice.web.model.BeerPagedList;
import com.springframework.msscbeerservice.web.model.BeerStyleEnum;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerDto getById(UUID beerId, boolean showInventoryOnHand) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFounException::new);
        return showInventoryOnHand ? beerMapper.beerToBeerDtoEnriched(beer) : beerMapper.beerToBeerDto(beer);
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }
    @Cacheable(cacheNames = "beerUpcCache", key = "#upc", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerDto getBeerByUpc(String upc, boolean showInventoryOnHand) {
        Beer beer = beerRepository.findBeerByUpc(upc).orElseThrow(NotFounException::new);
        return showInventoryOnHand ? beerMapper.beerToBeerDtoEnriched(beer) : beerMapper.beerToBeerDto(beer);
    }

    @Override
    public BeerDto updateBeerById(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFounException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(String.valueOf(beerDto.getBeerStyle()));
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());
        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, boolean showInventoryOnHand, PageRequest pageRequest) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isBlank(beerName) && !ObjectUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, String.valueOf(beerStyle), pageRequest);
        } else if (!StringUtils.isBlank(beerName) && ObjectUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isBlank(beerName) && !ObjectUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerStyle(String.valueOf(beerStyle), pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        beerPagedList = new BeerPagedList(beerPage
                .getContent()
                .stream()
                .map(beer -> showInventoryOnHand ? beerMapper.beerToBeerDtoEnriched(beer) : beerMapper.beerToBeerDto(beer))
                .toList(),
                PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()), beerPage.getTotalElements());
        return beerPagedList;
    }
}
