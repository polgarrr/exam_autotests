package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverConditions;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.*;

public class CommonSteps {

    private final String HOME_PAGE_URL = "https://www.mtsbank.ru/";
    private final String LICENSE_PAGE_URL = "https://www.mtsbank.ru/o-banke/licensii/";
    private final String ENG_PAGE_URL = "https://www.mtsbank.ru/eng/";
    private final By FOOTER = By.xpath("//footer");
    private final String PERSONAL_DATA_DOCUMENT_TITLE = "ПОЛИТИКА \n \nВ ОТНОШЕНИИ ОБРАБОТКИ ПЕРСОНАЛЬНЫХ ДАННЫХ \n \n" +
            "Публичного акционерного общества \n«МТС-Банк» \n(ПАО «МТС-Банк») ";
    private final String THIRD_PARTIES_DOCUMENT_TITLE = "Перечень третьих лиц – партнеров Банка \n" +
            "имеющих право на обработку персональных данных заемщиков  ";


    @Step("Открыть домашнюю страницу")
    public CommonSteps openHomePage() {
        open(this.HOME_PAGE_URL);
        return this;
    }

    @Step("Найти футер на главной странице")
    public CommonSteps checkFooter() {
        $(FOOTER).shouldBe(Condition.visible, Duration.ofSeconds(30)).scrollIntoView(true);
        $(FOOTER).shouldHave(Condition.text("2022 ПАО «МТС-Банк»"));
        return this;
    }

    @Step("Проверка ссылки Телеграм")
    public CommonSteps correctTG() {
        open(this.HOME_PAGE_URL);
        String url = $x("//a[@aria-label='Telegram']").getAttribute("href");
        $x("//a[@aria-label='Telegram']").scrollTo().click();
        switchTo().window(1);
        webdriver().shouldHave(WebDriverConditions.url(url));
        closeWindow();
        return this;
    }

    @Step("Проверка ссылки ВК")
    public CommonSteps correctVK() {
        open(this.HOME_PAGE_URL);
        String url = $x("//a[@aria-label='Vk']").getAttribute("href");
        $x("//a[@aria-label='Vk']").scrollTo().click();
        switchTo().window(1);
        webdriver().shouldHave(WebDriverConditions.url(url));
        closeWindow();
        return this;
    }

    @Step("Проверка ссылки Одноклассники")
    public CommonSteps correctOdnoklassniki() {
        open(this.HOME_PAGE_URL);
        String url = $x("//a[@aria-label='Ok']").getAttribute("href");
        $x("//a[@aria-label='Ok']").scrollTo().click();
        switchTo().window(1);
        webdriver().shouldHave(WebDriverConditions.url(url));
        closeWindow();
        return this;
    }

    @Step("Проверка документа 'Политика в отношении обработки персональных данных ПАО «МТС-Банк' со страницы 'Персональные данные'")
    public CommonSteps correctPersonalDataDocumentTitle() throws FileNotFoundException {
        $("a[href='/o-banke/personalnye-dannye/']").scrollTo().click();
        File personalDataDoc = $x("(//a[@class='styled__StyledLink-iy9568-4 iUKFdW'])[1]")
                .shouldHave(Condition.text("Политика в отношении обработки персональных данных"))
                .download();
        String docContent = TestUtils.pdfFileParser(personalDataDoc, 1);
        Assertions.assertTrue(docContent.contains(PERSONAL_DATA_DOCUMENT_TITLE));
        return this;
    }

    @Step("Проверка документа 'Перечень третьих лиц - партнеров Банка' со страницы 'Персональные данные'")
    public CommonSteps correctThirdPartiesDocumentTitle() throws FileNotFoundException {
        $("a[href='/o-banke/personalnye-dannye/']").scrollTo().click();
        File thirdPartiesDoc = $x("(//a[@class='styled__StyledLink-iy9568-4 iUKFdW'])[2]")
                .shouldHave(Condition.text("Перечень третьих лиц"))
                .download();
        String docContent = TestUtils.pdfFileParser(thirdPartiesDoc, 1);
        Assertions.assertTrue(docContent.contains(THIRD_PARTIES_DOCUMENT_TITLE));
        return this;
    }

    @Step("Проверка лицензии банка на сайте ЦБ РФ cbr.ru")
    public CommonSteps licenseCBR() {
        open(LICENSE_PAGE_URL);
//        String text = $x()
        String text = $x("//div[@class='Wrapper-sc-1vydk7-0 Bwaux LevelOneText-sc-qwe0bz-1 jTdJVP']")
                .shouldBe(Condition.visible, Duration.ofSeconds(30))
                .getText();
        int licenseNumber = Integer.parseInt(text.substring(text.indexOf("№ ") + 2, text.indexOf(" от")));
        open("https://www.cbr.ru/banking_sector/credit/coinfo/?id=450000343");
        $x("//*[@id=\"content\"]/div/div/div/div[2]/div[1]/div[3]")
                .shouldBe(Condition.visible, Duration.ofSeconds(30));
        String centralBankText = $$x("(//div[@class='coinfo_item row'])[3]")
                .filter(Condition.textCaseSensitive("Регистрационный номер"))
                .first()
                .getText();
        int centralBankLicenseNumber = Integer.parseInt(centralBankText.substring(centralBankText.indexOf("номер\n") + 6));
        Assertions.assertEquals(licenseNumber, centralBankLicenseNumber);
        return this;
    }

    @Step("Проверка локализации страницы: английский язык -- colored block")
    public CommonSteps englishLanguageLocalizationPage() {
        open(ENG_PAGE_URL);
        String text = $x("//div[@class='ColoredBlock-sc-11qhib9-0 jyehLZ']").shouldBe(Condition.visible, Duration.ofSeconds(30)).getText();
        // поиск кириллических символов в основном блоке страницы на английском языке
        Pattern pattern = Pattern.compile("[А-Яа-я]");
        Matcher matcher = pattern.matcher(text);
        Assertions.assertFalse(matcher.find(), "На англоязычной версии страницы найдены кириллические символы");
        return this;
    }

    @Step("Проверка локализации страницы: английский язык -- 'terms of processing -> personal data'")
    public CommonSteps termsOfProcessingPersDataPage() {
        open(ENG_PAGE_URL);
        String personalData = $x("(//a[@class='LinkWrapper-sc-a7l7fm-0 eaxjcO'])[1]")
                .shouldBe(Condition.visible, Duration.ofSeconds(30))
                .getText();
        Pattern pattern = Pattern.compile("[А-Яа-я]");
        Matcher matcherPersD = pattern.matcher(personalData);
        Assertions.assertTrue(matcherPersD.find(), "На странице 'personal data' найдены кириллические символы");
        return this;
    }

    @Step("Проверка локализации страницы: английский язык -- 'terms of processing -> processing of website visitor's data'")
    public CommonSteps termsOfProcessingWebVisitorsData() {
        open(ENG_PAGE_URL);
        String processingWebVisitorData = $x("(//a[@class='LinkWrapper-sc-a7l7fm-0 eaxjcO'])[2]")
                .shouldBe(Condition.visible, Duration.ofSeconds(30))
                .getText();
        Pattern pattern = Pattern.compile("[А-Яа-я]");
        Matcher matcherPrWVD = pattern.matcher(processingWebVisitorData);
        Assertions.assertTrue(matcherPrWVD.find(), "На странице 'processing of website visitor's data' найдены кириллические символы");
        return this;
    }
}
