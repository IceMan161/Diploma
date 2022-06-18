package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.SqlHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PurchasePage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class PayCardTest extends TestPage{

    MainPage mainPage = new MainPage();
    PurchasePage purchasePage = new PurchasePage();

    @BeforeEach
    void setUpForPayWithCard() {
        mainPage.payDebitCard();
    }

    @Test
    @DisplayName("Успешная покупка тура при оплате картой со статусом APPROVED")
    public void shouldSuccessPayIfValidApprovedCard() {
        val cardData = getApprovedCardNumber();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.successTransactionNotification();
        val paymentStatus = SqlHelper.getStatusDebitPaymentEntity();
        assertEquals("APPROVED", paymentStatus);
    }

    @Test
    @DisplayName("Неудавшаяся операция покупке тура по карте со статусом DECLINED")
    public void shouldFailurePayIfValidDeclinedCard() {
        val cardData = getDeclinedCardNumber();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.failureTransactionNotification();
        val paymentStatus = SqlHelper.getStatusDebitPaymentEntity();
        assertEquals("DECLINED", paymentStatus);
    }

    @Test
    @DisplayName("Поле номера карты не заполнено")
    public void shouldHaveEmptyCardNumber() {
        val cardData = getEmptyNumber();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.emptyFieldError();
    }

    @Test
    @DisplayName("Количество цифр в номере карты меньше 16")
    public void shouldHaveNumberIfFewDigits() {
        val cardData = getCardNumberLess16Digits();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Несуществующий номер карты")
    public void shouldHaveNotExistentCardNumber() {
        val cardData = getNumberIfNotExistInBase();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.failureTransactionNotification();
    }

    @Test
    @DisplayName("Различные типы карт")
    public void shouldHaveNumberIfDifferentTypeOfCard() {
        val cardData = getNumberFaker();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.failureTransactionNotification();
    }

    @Test
    @DisplayName("Поле месяц не заполнено")
    public void shouldHaveEmptyMonth() {
        val cardData = getEmptyMonth();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.emptyFieldError();
    }

    @Test
    @DisplayName("Поле месяц содержит нулевое значение")
    public void shouldHaveMonthWithZeroValue() {
        val cardData = getMonthWithZero();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.invalidCardExpirationDateError();
    }

    @Test
    @DisplayName("Поле месяц содержит значение больше чем 12")
    public void shouldHaveMonthMoreThan12() {
        val cardData = getMonthMore12();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.invalidCardExpirationDateError();
    }

    @Test
    @DisplayName("Поле месяц состоит из 1 цифры")
    public void shouldHaveMonthWithOneDigit() {
        val cardData = getMonthOneDigit();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле год не заполнено")
    public void shouldHaveEmptyYear() {
        val cardData = getEmptyYear();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.emptyFieldError();
    }

    @Test
    @DisplayName("Карта просрочена")
    public void shouldHaveYearBeforeCurrentYear() {
        val cardData = getExpiredCard();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.expiredDateError();
    }

    @Test
    @DisplayName("Поле год содержит нулевое значение")
    public void shouldHaveYearWithZero() {
        val cardData = getYearZero();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.expiredDateError();
    }

    @Test
    @DisplayName("Значение поля год превышает текущий год более чем на 6")
    public void shouldHaveYearInTheFarFuture() {
        val cardData = getExceedingYearBy6();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.invalidCardExpirationDateError();
    }

    @Test
    @DisplayName("Поле год состоит из 1 цифры")
    public void shouldHaveYearWithOneDigit() {
        val cardData = getYearhOneDigit();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле имя держателя не заполнено")
    public void shouldHaveEmptyHolder() {
        val cardData = getEmptyHolderName();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.emptyFieldError();
    }

    @Test
    @DisplayName("В поле имя держателя заполнена только фамилия")
    public void shouldHaveHolderWithoutName() {
        val cardData = getHolderWithoutName();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле имя держателя содержит кириллицу")
    public void shouldHaveRussianHolder() {
        val cardData = getHolderInCyrillic();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле имя держателя содержит цифры")
    public void shouldHaveDigitsInHolder() {
        val cardData = getDigitsHolderName();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле имя держателя содержит большое количество пробелов между именем и фамилией")
    public void shouldHaveHolderWithManySpaces() {
        val cardData = getHolderWithSpaces();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле CVC/CVV не заполнено")
    public void shouldHaveEmptyCvcCode() {
        val cardData = getEmptyCvcCode();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.emptyFieldError();
    }

    @Test
    @DisplayName("Поле CVC/CVV содержит 1 цифру")
    public void shouldHaveCvcCodeWithOneDigits() {
        val cardData = getCvcCodeOneDigits();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле CVC/CVV содержит 2 цифры")
    public void shouldHaveCvcCodeWithTwoDigits() {
        val cardData = getCvcCodeTwoDigits();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле имя держателя содержит символы")
    public void shouldHaveSpecialCharactersInHolder() {
        val cardData = getHolderNameFieldContainsCharacters();
        purchasePage.fillPurchaseForm(cardData);
        purchasePage.incorrectFormatError();
    }

}
