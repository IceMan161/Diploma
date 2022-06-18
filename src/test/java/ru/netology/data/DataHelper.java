package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    public static String approvedCardNumber = "4444 4444 4444 4441";
    public static String declinedCardNumber = "4444 4444 4444 4442";
    public static String notExistCardNumber = "7777 7777 7777 7777";
    public static Faker fakerEN = new Faker(new Locale("en-US"));
    public static Faker fakerRU = new Faker(new Locale("ru-RU"));
    public static String differentTypeOfCard = new Faker(new Locale("en-US")).finance().creditCard();

    @Data
    @AllArgsConstructor
    public static class CardInfo {
        String number, month, year, holder, cvc;
    }

    public static CardInfo getCardInfo(String number) {
        String month = String.format("%2d", fakerEN.number().numberBetween(1, 12)).replace(" ", "0");
        int currentYear = LocalDate.now().getYear();
        String year = Integer.toString(fakerEN.number().numberBetween(currentYear + 1, currentYear + 5)).substring(2);
        String holder = fakerEN.name().firstName() + " " + fakerEN.name().lastName();
        String cvc = fakerEN.numerify("###");
        return new CardInfo(number, month, year, holder, cvc);
    }

    public static CardInfo getApprovedCardNumber() {
        return getCardInfo(approvedCardNumber);
    }

    public static CardInfo getDeclinedCardNumber() {
        return getCardInfo(declinedCardNumber);
    }

    public static CardInfo getNumberIfNotExistInBase() {
        return getCardInfo(notExistCardNumber);
    }

    public static CardInfo getEmptyNumber() {
        return getCardInfo("");
    }

    public static CardInfo getCardNumberLess16Digits() {
        return getCardInfo("card" + approvedCardNumber.substring(2));
    }

    public static CardInfo getNumberFaker() {
        return getCardInfo(differentTypeOfCard);
    }

    public static CardInfo getEmptyMonth() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setMonth("");
        return card;
    }

    public static CardInfo getMonthWithZero() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setMonth(("00"));
        return card;
    }

    public static CardInfo getMonthMore12() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setMonth(Integer.toString(fakerEN.number().numberBetween(17, 27)));
        return card;
    }

    public static CardInfo getMonthOneDigit() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setMonth(Integer.toString(fakerEN.number().numberBetween(3, 7)));
        return card;
    }

    public static CardInfo getEmptyYear() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setYear("");
        return card;
    }

    public static CardInfo getExceedingYearBy6() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setYear(LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy")));
        return card;
    }

    public static CardInfo getYearhOneDigit() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setYear(Integer.toString(fakerEN.number().numberBetween(1, 9)));
        return card;
    }

    public static CardInfo getYearZero() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setYear("00");
        return card;
    }

    public static CardInfo getExpiredCard() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setYear(LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy")));
        return card;
    }

    public static CardInfo getEmptyHolderName() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setHolder("");
        return card;
    }

    public static CardInfo getHolderWithoutName() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setHolder(fakerEN.name().lastName());
        return card;
    }

    public static CardInfo getHolderWithSpaces() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setHolder(fakerEN.name().firstName() + "   " + fakerEN.name().lastName());
        return card;
    }

    public static CardInfo getDigitsHolderName() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setHolder(fakerEN.regexify("[0-9]{7,10}"));
        return card;
    }

    public static CardInfo getHolderInCyrillic() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setHolder(fakerRU.name().firstName() + " " + fakerRU.name().lastName());
        return card;
    }

    public static CardInfo getEmptyCvcCode() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setCvc("");
        return card;
    }

    public static CardInfo getCvcCodeTwoDigits() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setCvc(fakerEN.regexify("[0-9]{2}"));
        return card;
    }
    public static CardInfo getCvcCodeOneDigits() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setCvc(fakerEN.regexify("[0-9]{1}"));
        return card;
    }

    public static CardInfo getHolderNameFieldContainsCharacters() {
        CardInfo card = getCardInfo(approvedCardNumber);
        card.setHolder(fakerEN.regexify("[\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\-\\_\\+]{5,10}"));
        return card;
    }



}
