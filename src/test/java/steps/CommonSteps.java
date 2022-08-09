package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverConditions;
import io.qameta.allure.Step;
import org.junit.Assert;
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
    private final By FOOTER = By.cssSelector("[class*='styled__Footer-sc-9z154d-0']");
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
        $x("//*[@id=\"__next\"]/div[1]/footer/div/div[6]/div/ul/li[6]/a").scrollTo().click();
        File personalDataDoc = $x("//*[@id=\"__next\"]/div[3]/div/div/div[6]/div[1]/a")
                .shouldHave(Condition.text("Политика в отношении обработки персональных данных"))
                .download();
        String docContent = TestUtils.pdfFileParser(personalDataDoc, 1);
        Assert.assertTrue(docContent.contains(PERSONAL_DATA_DOCUMENT_TITLE));
        return this;
    }

    @Step("Проверка документа 'Перечень третьих лиц - партнеров Банка' со страницы 'Персональные данные'")
    public CommonSteps correctThirdPartiesDocumentTitle() throws FileNotFoundException {
        $x("//*[@id=\"__next\"]/div[1]/footer/div/div[6]/div/ul/li[6]/a").scrollTo().click();
        File thirdPartiesDoc = $x("//*[@id=\"__next\"]/div[3]/div/div/div[6]/div[2]/a")
                .shouldHave(Condition.text("Перечень третьих лиц"))
                .download();
        String docContent = TestUtils.pdfFileParser(thirdPartiesDoc, 1);
        Assert.assertTrue(docContent.contains(THIRD_PARTIES_DOCUMENT_TITLE));
        return this;
    }

    @Step("Проверка лицензии банка на сайте ЦБ РФ cbr.ru")
    public CommonSteps licenseCBR() {
        open(LICENSE_PAGE_URL);
        String text = $x("//*[@id=\"__next\"]/div[4]/div/div/div[5]/div[1]/ul/div[1]/div[2]/div")
                .shouldBe(Condition.visible, Duration.ofSeconds(30))
                .getText();
        int licenseNumber = Integer.parseInt(text.substring(text.indexOf("№ ") + 2, text.indexOf(" от")));
        open("https://www.cbr.ru/banking_sector/credit/coinfo/?id=450000343");
        $x("//*[@id=\"content\"]/div/div/div/div[2]/div[1]/div[3]")
                .shouldBe(Condition.visible, Duration.ofSeconds(30));
        String centralBankText = $$x("//*[@id=\"content\"]/div/div/div/div[2]/div[1]/div")
                .filter(Condition.textCaseSensitive("Регистрационный номер"))
                .first()
                .getText();
        int centralBankLicenseNumber = Integer.parseInt(centralBankText.substring(centralBankText.indexOf("номер\n") + 6));
        Assert.assertEquals(licenseNumber, centralBankLicenseNumber);
        return this;
    }

    @Step("Проверка локализации страницы: английский язык -- colored block")
    public CommonSteps englishLanguageLocalizationPage() {
        open(ENG_PAGE_URL);
        String text = $x("//*[@id=\"__next\"]/div[4]").shouldBe(Condition.visible, Duration.ofSeconds(30)).getText();
        // поиск кириллических символов в основном блоке страницы на английском языке
        Pattern pattern = Pattern.compile("[А-Яа-я]");
        Matcher matcher = pattern.matcher(text);
        Assert.assertFalse("На англоязычной версии страницы найдены кириллические символы", matcher.find());
        return this;
    }

    @Step("Проверка локализации страницы: английский язык -- 'terms of processing -> personal data'")
    public CommonSteps termsOfProcessingPersDataPage() {
        open(ENG_PAGE_URL);
        String personalData = $x("//*[@id=\"subscribeEngFormId\"]/form/div[2]/label/div[2]/div/div/a[1]")
                .shouldBe(Condition.visible, Duration.ofSeconds(30))
                .getText();
        Pattern pattern = Pattern.compile("[А-Яа-я]");
        Matcher matcherPersD = pattern.matcher(personalData);
        Assert.assertTrue("На странице 'personal data' найдены кириллические символы", matcherPersD.find());
        return this;
    }

    @Step("Проверка локализации страницы: английский язык -- 'terms of processing -> processing of website visitor's data'")
    public CommonSteps termsOfProcessingWebVisitorsData() {
        open(ENG_PAGE_URL);
        String processingWebVisitorData = $x("//*[@id=\"subscribeEngFormId\"]/form/div[2]/label/div[2]/div/div/a[2]")
                .shouldBe(Condition.visible, Duration.ofSeconds(30))
                .getText();
        Pattern pattern = Pattern.compile("[А-Яа-я]");
        Matcher matcherPrWVD = pattern.matcher(processingWebVisitorData);
        Assert.assertTrue("На странице 'processing of website visitor's data' найдены кириллические символы", matcherPrWVD.find());
        return this;
    }
}