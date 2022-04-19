package ru.netology.test;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.DbHelper;
import ru.netology.page.BuyByCard;
import ru.netology.page.BuyByCredit;
import ru.netology.page.HomePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.DbHelper.*;


public class TestCard {

    DataHelper.CardNumber approvedCard = DataHelper.approvedCardInfo();
    DataHelper.CardNumber declinedCard = DataHelper.declinedCardInfo();

    @BeforeEach
    public void cleanTables() {
        DbHelper.cleanData();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setupClass() {
        open("http://localhost:8080");
    }

    @Test
    void shouldSwitchBetweenPages() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        BuyByCard buyByCard = buyByCredit.buyByCard();
        buyByCard.buyByCreditCard();
    }

    @Test
    void shouldBuyByApprovedCard() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCard.getSuccessMessage();
        assertEquals(approvedCard.getStatus(), payData().getStatus());
    }

    @Test
    void shouldBuyByDeclineCard() {
        var homePage = new HomePage();
        var buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getDeclainedCardInfo(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCard.getErrorMessage();
        assertEquals(declinedCard.getStatus(), payData().getStatus());
        checkEmptyOrderEntity();
    }


    @Test
//"Покупка тура с невалидным номером карты"
    void shouldSendFormWithInvalidCardNumber() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getInvalidCardNumber(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCard.formatError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
        //"Отправка формы с пустым полем Номер карты"
    void shouldSendFormWithoutCardNumber() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getEmptyCardNumber(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCard.formatError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным месяцем (однозначное числовое значение)"
    void shouldSendFormWithInvalidMonth1() {
        HomePage homePage = new HomePage();
        var buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getInvalidMonth1(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCard.invalidError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным месяцем (неверно указан срок действия карты)"
    void shouldSendFormWithInvalidMonth2() {
        HomePage homePage = new HomePage();
        var buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getInvalidMonth2(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCard.invalidError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с пустым месяцем"
    void shouldSendFormWithoutMonth() {
        HomePage homePage = new HomePage();
        var buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getEmptyMonth(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCard.formatError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным годом (однозначное числовое значение)"
    void shouldSendFormWithInvalidYearCard1() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getValidMonth(), getInvalidYear(), getValidOwner(), getValidCvc());
        buyByCard.formatError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным годом (неверно указан срок действия карты)"
    void shouldSendFormWithInvalidYearCard2() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getValidMonth(), getInvalidLastYear(), getValidOwner(), getValidCvc());
        buyByCard.formatError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с пустым полем Год"
    void shouldSendFormWithoutYear() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getValidMonth(), getEmptyYear(), getValidOwner(), getValidCvc());
        buyByCard.formatError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Ввод нулевого значения в поле Год"
    void shouldSendFormWithNullYear() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getValidMonth(), getNullYear(), getValidOwner(), getValidCvc());
        buyByCard.expiredError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным данными владельца (значение набрано кириллицей)"
    void shouldSendFormWithInvalidOwnerCyrillic() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getInvalidOwnerCyrillic(), getValidCvc());
        buyByCard.formatError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с введеными в поле Владелец цифровых значений и математических символов"
    void shouldSendFormWithInvalidOwnerMaths() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getInvalidOwnerMaths(), getValidCvc());
        buyByCard.formatError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с введеными в поле Владелец буквенных значений в нижнем и верхнем регистре"
    void shouldSendFormWithInvalidOwnerRegister() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getInvalidOwnerRegister(), getValidCvc());
        buyByCard.formatError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с введеным в поле Владелец одной буквы (минимальная длина)"
    void shouldSendFormWithInvalidOwnerMinLength() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getInvalidOwnerMinLength(), getValidCvc());
        buyByCard.formatError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с введеными в поле Владелец 270 буквенных значений (максимальная длина поля)"
    void shouldSendFormWithInvalidOwnerMaxLength() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getInvalidOwnerMaxLength(), getValidCvc());
        buyByCard.formatError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с пустым полем Владелец"
    void shouldSendFormWithInvalidOwnerEmpty() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getEmptyOwner(), getValidCvc());
        buyByCard.emptyError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным CVC/CCV (однозначное числовое значение)"
    void shouldSendFormWithInvalidCvc() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getValidOwner(), getInvalidCvc());
        buyByCard.formatError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным CVC/CCV (проверка на 000)"
    void shouldSendFormWithNullCvc() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getValidOwner(), getNullCvc());
        buyByCard.formatError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным CVC/CCV (пустое поле)"
    void shouldSendFormWitheEmptyCvc() {
        HomePage homePage = new HomePage();
        BuyByCard buyByCard = homePage.getPageByCard();
        buyByCard.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getValidOwner(), getEmptyCvc());
        buyByCard.formatError();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

}