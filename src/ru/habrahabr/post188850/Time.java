package ru.habrahabr.post188850;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Locale;

/**
 * @author phpusr
 *         Date: 16.04.14
 *         Time: 12:17
 */

/**
 *  Демонстрация использования java.time.*
 *
 *  (статья) http://java.dzone.com/articles/deeper-look-java-8-date-and
 *  (код) https://github.com/phpusr/Java-8-playground/blob/master/date-time/src/Java8DateTimeExamples.java
 */
public class Time {

    public static void main(String[] args) {
        // Локальная дата
        System.out.println("=== LocalTime");
        LocalTime time = LocalTime.now();
        System.out.println("time: " + time);
        System.out.println("hours: " + time.getHour());
        System.out.println("30 minuts ago: " + time.minusMinutes(30));


        // Локальная дата и время
        System.out.println("\n=== LocalDateTime");
        LocalDateTime date = LocalDateTime.of(2012, 12, 1, 14, 42);
        System.out.println("custom date: " + date);
        System.out.println("nano seconds: " + date.getNano());
        System.out.println("6 months ago: " + date.minusMonths(6));
        System.out.println("last day of month: " + date.with(TemporalAdjusters.lastDayOfMonth()));


        // Форматирование даты
        System.out.println("\n=== DateTimeFormatter");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("ISO_LOCAL_DATE_TIME: " + now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy a hh:mm:ss:SS(n)");
        System.out.println("Pattern: " + now.format(format));
        DateTimeFormatter ru = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale("ru"));
        System.out.println("LocalizedDateTime: " + now.format(ru));
        // Parsing
        LocalDate parse = LocalDate.parse("16.04.2014", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        System.out.println("parse date: " + parse);


        // Представление даты в виде отсчета  от 01.01.1970 (TimeStamp)
        System.out.println("\n=== Instant");
        Instant instant = Instant.now();
        System.out.println("now: " + instant);
        System.out.println("epoch milli: " + instant.toEpochMilli());
        // Разница между началом отсчета в Instant
        Instant offset = LocalDateTime.of(1970, 1, 1, 0, 0, 20, 0).toInstant(ZoneOffset.UTC);
        System.out.println(offset.getEpochSecond());
        System.out.println(offset.toEpochMilli());


        // Вычисление кол-ва прожитых дней с помощью Period и Duration
        System.out.println("\n=== Period & Duration");
        LocalDateTime mytBirthday = LocalDateTime.of(1991, 8, 29, 0, 0);
        LocalDateTime nowDate = LocalDateTime.now();
        Period period = Period.between(mytBirthday.toLocalDate(), nowDate.toLocalDate());
        System.out.println("years: " + period.getYears() + ", months: " + period.getMonths() + ", days: " + period.getDays());
        System.out.println("months: " + period.toTotalMonths());
        Duration duration = Duration.between(mytBirthday, nowDate);
        System.out.println("I live days: " + duration.toDays());
        // Проверка
        long days = (nowDate.toInstant(ZoneOffset.UTC).toEpochMilli() - mytBirthday.toInstant(ZoneOffset.UTC).toEpochMilli()) / (24*60*60*1000);
        System.out.println("I live days (check): " + days);
    }

}
