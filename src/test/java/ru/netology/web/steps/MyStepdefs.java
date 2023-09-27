package ru.netology.web.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.VerificationPage;

import static ru.netology.web.data.DataHelper.getFirstCardInfo;
import static ru.netology.web.data.DataHelper.getSecondCardInfo;

public class MyStepdefs {

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void пользовательЗалогиненСИменемИПаролем(String login, String password) {
        var loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        var verificationPage = loginPage.validLogin(login, password);
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Когда("пользователь переводит {int} рублей с карты с номером {string} на свою {int} карту с главной страницы")
    public void пользовательПереводитРублейСКартыСНомеромНаСвоюКартуСГлавнойСтраницы(int amount, String fromCardNo, int toCard) {
        DashboardPage dashboardPage = new DashboardPage();
        DataHelper.CardInfo toCardInfo;
        if (toCard == 2) {
            toCardInfo = getSecondCardInfo();
        } else toCardInfo = getFirstCardInfo();

        var transferPage = dashboardPage.selectCardToTransfer(toCardInfo);
        transferPage.makeValidTransfer(String.valueOf(amount), fromCardNo);
    }

    @Тогда("баланс его {} карты из списка на главной странице должен стать {} рублей")
    public void балансЕгоКартыИзСпискаНаГлавнойСтраницеДолженСтатьРублей(int toCard, int expectedBalance) {
        DashboardPage dashboardPage = new DashboardPage();
        DataHelper.CardInfo toCardInfo;
        if (toCard == 2) {
            toCardInfo = getSecondCardInfo();
        } else toCardInfo = getFirstCardInfo();
        int actualCardBalance = dashboardPage.getCardBalance(toCardInfo.getCardTestId());

        Assertions.assertEquals(actualCardBalance, expectedBalance);
    }
}