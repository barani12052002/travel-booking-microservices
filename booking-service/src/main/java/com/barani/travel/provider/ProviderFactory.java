package com.barani.travel.provider;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProviderFactory {

    private final Map<String, BookingProvider> providers;

    public ProviderFactory(List<BookingProvider> providerList){

        providers = providerList.stream().collect(Collectors.toMap(
                        BookingProvider::getProviderCode, Function.identity()));

    }

    public BookingProvider getProvider(String code){

        return providers.get(code);

    }

}