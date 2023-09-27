package ru.netology.web.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.�����;
import io.cucumber.java.ru.�����;
import io.cucumber.java.ru.�����;
import org.junit.jupiter.api.Assertions;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static ru.netology.web.data.DataHelper.getFirstCardInfo;
import static ru.netology.web.data.DataHelper.getSecondCardInfo;

public class MyStepdefs {

    @�����("������������ ��������� � ������ {string} � ������� {string}")
    public void ������������������������������������(String login, String password) {
        var loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        var verificationPage = loginPage.validLogin(login, password);
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @�����("������������ ��������� {int} ������ � ����� � ������� {string} �� ���� {int} ����� � ������� ��������")
    public void ��������������������������������������������������������������������(int amount, String fromCardNo, int toCard) {
        DashboardPage dashboardPage = new DashboardPage();
        DataHelper.CardInfo toCardInfo;
        if (toCard == 2) {
            toCardInfo = getSecondCardInfo();
        } else toCardInfo = getFirstCardInfo();

        var transferPage = dashboardPage.selectCardToTransfer(toCardInfo);
        transferPage.makeValidTransfer(String.valueOf(amount), fromCardNo);
    }

    @�����("������ ��� {} ����� �� ������ �� ������� �������� ������ ����� {} ������")
    public void ��������������������������������������������������������(int toCard, int expectedBalance) {
        DashboardPage dashboardPage = new DashboardPage();
        DataHelper.CardInfo toCardInfo;
        if (toCard == 2) {
            toCardInfo = getSecondCardInfo();
        } else toCardInfo = getFirstCardInfo();
        int actualCardBalance = dashboardPage.getCardBalance(toCardInfo.getCardTestId());

        Assertions.assertEquals(actualCardBalance, expectedBalance);
    }
}