package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PurchasePage {

    private final SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement month = $("[placeholder='08']");
    private final SelenideElement year = $("[placeholder='22']");
    private final SelenideElement holderName = $$(".input__control").get(3);
    private final SelenideElement cvvCode = $("[placeholder='999']");
    private final SelenideElement emptyField = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement incorrectMessageFormat = $(byText("Неверный формат"));
    private final SelenideElement invalidCardExpirationDate = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement expiredCardDate = $(byText("Истёк срок действия карты"));
    private final SelenideElement successTransaction = $(byText("Операция одобрена Банком."));
    private final SelenideElement failedTransaction = $(byText("Ошибка! Банк отказал в проведении операции."));
    private final SelenideElement continueButton = $$("button").find(exactText("Продолжить"));

    public void fillPurchaseForm(DataHelper.CardInfo cardInfo) {
        cardNumber.setValue(cardInfo.getNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        holderName.setValue(cardInfo.getHolder());
        cvvCode.setValue(cardInfo.getCvc());
        continueButton.click();
    }

    public void successTransactionNotification() {
        successTransaction.shouldHave(visible, Duration.ofSeconds(20));
    }

    public void failureTransactionNotification() {
        failedTransaction.shouldHave(visible, Duration.ofSeconds(20));
    }

    public void emptyFieldError() {
        emptyField.shouldBe(visible);
    }

    public void incorrectFormatError() {
        incorrectMessageFormat.shouldBe(visible);
    }

    public void invalidCardExpirationDateError() {
        invalidCardExpirationDate.shouldBe(visible);
    }

    public void expiredDateError() {
        expiredCardDate.shouldBe(visible);
    }

}
