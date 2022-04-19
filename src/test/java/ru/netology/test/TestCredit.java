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
import static ru.netology.data.DataHelper.getEmptyCvc;
import static ru.netology.data.DbHelper.*;
import static ru.netology.data.DbHelper.checkEmptyOrderEntity;

public class TestCredit {

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
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCredit.getSuccessMessage();
        assertEquals(approvedCard.getStatus(), creditData().getStatus());
    }

    @Test
    void shouldBuyByDeclineCard() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getDeclainedCardInfo(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCredit.getErrorMessage();
        assertEquals(declinedCard.getStatus(), creditData().getStatus());
        checkEmptyOrderEntity();
    }


    @Test
//"Покупка тура с невалидным номером карты"
    void shouldSendFormWithInvalidCardNumber() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getInvalidCardNumber(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCredit.formatError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
        //"Отправка формы с пустым полем Номер карты"
    void shouldSendFormWithoutCardNumber() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getEmptyCardNumber(), getValidMonth(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCredit.formatError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным месяцем (однозначное числовое значение)"
    void shouldSendFormWithInvalidMonth1() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getInvalidMonth1(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCredit.invalidError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным месяцем (неверно указан срок действия карты)"
    void shouldSendFormWithInvalidMonth2() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getInvalidMonth2(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCredit.invalidError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с пустым месяцем"
    void shouldSendFormWithoutMonth() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getEmptyMonth(), getValidYear(), getValidOwner(), getValidCvc());
        buyByCredit.formatError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным годом (однозначное числовое значение)"
    void shouldSendFormWithInvalidYearCard1() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getValidMonth(), getInvalidYear(), getValidOwner(), getValidCvc());
        buyByCredit.formatError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным годом (неверно указан срок действия карты)"
    void shouldSendFormWithInvalidYearCard2() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getValidMonth(), getInvalidLastYear(), getValidOwner(), getValidCvc());
        buyByCredit.formatError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }


    @Test
//"Отправка формы с пустым полем Год"
    void shouldSendFormWithoutYear() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getValidMonth(), getEmptyYear(), getValidOwner(), getValidCvc());
        buyByCredit.formatError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Ввод нулевого значения в поле Год"
    void shouldSendFormWithNullYear() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getValidMonth(), getNullYear(), getValidOwner(), getValidCvc());
        buyByCredit.expiredError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным данными владельца (значение набрано кириллицей)"
    void shouldSendFormWithInvalidOwnerCyrillic() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getInvalidOwnerCyrillic(), getValidCvc());
        buyByCredit.formatError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с введеными в поле Владелец цифровых значений и математических символов"
    void shouldSendFormWithInvalidOwnerMaths() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getInvalidOwnerMaths(), getValidCvc());
        buyByCredit.formatError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с введеными в поле Владелец буквенных значений в нижнем и верхнем регистре"
    void shouldSendFormWithInvalidOwnerRegister() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getInvalidOwnerRegister(), getValidCvc());
        buyByCredit.formatError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с введеным в поле Владелец одной буквы (минимальная длина)"
    void shouldSendFormWithInvalidOwnerMinLength() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getInvalidOwnerMinLength(), getValidCvc());
        buyByCredit.formatError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с введеными в поле Владелец 270 буквенных значений (максимальная длина поля)"
    void shouldSendFormWithInvalidOwnerMaxLength() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getInvalidOwnerMaxLength(), getValidCvc());
        buyByCredit.formatError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }


    @Test
//"Отправка формы с пустым полем Владелец"
    void shouldSendFormWithInvalidOwnerEmpty() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getEmptyOwner(), getValidCvc());
        buyByCredit.emptyError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным CVC/CCV (однозначное числовое значение)"
    void shouldSendFormWithInvalidCvc() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getValidOwner(), getInvalidCvc());
        buyByCredit.formatError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным CVC/CCV (проверка на 000)"
    void shouldSendFormWithNullCvc() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getValidOwner(), getNullCvc());
        buyByCredit.formatError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
//"Отправка формы с невалидным CVC/CCV (пустое поле)"
    void shouldSendFormWitheEmptyCvc() {
        HomePage homePage = new HomePage();
        BuyByCredit buyByCredit = homePage.getPageCredit();
        buyByCredit.enterCardData(getApprovedCardInfo(), getValidMonth(), getValidYear(), getValidOwner(), getEmptyCvc());
        buyByCredit.formatError();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }


}
