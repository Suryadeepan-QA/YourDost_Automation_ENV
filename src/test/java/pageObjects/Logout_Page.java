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

    // -------------------------
    // MOBILE MENU
    // -------------------------

    private final By mobileMenuButton =
            By.cssSelector("[data-testid='MenuIcon']");


    // -------------------------
    // DESKTOP PROFILE
    // -------------------------

    private final By desktopProfileButton =
            By.xpath(
                    "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button"
            );


    // -------------------------
    // MOBILE LOGOUT
    // -------------------------

    private final By mobileLogoutButton =
            By.xpath(
                    "//span[normalize-space()='Logout']"
            );


    // -------------------------
    // DESKTOP LOGOUT
    // -------------------------

    private final By desktopLogoutButton =
            By.xpath(
                    "//li[normalize-space()='Logout']"
            );


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
                    + File.separator
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

            waitForPageToLoad();

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
    // CHECK ELEMENT DISPLAYED
    // =========================================================

    private boolean isElementDisplayed(By locator) {

        try {

            return driver.findElement(locator).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }


    // =========================================================
    // CLICK MOBILE MENU
    // =========================================================

    private void clickMobileMenu() {

        System.out.println(
                "\n[LOGOUT] Mobile layout detected."
        );

        System.out.println(
                "[LOGOUT] Waiting for mobile menu..."
        );

        try {

            WebElement menu =
                    wait.until(
                            ExpectedConditions
                                    .elementToBeClickable(
                                            mobileMenuButton
                                    )
                    );

            System.out.println(
                    "[LOGOUT] Mobile 3-line menu found."
            );

            menu.click();

            System.out.println(
                    "[LOGOUT SUCCESS] Mobile menu clicked."
            );

            captureScreenshot(
                    "mobile_menu_clicked"
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
                    "mobile_menu_click_failed"
            );

            throw new RuntimeException(
                    "Mobile menu could not be clicked: "
                    + e.getMessage(),
                    e
            );
        }
    }


    // =========================================================
    // CLICK DESKTOP PROFILE
    // =========================================================

    private void clickDesktopProfile() {

        System.out.println(
                "\n[LOGOUT] Desktop layout detected."
        );

        System.out.println(
                "[LOGOUT] Waiting for desktop profile button..."
        );

        try {

            WebElement profile =
                    wait.until(
                            ExpectedConditions
                                    .elementToBeClickable(
                                            desktopProfileButton
                                    )
                    );

            System.out.println(
                    "[LOGOUT] Desktop profile button found."
            );

            profile.click();

            System.out.println(
                    "[LOGOUT SUCCESS] Desktop profile clicked."
            );

            captureScreenshot(
                    "desktop_profile_clicked"
            );

        } catch (Exception e) {

            System.out.println(
                    "[LOGOUT FAILED] Unable to click desktop profile."
            );

            System.out.println(
                    "[LOGOUT FAILED] Reason: "
                    + e.getMessage()
            );

            captureScreenshot(
                    "desktop_profile_click_failed"
            );

            throw new RuntimeException(
                    "Desktop profile could not be clicked: "
                    + e.getMessage(),
                    e
            );
        }
    }


    // =========================================================
    // CLICK MOBILE LOGOUT
    // =========================================================

    private void clickMobileLogout() {

        System.out.println(
                "\n[LOGOUT] Waiting for Mobile Logout option..."
        );

        try {

            WebElement logout =
                    wait.until(
                            ExpectedConditions
                                    .elementToBeClickable(
                                            mobileLogoutButton
                                    )
                    );

            System.out.println(
                    "[LOGOUT] Mobile Logout option found."
            );

            logout.click();

            System.out.println(
                    "[LOGOUT SUCCESS] Mobile Logout clicked."
            );

            captureScreenshot(
                    "mobile_logout_clicked"
            );

        } catch (Exception e) {

            System.out.println(
                    "[LOGOUT FAILED] Unable to click Mobile Logout."
            );

            System.out.println(
                    "[LOGOUT FAILED] Reason: "
                    + e.getMessage()
            );

            captureScreenshot(
                    "mobile_logout_click_failed"
            );

            throw new RuntimeException(
                    "Mobile Logout could not be clicked: "
                    + e.getMessage(),
                    e
            );
        }
    }


    // =========================================================
    // CLICK DESKTOP LOGOUT
    // =========================================================

    private void clickDesktopLogout() {

        System.out.println(
                "\n[LOGOUT] Waiting for Desktop Logout option..."
        );

        try {

            WebElement logout =
                    wait.until(
                            ExpectedConditions
                                    .elementToBeClickable(
                                            desktopLogoutButton
                                    )
                    );

            System.out.println(
                    "[LOGOUT] Desktop Logout option found."
            );

            logout.click();

            System.out.println(
                    "[LOGOUT SUCCESS] Desktop Logout clicked."
            );

            captureScreenshot(
                    "desktop_logout_clicked"
            );

        } catch (Exception e) {

            System.out.println(
                    "[LOGOUT FAILED] Unable to click Desktop Logout."
            );

            System.out.println(
                    "[LOGOUT FAILED] Reason: "
                    + e.getMessage()
            );

            captureScreenshot(
                    "desktop_logout_click_failed"
            );

            throw new RuntimeException(
                    "Desktop Logout could not be clicked: "
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
    // COMPLETE LOGOUT
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
            // 2. Detect Desktop / Mobile
            // -------------------------------------------------

            System.out.println(
                    "[LOGOUT] Detecting dashboard layout..."
            );


            // =================================================
            // MOBILE
            // =================================================

            if (isElementDisplayed(mobileMenuButton)) {

                clickMobileMenu();

                clickMobileLogout();
            }


            // =================================================
            // DESKTOP
            // =================================================

            else if (isElementDisplayed(desktopProfileButton)) {

                clickDesktopProfile();

                clickDesktopLogout();
            }


            // =================================================
            // UNKNOWN
            // =================================================

            else {

                throw new RuntimeException(
                        "Unable to detect Desktop or Mobile logout UI."
                );
            }


            // -------------------------------------------------
            // 3. Verify Logout
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
