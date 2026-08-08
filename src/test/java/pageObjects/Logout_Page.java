package pageObjects;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Logout_Page {

    private WebDriver driver;
    private WebDriverWait wait;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public Logout_Page(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }

    // =========================================================
    // LOCATORS
    // =========================================================

    // Mobile 3-line menu
    private final By mobileMenuButton =
            By.cssSelector("svg[data-testid='MenuIcon']");

    // Logout option inside mobile menu
    private final By mobileLogoutButton =
            By.xpath("//span[normalize-space()='Logout']");

    // =========================================================
    // SCREENSHOT
    // =========================================================

    private void captureScreenshot(String name) {

        try {

            String timestamp =
                    LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern(
                                    "yyyyMMdd_HHmmss"
                            )
                    );

            String directory =
                    "target/screenshots/logout";

            File file =
                    new File(directory);

            if (!file.exists()) {
                file.mkdirs();
            }

            String screenshotPath =
                    directory
                    + "/"
                    + name
                    + "_"
                    + timestamp
                    + ".png";

            File screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(
                                    OutputType.FILE
                            );

            Files.copy(
                    screenshot.toPath(),
                    Paths.get(screenshotPath),
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (IOException e) {

            System.out.println(
                    "Unable to save screenshot: "
                    + e.getMessage()
            );
        }
    }

    // =========================================================
    // LOGOUT
    // =========================================================

    public void logout_session() {

        try {

            // -------------------------------------------------
            // 1. Wait for dashboard
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions.urlContains(
                            "userDashboard"
                    )
            );

            // -------------------------------------------------
            // 2. Click mobile 3-line menu
            // -------------------------------------------------

            WebElement menu =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    mobileMenuButton
                            )
                    );

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            menu
                    );

            // -------------------------------------------------
            // 3. Click Logout
            // -------------------------------------------------

            WebElement logout =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    mobileLogoutButton
                            )
                    );

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            logout
                    );

            // -------------------------------------------------
            // 4. Verify logout
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions.not(
                            ExpectedConditions.urlContains(
                                    "userDashboard"
                            )
                    )
            );

            captureScreenshot(
                    "logout_success"
            );

        } catch (Exception e) {

            captureScreenshot(
                    "logout_failed"
            );

            throw new RuntimeException(
                    "Logout failed: "
                    + e.getMessage(),
                    e
            );
        }
    }
}
