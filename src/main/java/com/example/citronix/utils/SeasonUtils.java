package com.example.citronix.utils;

import com.example.citronix.model.enums.Season;

import java.time.LocalDate;
import java.time.Month;

public class SeasonUtils {

    public static Season seasonFromDate(LocalDate date) {
        Month month = date.getMonth();

        switch (month) {
            case DECEMBER:
            case JANUARY:
            case FEBRUARY:
                return Season.WINTER;
            case MARCH:
            case APRIL:
            case MAY:
                return Season.SPRING;
            case JUNE:
            case JULY:
            case AUGUST:
                return Season.SUMMER;
            case SEPTEMBER:
            case OCTOBER:
            case NOVEMBER:
                return Season.AUTUMN;
            default:
                throw new IllegalArgumentException("Invalid month in date: " + date);
        }
    }
}
