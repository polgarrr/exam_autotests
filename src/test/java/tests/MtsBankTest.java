package tests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import steps.CommonSteps;

import java.io.FileNotFoundException;

public class MtsBankTest extends BaseTest {

    @Test
    @DisplayName("Наличие футера на странице")
    public void checkingFooterOpened() {
        CommonSteps footer = new CommonSteps();
        footer
                .openHomePage()
                .checkFooter();
    }

    @Test
    @DisplayName("Проверка соцсетей")
    public void checkingSocMedia() {
        CommonSteps socMedia = new CommonSteps();
        socMedia
                .openHomePage()
                .correctTG()
                .correctVK()
                .correctOdnoklassniki();
    }

    @Test
    @DisplayName("Проверка названия документа о персональных данных (pdf)")
    public void checkPersonalDataDocumentTitle() throws FileNotFoundException {
        CommonSteps checkPersDataDoc = new CommonSteps();
        checkPersDataDoc
                .openHomePage()
                .correctPersonalDataDocumentTitle();
    }

    @Test
    @DisplayName("Проверка заголовка pdf документа о третьих лицах - партнерах банка")
    public void checkingThirdPartiesDoc() throws FileNotFoundException {
        CommonSteps checkPThirdPartiesDoc = new CommonSteps();
        checkPThirdPartiesDoc
                .openHomePage()
                .correctThirdPartiesDocumentTitle();
    }

    @Test
    @DisplayName("Проверка актуальности номера банковской лицензии")
    public void checkBankLicenseNumber() {
        CommonSteps steps = new CommonSteps();
        steps.licenseCBR();
    }

    @Test
    @DisplayName("Проверка англоязычной локализации страницы (отсутствие символов на русском)")
    public void checkEnglishLocalizationColoredBlock() {
        CommonSteps localization = new CommonSteps();
        localization.englishLanguageLocalizationPage();
    }

    @Test
    @DisplayName("Проверка англоязычной локализации страниц согласия на обработку персданных при подписке на новости (отсутствие символов на русском)")
    public void checkEnglishLocalizationTermsOfProcessingPD() {
        CommonSteps localization = new CommonSteps();
        localization.termsOfProcessingPersDataPage();
    }

    @Test
    @DisplayName("Проверка англоязычной локализации страниц согласия на обработку и получение данных посетителя при подписке на новости (отсутствие символов на русском)")
    public void checkEnglishLocalizationTermsOfProcessingWVD() {
        CommonSteps localization = new CommonSteps();
        localization.termsOfProcessingWebVisitorsData();
    }

}
