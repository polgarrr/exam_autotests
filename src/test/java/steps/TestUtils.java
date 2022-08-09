package steps;

import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class TestUtils {

    public static String pdfFileParser(File fileForParse, int page) {
        String result = "";
        PdfReader reader;
        try {
            reader = new PdfReader(Files.toByteArray(fileForParse));
            result = PdfTextExtractor.getTextFromPage(reader, page);
            reader.close();
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        log.info("Parsing .pdf file \"{}\" results:\n{}", fileForParse, result);

        return result;
    }
}
