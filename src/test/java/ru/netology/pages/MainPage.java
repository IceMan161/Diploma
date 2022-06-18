package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    private static final SelenideElement payDebitCardButton = $$("button").find(exactText("Купить"));
    private static final SelenideElement payCreditButton = $$("button").find(exactText("Купить в кредит"));

    public void payDebitCard() {
        payDebitCardButton.click();
    }

    public void payCredit() {
        payCreditButton.click();
    }

}
