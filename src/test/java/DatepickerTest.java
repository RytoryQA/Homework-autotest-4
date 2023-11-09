import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DatepickerTest {

    Locale locale = new Locale("ru");

    private String generateDate(int addDays, String pattern, Locale locale) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void test() {
        open("http://localhost:9999/");

        $("[data-test-id=city] input").setValue("Мо");
        $(".menu").shouldBe(visible);
        $$(".menu-item__control").findBy(text("Москва")).click();
        String meetingDate = generateDate(23, "dd.MM.yyyy", locale);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(meetingDate);
        String currentMonth = String.format(".popup .calendar__name");
        if(meetingDate.contains(currentMonth)) {
            return;
        } else {
            $x("//span[@data-test-id='date']").click();
            $x("//div[@class='calendar__arrow calendar__arrow_direction_right']").click();
        }

        $("[data-test-id=name] input").setValue("Вик-Викторовна Иванова");
        $("[data-test-id=phone] input").setValue("+99912345678");
        $("[data-test-id=agreement] .checkbox__box").click();
        $("button.button").click();
        $("[data-test-id=notification] .notification__title")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно!"));
        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + meetingDate));

    }

}
