package com.n11.test.steps;

import com.n11.test.base.BaseTest;
import com.thoughtworks.gauge.Step;
import org.apache.commons.io.FileUtils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StepImplementation extends BaseTest {

    @Step({"Go to <url> "})
    public void goToUrl(String url) {
        driver.get(url);
        logger.info(" going to " + url + " address ");
        assertEquals("https://www.n11.com/kampanyalar", url);
    }

    @Step({"Wait a <int> second"})
    public void waitBySeconds(int seconds) {
        try {
            logger.info(" waiting for " + seconds + " seconds ");
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Take a screenshot")
    public void takeScreenshot() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(".gauge/screenshots/screenshot.png"));
        logger.info(" screenshot is taken ");
    }

    @Step({"Click the links"})
    public void listTheLinks() throws Exception {
        List<WebElement> categories = driver.findElements(By.xpath("//div[@class='categoryMenuTab']//ul//li"));
        int count = 0;
        for (int x = 0; x < categories.size(); x++) {
            String name = categories.get(x).getText();
            categories.get(x).click();
            List<WebElement> links = driver.findElements(By.xpath("//section[contains(@class,'group')]" + "[" + (x + 1) + "]//a"));
            for (int y = 0; y < links.size(); y++) {
                System.out.println(name + " " + links.get(y).getAttribute("href"));
                count++;
                List<String> url = new ArrayList<String>();
                url.add(links.get(y).getAttribute("href"));
            }
        }
        logger.info(" clicking links ");
        System.out.println(count);
    }


    @Step("Export list to Excel")
    public void exportLinks () throws Exception {
        File file = new File("src/test/java/excel/excel.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sh = wb.createSheet();
        sh.createRow(0).createCell(0).setCellValue("Links");
        try {
            FileOutputStream fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        logger.info(" exporting to excel ");
    }
}


