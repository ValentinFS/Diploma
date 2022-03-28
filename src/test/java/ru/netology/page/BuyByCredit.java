package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuyByCredit {

    private SelenideElement heading = $$(".heading").findBy(Condition.text("Кредит по данным карты"));
    private SelenideElement cardNumberCard = $(".input__control[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthCard = $(".input__control[placeholder='08']");
    private SelenideElement yearCard = $(".input__control[placeholder='22']");
    private SelenideElement ownerCard = $(By.xpath("//span[text()='Владелец']/..//input"));
    private SelenideElement cvcCard = $(".input__control[placeholder='999']");

    private SelenideElement buttonBuyCard = $$("button").findBy(Condition.text("Купить"));
    private SelenideElement buttonBuyByCreditCard = $$("button").findBy(Condition.text("Купить в кредит"));
    private SelenideElement buttonContinue = $$("button").findBy(Condition.text("Продолжить"));


    private ElementsCollection successMessage = $$(".notification__title");
    private SelenideElement errorMessage = $$(".notification__title").findBy(Condition.text("Ошибка"));


    public BuyByCredit() {
        heading.shouldBe(Condition.visible);
    }

    public BuyByCard buyByCard() {
        buttonBuyCard.click();
        $$(".heading").findBy(Condition.text("Оплата по карте")).shouldBe(Condition.visible);
        return new BuyByCard();
    }

    public void enterCardData(String cardNumber, String month, String year, String owner, String cvc) {
        cardNumberCard.setValue(cardNumber);
        monthCard.setValue(month);
        yearCard.setValue(year);
        ownerCard.setValue(owner);
        cvcCard.setValue(cvc);
        buttonContinue.click();
    }

    public void getSuccessMessage() {
        successMessage.get(0).shouldBe(Condition.visible, Duration.ofSeconds(15));
        successMessage.get(0).shouldHave(Condition.exactText("Успешно"));
    }

    public void getErrorMessage() {
        successMessage.get(1).shouldBe(Condition.visible, Duration.ofSeconds(15));
        successMessage.get(1).shouldHave(Condition.exactText("Ошибка! Банк отказал в проведении операции."));
    }

    public void errorCardNumber() {
        errorMessage.shouldHave(Condition.text("Неверный формат"));
    }

    public void errorMonth() {
        errorMessage.shouldHave(Condition.text("Неверный формат"));
    }

    public void errorYear() {
        errorMessage.shouldHave(Condition.text("Неверный формат"));
    }

    public void errorOwner() {
        errorMessage.shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    public void errorCvc() {
        errorMessage.shouldHave(Condition.text("Неверный формат"));
    }

}
