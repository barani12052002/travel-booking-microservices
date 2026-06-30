package com.barani.travel.util;

import java.util.UUID;

public class BookingReferenceGenerator {

    public static String generate() {
        return "BK-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }

}