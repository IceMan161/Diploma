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
        val cardInfo = getApprovedCardNumber();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.successTransactionNotification();
        val paymentStatus = SqlHelper.getStatusDebitPaymentEntity();
        assertEquals("APPROVED", paymentStatus);
    }

    @Test
    @DisplayName("Неудавшаяся операция покупке тура по карте со статусом DECLINED")
    public void shouldFailurePayIfValidDeclinedCard() {
        val cardInfo = getDeclinedCardNumber();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.failureTransactionNotification();
        val paymentStatus = SqlHelper.getStatusDebitPaymentEntity();
        assertEquals("DECLINED", paymentStatus);
    }

    @Test
    @DisplayName("Поле номера карты не заполнено")
    public void shouldHaveEmptyCardNumber() {
        val cardInfo = getEmptyNumber();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.emptyFieldError();
    }

    @Test
    @DisplayName("Количество цифр в номере карты меньше 16")
    public void shouldHaveNumberIfFewDigits() {
        val cardInfo = getCardNumberLess16Digits();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Несуществующий номер карты")
    public void shouldHaveNotExistentCardNumber() {
        val cardInfo = getNumberIfNotExistInBase();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.failureTransactionNotification();
    }

    @Test
    @DisplayName("Различные типы карт")
    public void shouldHaveNumberIfDifferentTypeOfCard() {
        val cardInfo = getNumberFaker();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.failureTransactionNotification();
    }

    @Test
    @DisplayName("Поле месяц не заполнено")
    public void shouldHaveEmptyMonth() {
        val cardInfo = getEmptyMonth();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.emptyFieldError();
    }

    @Test
    @DisplayName("Поле месяц содержит нулевое значение")
    public void shouldHaveMonthWithZeroValue() {
        val cardInfo = getMonthWithZero();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.invalidCardExpirationDateError();
    }

    @Test
    @DisplayName("Поле месяц содержит значение больше чем 12")
    public void shouldHaveMonthMoreThan12() {
        val cardInfo = getMonthMore12();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.invalidCardExpirationDateError();
    }

    @Test
    @DisplayName("Поле месяц состоит из 1 цифры")
    public void shouldHaveMonthWithOneDigit() {
        val cardInfo = getMonthOneDigit();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле год не заполнено")
    public void shouldHaveEmptyYear() {
        val cardInfo = getEmptyYear();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.emptyFieldError();
    }

    @Test
    @DisplayName("Карта просрочена")
    public void shouldHaveYearBeforeCurrentYear() {
        val cardInfo = getExpiredCard();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.expiredDateError();
    }

    @Test
    @DisplayName("Поле год содержит нулевое значение")
    public void shouldHaveYearWithZero() {
        val cardInfo = getYearZero();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.expiredDateError();
    }

    @Test
    @DisplayName("Значение поля год превышает текущий год более чем на 6")
    public void shouldHaveYearInTheFarFuture() {
        val cardInfo = getExceedingYearBy6();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.invalidCardExpirationDateError();
    }

    @Test
    @DisplayName("Поле год состоит из 1 цифры")
    public void shouldHaveYearWithOneDigit() {
        val cardInfo = getYearhOneDigit();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле имя держателя не заполнено")
    public void shouldHaveEmptyHolder() {
        val cardInfo = getEmptyHolderName();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.emptyFieldError();
    }

    @Test
    @DisplayName("В поле имя держателя заполнена только фамилия")
    public void shouldHaveHolderWithoutName() {
        val cardInfo = getHolderWithoutName();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле имя держателя содержит кириллицу")
    public void shouldHaveRussianHolder() {
        val cardInfo = getHolderInCyrillic();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле имя держателя содержит цифры")
    public void shouldHaveDigitsInHolder() {
        val cardInfo = getDigitsHolderName();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле имя держателя содержит большое количество пробелов между именем и фамилией")
    public void shouldHaveHolderWithManySpaces() {
        val cardInfo = getHolderWithSpaces();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле CVC/CVV не заполнено")
    public void shouldHaveEmptyCvcCode() {
        val cardInfo = getEmptyCvcCode();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.emptyFieldError();
    }

    @Test
    @DisplayName("Поле CVC/CVV содержит 1 цифру")
    public void shouldHaveCvcCodeWithOneDigits() {
        val cardInfo = getCvcCodeOneDigits();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле CVC/CVV содержит 2 цифры")
    public void shouldHaveCvcCodeWithTwoDigits() {
        val cardInfo = getCvcCodeTwoDigits();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.incorrectFormatError();
    }

    @Test
    @DisplayName("Поле имя держателя содержит символы")
    public void shouldHaveSpecialCharactersInHolder() {
        val cardInfo = getHolderNameFieldContainsCharacters();
        purchasePage.fillPurchaseForm(cardInfo);
        purchasePage.incorrectFormatError();
    }

}
