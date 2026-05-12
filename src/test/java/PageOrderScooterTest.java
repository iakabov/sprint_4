import model.PageOrderScooterAboutRent;
import model.PageOrderScooterForWhomScooter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class PageOrderScooterTest {
    private static final String ORDER_PAGE_URL = "https://qa-scooter.praktikum-services.ru/order";
    private static final String BROWSER_NAME = "Firefox"; //задаем браузер Chrome или Firefox

    private WebDriver driver;

    // поля класса
    private final String name;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final String colorScooter;
    private final String comment;

    // конструктор класса
    public PageOrderScooterTest(String name, String lastName, String address, String metroStation, String phone,
                                String date, String rentalPeriod, String colorScooter, String comment) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.colorScooter = colorScooter;
        this.comment = comment;
    }

    // тестовые данные
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Александр", "Иванов", "город Москва", "ВДНХ", "+79101112233", "01.01.2025", "сутки", "black", "Мой комментарий"},
                {"Сергей", "Петров", "улица Ленина", "ЗИЛ", "+79205556677", "31.12.2024", "трое суток", "grey", "Без комментариев"},
        };
    }

    @Before
    public void before() {
        driver = SelectBrowser.selectDriverBrowser(BROWSER_NAME);
    }

    @Test
    public void testMakingOrder() {
        driver.get(ORDER_PAGE_URL);
        PageOrderScooterForWhomScooter pageOrderScooterForWhomScooter = new PageOrderScooterForWhomScooter(driver);
        pageOrderScooterForWhomScooter.closeCokie(); // закрываем куки
        pageOrderScooterForWhomScooter.testFieldName(name);
        pageOrderScooterForWhomScooter.testFieldLastName(lastName);
        pageOrderScooterForWhomScooter.testFieldAddress(address);
        pageOrderScooterForWhomScooter.testFieldMetroStation(metroStation);
        pageOrderScooterForWhomScooter.testFieldPhone(phone);
        pageOrderScooterForWhomScooter.pushButtonNext();

        PageOrderScooterAboutRent pageOrderScooterAboutRent = new PageOrderScooterAboutRent(driver);
        pageOrderScooterAboutRent.testPageAboutRentIsDisplayed();
        pageOrderScooterAboutRent.testFieldDeliveryDate(date);
        pageOrderScooterAboutRent.testFieldRentalPeriod(rentalPeriod);
        pageOrderScooterAboutRent.testFieldColorScooter(colorScooter);
        pageOrderScooterAboutRent.testComment(comment);
        pageOrderScooterAboutRent.testButtonOrder();
        pageOrderScooterAboutRent.testByttonYesOrder();

        // проверяем что заказ оформлен
        assertTrue(pageOrderScooterAboutRent.testMessageOrderOk().contains("Заказ оформлен"));

        // driver.quit(); // Удалено, закрытие в after()
    }

    @After
    public void after() {
        if (driver != null) {
            driver.quit();
        }
    }
}