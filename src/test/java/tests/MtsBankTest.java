package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.CommonSteps;

import java.io.FileNotFoundException;

public class MtsBankTest extends BaseTest {

    private final CommonSteps steps = new CommonSteps();
    @Test
    @DisplayName("Наличие футера на странице")
    public void checkFooterOpened() {
        steps.openHomePage().checkFooter();
    }

    @Test
    @DisplayName("Проверка соцсетей")
    public void checkSocMedia() {
        steps.openHomePage().correctTG().correctVK().correctOdnoklassniki();
    }

    @Test
    @DisplayName("Проверка заголовка документа о персональных данных (pdf)")
    public void checkPersonalDataDocumentTitle() throws FileNotFoundException {
        steps.openHomePage().correctPersonalDataDocumentTitle();
    }

    @Test
    @DisplayName("Проверка заголовка pdf документа о третьих лицах - партнерах банка")
    public void checkThirdPartiesDoc() throws FileNotFoundException {
        steps.openHomePage().correctThirdPartiesDocumentTitle();
    }

    @Test
    @DisplayName("Проверка актуальности номера банковской лицензии")
    public void checkBankLicenseNumber() {
        steps.licenseCBR();
    }

    @Test
    @DisplayName("Проверка англоязычной локализации страницы (отсутствие символов на русском)")
    public void checkEnglishLocalizationColoredBlock() {
        steps.englishLanguageLocalizationPage();
    }

    @Test
    @DisplayName("Проверка англоязычной локализации страниц согласия на обработку персданных при подписке на новости (отсутствие символов на русском)")
    public void checkEnglishLocalizationTermsOfProcessingPD() {
        steps.termsOfProcessingPersDataPage();
    }

    @Test
    @DisplayName("Проверка англоязычной локализации страниц согласия на обработку и получение данных посетителя при подписке на новости (отсутствие символов на русском)")
    public void checkEnglishLocalizationTermsOfProcessingWVD() {
        steps.termsOfProcessingWebVisitorsData();
    }

}
