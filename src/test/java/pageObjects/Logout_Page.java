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

    /*
     * Mobile hamburger menu.
     *
     * HTML:
     * <svg data-testid="MenuIcon">
     */
    private final By menuButton =
            By.cssSelector("[data-testid='MenuIcon']");

    /*
     * Logout option inside mobile menu.
     *
     * HTML:
     * <span>Logout</span>
     */
    private final By logoutButton =
            By.xpath("//span[normalize-space()='Logout']");

    // =========================================================
    // SCREENSHOT DIRECTORY
    // =========================================================

    private String getScreenshotDirectory() {

        String directory =
                "target/screenshots/login";

        File file = new File(directory);

        if (!file.exists()) {
            file.mkdirs();
        }

        return directory;
    }

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

            String screenshotPath =
                    getScreenshotDirectory()
                    + "/"
                    + name
                    + "_"
                    + timestamp
                    + ".png";

            File screenshotFile =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(
                                    OutputType.FILE
                            );

            Files.copy(
                    screenshotFile.toPath(),
                    Paths.get(screenshotPath),
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (IOException e) {

            System.out.println(
                    "Screenshot failed: "
                    + e.getMessage()
            );
        }
    }

    // =========================================================
    // WAIT FOR PAGE LOAD
    // =========================================================

    private void waitForPageToLoad() {

        wait.until(
                webDriver -> {

                    Object state =
                            ((JavascriptExecutor) webDriver)
                                    .executeScript(
                                            "return document.readyState"
                                    );

                    return "complete".equals(state);
                }
        );
    }

    // =========================================================
    // VERIFY LOGIN SUCCESS
    // =========================================================

    public void verifyLoginSuccess() {

        try {

            waitForPageToLoad();

            wait.until(
                    ExpectedConditions.urlContains(
                            "userDashboard"
                    )
            );

        } catch (Exception e) {

            captureScreenshot(
                    "login_verification_failed"
            );

            throw new RuntimeException(
                    "Login verification failed: "
                    + e.getMessage(),
                    e
            );
        }
    }

    // =========================================================
    // CLICK MOBILE MENU
    // =========================================================

    private void clickMenu() {

        try {

            WebElement menu =
                    wait.until(
                            ExpectedConditions
                                    .elementToBeClickable(
                                            menuButton
                                    )
                    );

            menu.click();

        } catch (Exception e) {

            captureScreenshot(
                    "menu_click_failed"
            );

            throw new RuntimeException(
                    "Mobile menu could not be clicked: "
                    + e.getMessage(),
                    e
            );
        }
    }

    // =========================================================
    // CLICK LOGOUT
    // =========================================================

    private void clickLogout() {

        try {

            WebElement logout =
                    wait.until(
                            ExpectedConditions
                                    .elementToBeClickable(
                                            logoutButton
                                    )
                    );

            logout.click();

        } catch (Exception e) {

            captureScreenshot(
                    "logout_click_failed"
            );

            throw new RuntimeException(
                    "Logout button could not be clicked: "
                    + e.getMessage(),
                    e
            );
        }
    }

    // =========================================================
    // VERIFY LOGOUT
    // =========================================================

    private void verifyLogoutSuccess() {

        try {

            wait.until(
                    ExpectedConditions.not(
                            ExpectedConditions.urlContains(
                                    "userDashboard"
                            )
                    )
            );

        } catch (Exception e) {

            captureScreenshot(
                    "logout_verification_failed"
            );

            throw new RuntimeException(
                    "Logout verification failed: "
                    + e.getMessage(),
                    e
            );
        }
    }

    // =========================================================
    // COMPLETE MOBILE LOGOUT
    // =========================================================

    public void logout_session() {

        try {

            // Make sure we are on dashboard
            wait.until(
                    ExpectedConditions.urlContains(
                            "userDashboard"
                    )
            );

            // Step 1: Click 3-line menu
            clickMenu();

            // Step 2: Click Logout
            clickLogout();

            // Step 3: Verify logout
            verifyLogoutSuccess();

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
