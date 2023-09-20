package com.gft.inditex.domain;

import lombok.Getter;

@Getter
public enum Brands {
    ZARA(1);
    private final int value;
    Brands(int value) {
        this.value = value;
    }
    public static Brands getByValue(int value) {
        for (Brands brand : Brands.values()) {
            if (brand.value == value) {
                return brand;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }
}
