package re.netologi;

import com.codeborne.selenide.Condition;
import jdk.jfr.Registered;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import static re.netologi.DataGenerator.Registration.getRegisteredUser;
import static re.netologi.DataGenerator.Registration.getUser;
import static re.netologi.DataGenerator.getRandomLogin;
import static re.netologi.DataGenerator.getRandomPassword;

public class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }
    @Test

    void shouldRegisteredActionUser(){
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(Condition.text("  Личный кабинет")).should(Condition.visible);
    }
    @Test
    void shouldRegisteredNoActionUser(){
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .should(Condition.visible);
    }
    @Test
    void shouldRegisteredBlockedActionUser(){
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"))
                .should(Condition.visible);

    }
    @Test
    void shouldRegisteredNoNameUser(){
        var registeredUser = getRegisteredUser("active");
        var login = getRandomLogin();
        $("[data-test-id='login'] input").setValue(login);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .should(Condition.visible);
    }
    @Test
    void shouldRegisteredNoPasswordUser(){
        var registeredUser = getRegisteredUser("active");
        var password = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(password);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .should(Condition.visible);
    }
    @Test
    void shouldRegisteredNotNameUser(){
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='login'] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"))
                .should(Condition.visible);
    }
    @Test
    void shouldRegisteredNotPasswordUser(){
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='password'] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"))
                .should(Condition.visible);
    }



}
