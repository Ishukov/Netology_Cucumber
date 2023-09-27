package ru.netology.web.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru. огда;
import io.cucumber.java.ru.ѕусть;
import io.cucumber.java.ru.“огда;
import org.junit.jupiter.api.Assertions;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static ru.netology.web.data.DataHelper.getFirstCardInfo;
import static ru.netology.web.data.DataHelper.getSecondCardInfo;

public class MyStepdefs {

    @ѕусть("пользователь залогинен с именем {string} и паролем {string}")
    public void пользователь«алогинен—»менем»ѕаролем(String login, String password) {
        var loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        var verificationPage = loginPage.validLogin(login, password);
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @ огда("пользователь переводит {int} рублей с карты с номером {string} на свою {int} карту с главной страницы")
    public void пользовательѕереводит–ублей— арты—ЌомеромЌа—вою арту—√лавной—траницы(int amount, String fromCardNo, int toCard) {
        DashboardPage dashboardPage = new DashboardPage();
        DataHelper.CardInfo toCardInfo;
        if (toCard == 2) {
            toCardInfo = getSecondCardInfo();
        } else toCardInfo = getFirstCardInfo();

        var transferPage = dashboardPage.selectCardToTransfer(toCardInfo);
        transferPage.makeValidTransfer(String.valueOf(amount), fromCardNo);
    }

    @“огда("баланс его {} карты из списка на главной странице должен стать {} рублей")
    public void баланс≈го арты»з—пискаЌа√лавной—траницеƒолжен—тать–ублей(int toCard, int expectedBalance) {
        DashboardPage dashboardPage = new DashboardPage();
        DataHelper.CardInfo toCardInfo;
        if (toCard == 2) {
            toCardInfo = getSecondCardInfo();
        } else toCardInfo = getFirstCardInfo();
        int actualCardBalance = dashboardPage.getCardBalance(toCardInfo.getCardTestId());

        Assertions.assertEquals(actualCardBalance, expectedBalance);
    }
}