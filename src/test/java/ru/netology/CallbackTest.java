package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CallbackTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void openApplication() {
        open("http://localhost:9999/");
    }

    @Test
    void formSubmissionSuccess() {

        $("[data-test-id=city] input").setValue("Самара");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Вик-Викторовна Иванова");
        $("[data-test-id=phone] input").setValue("+99912345678");
        $("[data-test-id=agreement] .checkbox__box").click();
        $("button.button").click();
        $("[data-test-id=notification] .notification__title")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно!"));
        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + currentDate));

    }

    @Test
    void testDropdownListCity() {

        $("[data-test-id=city] input").setValue("Мо");
        $(".menu").shouldBe(visible);
        $$(".menu-item__control").findBy(text("Москва")).click();
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Петр Петров");
        $("[data-test-id=phone] input").setValue("+99912345679");
        $("[data-test-id=agreement] .checkbox__box").click();
        $("button.button").click();
        $("[data-test-id=notification] .notification__title")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно!"));
        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + currentDate));
    }

}
