package com.example.deal.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SesCodeGeneratorUtil {

    private static final Random RANDOM_GENERATOR = new SecureRandom();

    public static Integer createSesCode() {
        return RANDOM_GENERATOR.nextInt(1000, 9999);
    }
}
