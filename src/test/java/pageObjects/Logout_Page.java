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
    private final By menuButton =
            By.cssSelector("[data-testid='MenuIcon']");

    // Logout option inside mobile menu
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

            System.out.println(
                    "[SCREENSHOT] Saved: "
                    + screenshotPath
            );

        } catch (IOException e) {

            System.out.println(
                    "[SCREENSHOT] Failed: "
                    + e.getMessage()
            );
        }
    }

    // =========================================================
    // WAIT FOR PAGE LOAD
    // =========================================================

    private void waitForPageToLoad() {

        System.out.println(
                "[PAGE LOAD] Waiting for page to load..."
        );

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

        System.out.println(
                "[PAGE LOAD] Page loaded successfully."
        );
    }

    // =========================================================
    // VERIFY LOGIN SUCCESS
    // =========================================================

    public void verifyLoginSuccess() {

        System.out.println(
                "\n=================================================="
        );

        System.out.println(
                "              LOGIN VERIFICATION"
        );

        System.out.println(
                "=================================================="
        );

        try {

            // -------------------------------------------------
            // 1. Wait for page load
            // -------------------------------------------------

            waitForPageToLoad();

            // -------------------------------------------------
            // 2. Wait for dashboard URL
            // -------------------------------------------------

            System.out.println(
                    "[LOGIN] Waiting for dashboard..."
            );

            wait.until(
                    ExpectedConditions.urlContains(
                            "userDashboard"
                    )
            );

            System.out.println(
                    "[LOGIN SUCCESS] Dashboard URL detected."
            );

            System.out.println(
                    "[LOGIN SUCCESS] Current URL: "
                    + driver.getCurrentUrl()
            );

            System.out.println(
                    "=================================================="
            );

        } catch (Exception e) {

            System.out.println(
                    "\n[LOGIN FAILED] Login verification failed."
            );

            System.out.println(
                    "[LOGIN FAILED] Current URL: "
                    + driver.getCurrentUrl()
            );

            System.out.println(
                    "[LOGIN FAILED] Reason: "
                    + e.getMessage()
            );

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

        System.out.println(
                "\n[LOGOUT] Waiting for mobile menu..."
        );

        try {

            WebElement menu =
                    wait.until(
                            ExpectedConditions
                                    .elementToBeClickable(
                                            menuButton
                                    )
                    );

            System.out.println(
                    "[LOGOUT] Mobile 3-line menu found."
            );

            menu.click();

            System.out.println(
                    "[LOGOUT SUCCESS] Mobile menu clicked successfully."
            );

            captureScreenshot(
                    "menu_clicked"
            );

        } catch (Exception e) {

            System.out.println(
                    "[LOGOUT FAILED] Unable to click mobile menu."
            );

            System.out.println(
                    "[LOGOUT FAILED] Reason: "
                    + e.getMessage()
            );

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

        System.out.println(
                "\n[LOGOUT] Waiting for Logout option..."
        );

        try {

            WebElement logout =
                    wait.until(
                            ExpectedConditions
                                    .elementToBeClickable(
                                            logoutButton
                                    )
                    );

            System.out.println(
                    "[LOGOUT] Logout option found."
            );

            logout.click();

            System.out.println(
                    "[LOGOUT SUCCESS] Logout button clicked successfully."
            );

            captureScreenshot(
                    "logout_clicked"
            );

        } catch (Exception e) {

            System.out.println(
                    "[LOGOUT FAILED] Unable to click Logout."
            );

            System.out.println(
                    "[LOGOUT FAILED] Reason: "
                    + e.getMessage()
            );

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
    // VERIFY LOGOUT SUCCESS
    // =========================================================

    private void verifyLogoutSuccess() {

        System.out.println(
                "\n[LOGOUT] Verifying logout..."
        );

        try {

            wait.until(
                    ExpectedConditions.not(
                            ExpectedConditions.urlContains(
                                    "userDashboard"
                            )
                    )
            );

            System.out.println(
                    "[LOGOUT SUCCESS] Logout completed successfully."
            );

            System.out.println(
                    "[LOGOUT SUCCESS] Current URL: "
                    + driver.getCurrentUrl()
            );

            System.out.println(
                    "=================================================="
            );

        } catch (Exception e) {

            System.out.println(
                    "[LOGOUT FAILED] Logout verification failed."
            );

            System.out.println(
                    "[LOGOUT FAILED] Current URL: "
                    + driver.getCurrentUrl()
            );

            System.out.println(
                    "[LOGOUT FAILED] Reason: "
                    + e.getMessage()
            );

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

        System.out.println(
                "\n=================================================="
        );

        System.out.println(
                "              STARTING LOGOUT"
        );

        System.out.println(
                "=================================================="
        );

        try {

            // -------------------------------------------------
            // 1. Make sure dashboard is open
            // -------------------------------------------------

            System.out.println(
                    "[LOGOUT] Checking dashboard..."
            );

            wait.until(
                    ExpectedConditions.urlContains(
                            "userDashboard"
                    )
            );

            System.out.println(
                    "[LOGOUT] Dashboard detected."
            );

            // -------------------------------------------------
            // 2. Click 3-line mobile menu
            // -------------------------------------------------

            clickMenu();

            // -------------------------------------------------
            // 3. Click Logout
            // -------------------------------------------------

            clickLogout();

            // -------------------------------------------------
            // 4. Verify Logout
            // -------------------------------------------------

            verifyLogoutSuccess();

            System.out.println(
                    "\n=================================================="
            );

            System.out.println(
                    "           LOGOUT COMPLETED SUCCESSFULLY"
            );

            System.out.println(
                    "=================================================="
            );

        } catch (Exception e) {

            System.out.println(
                    "\n=================================================="
            );

            System.out.println(
                    "                 LOGOUT FAILED"
            );

            System.out.println(
                    "=================================================="
            );

            System.out.println(
                    "[LOGOUT FAILED] Current URL: "
                    + driver.getCurrentUrl()
            );

            System.out.println(
                    "[LOGOUT FAILED] Reason: "
                    + e.getMessage()
            );

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
