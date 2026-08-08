package pageObjects;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    // URL
    // =========================================================

    private final String DASHBOARD_URL =
            "https://staging-app.yourdost.com/userDashboard/home";

    // =========================================================
    // PROFILE LOCATORS
    // =========================================================

    /*
     * IMPORTANT:
     *
     * Replace the first locator below with the ACTUAL locator
     * of your Profile/User icon if available.
     *
     * Do not keep adding random generic XPath.
     */

    private final By profileButton = By.xpath(
            "//*[contains(@class,'profile') " +
            "or contains(@class,'Profile') " +
            "or contains(@class,'user') " +
            "or contains(@class,'User')]"
    );

    // =========================================================
    // LOGOUT LOCATORS
    // =========================================================

    /*
     * We don't assume Logout is necessarily inside <li>.
     *
     * Your previous locator was:
     *
     * //li[normalize-space()='Logout']
     *
     * That can fail if Logout is a div, button, a, etc.
     */

    private final By logoutButton = By.xpath(
            "//*[normalize-space()='Logout']"
    );

    // =========================================================
    // DASHBOARD ELEMENTS
    // =========================================================

    /*
     * Add elements that are definitely present on the
     * dashboard after login.
     *
     * These are only examples. Replace them with an actual
     * dashboard element from your application.
     */

    private final By dashboardBody = By.tagName("body");

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
    // SCREENSHOT METHOD
    // =========================================================

    private void captureScreenshot(String name) {

        try {

            String timestamp =
                    LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern(
                                    "yyyyMMdd_HHmmss"
                            )
                    );

            String screenshotDir =
                    getScreenshotDirectory();

            String screenshotPath =
                    screenshotDir
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
                    "Screenshot saved: "
                    + screenshotPath
            );

        } catch (IOException e) {

            System.out.println(
                    "Unable to save screenshot: "
                    + e.getMessage()
            );
        }
    }

    // =========================================================
    // WAIT FOR PAGE
    // =========================================================

    private void waitForPageToLoad() {

        wait.until(
                webDriver ->
                        ((JavascriptExecutor) webDriver)
                                .executeScript(
                                        "return document.readyState"
                                )
                                .equals("complete")
        );
    }

    // =========================================================
    // LOGIN SUCCESS VERIFICATION
    // =========================================================

    public void verifyLoginSuccess() {

        System.out.println(
                "\n# STARTING LOGIN VERIFICATION"
        );

        try {

            // -------------------------------------------------
            // 1. Wait for page loading
            // -------------------------------------------------

            waitForPageToLoad();

            // -------------------------------------------------
            // 2. Print URL
            // -------------------------------------------------

            System.out.println(
                    "Current URL: "
                    + driver.getCurrentUrl()
            );

            System.out.println(
                    "Current Title: "
                    + driver.getTitle()
            );

            // -------------------------------------------------
            // 3. Verify dashboard URL
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions.urlContains(
                            "userDashboard"
                    )
            );

            System.out.println(
                    "Dashboard URL detected."
            );

            // -------------------------------------------------
            // 4. Wait for body
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            dashboardBody
                    )
            );

            // -------------------------------------------------
            // 5. Wait for Profile/User icon
            // -------------------------------------------------

            WebElement profile =
                    waitForProfileButton();

            if (profile != null) {

                System.out.println(
                        "\n# LOGIN VERIFICATION SUCCESSFUL"
                );

                System.out.println(
                        "Profile navigation found."
                );

                return;
            }

            // -------------------------------------------------
            // Profile not found
            // -------------------------------------------------

            captureScreenshot(
                    "login_verification_failed"
            );

            throw new RuntimeException(
                    "Login verification failed: "
                    + "Profile button was not found."
            );

        } catch (Exception e) {

            System.out.println(
                    "\n# LOGIN VERIFICATION FAILED"
            );

            System.out.println(
                    "Current URL: "
                    + driver.getCurrentUrl()
            );

            System.out.println(
                    "Current Title: "
                    + driver.getTitle()
            );

            System.out.println(
                    "Reason: "
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
    // WAIT FOR PROFILE
    // =========================================================

    private WebElement waitForProfileButton() {

        System.out.println(
                "Waiting for Profile/User icon..."
        );

        try {

            WebElement profile =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(20)
                    ).until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    profileButton
                            )
                    );

            System.out.println(
                    "Profile/User icon is visible."
            );

            return profile;

        } catch (Exception e) {

            System.out.println(
                    "Profile/User icon was not found."
            );

            return null;
        }
    }

    // =========================================================
    // CLICK PROFILE
    // =========================================================

    private void clickProfile() {

        System.out.println(
                "Waiting for Profile navigation..."
        );

        WebElement profile =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                profileButton
                        )
                );

        // -----------------------------------------------------
        // Scroll profile into view
        // -----------------------------------------------------

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView" +
                        "({block:'center'});",
                        profile
                );

        // -----------------------------------------------------
        // Wait until clickable
        // -----------------------------------------------------

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        profileButton
                )
        );

        // -----------------------------------------------------
        // Click
        // -----------------------------------------------------

        try {

            profile.click();

        } catch (Exception e) {

            System.out.println(
                    "Normal click failed. "
                    + "Trying JavaScript click."
            );

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            profile
                    );
        }

        System.out.println(
                "Profile clicked."
        );

        // -----------------------------------------------------
        // Screenshot after profile click
        // -----------------------------------------------------

        captureScreenshot(
                "profile_clicked"
        );
    }

    // =========================================================
    // WAIT FOR LOGOUT
    // =========================================================

    private WebElement waitForLogoutButton() {

        System.out.println(
                "Waiting for Logout option..."
        );

        try {

            WebElement logout =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(15)
                    ).until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    logoutButton
                            )
                    );

            System.out.println(
                    "Logout option is visible."
            );

            return logout;

        } catch (Exception e) {

            // -------------------------------------------------
            // Diagnostic information
            // -------------------------------------------------

            System.out.println(
                    "\n# LOGOUT ELEMENT DIAGNOSTICS"
            );

            System.out.println(
                    "Current URL: "
                    + driver.getCurrentUrl()
            );

            System.out.println(
                    "Current Title: "
                    + driver.getTitle()
            );

            List<WebElement> logoutElements =
                    driver.findElements(
                            By.xpath(
                                    "//*[normalize-space()='Logout']"
                            )
                    );

            System.out.println(
                    "Logout elements found: "
                    + logoutElements.size()
            );

            for (WebElement element :
                    logoutElements) {

                try {

                    System.out.println(
                            "Logout text: ["
                            + element.getText()
                            + "]"
                    );

                    System.out.println(
                            "Logout tag: "
                            + element.getTagName()
                    );

                    System.out.println(
                            "Logout class: "
                            + element.getAttribute(
                                    "class"
                            )
                    );

                } catch (Exception ignored) {
                }
            }

            captureScreenshot(
                    "logout_option_not_found"
            );

            return null;
        }
    }

    // =========================================================
    // CLICK LOGOUT
    // =========================================================

    private void clickLogout() {

        WebElement logout =
                waitForLogoutButton();

        if (logout == null) {

            throw new RuntimeException(
                    "Logout option was not found "
                    + "after clicking Profile."
            );
        }

        // -----------------------------------------------------
        // Scroll Logout into view
        // -----------------------------------------------------

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView" +
                        "({block:'center'});",
                        logout
                );

        // -----------------------------------------------------
        // Wait until clickable
        // -----------------------------------------------------

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        logoutButton
                )
        );

        // -----------------------------------------------------
        // Click Logout
        // -----------------------------------------------------

        try {

            logout.click();

        } catch (Exception e) {

            System.out.println(
                    "Normal Logout click failed. "
                    + "Trying JavaScript click."
            );

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            logout
                    );
        }

        System.out.println(
                "Logout clicked."
        );
    }

    // =========================================================
    // VERIFY LOGOUT
    // =========================================================

    private void verifyLogoutSuccess() {

        System.out.println(
                "Verifying Logout..."
        );

        try {

            new WebDriverWait(
                    driver,
                    Duration.ofSeconds(20)
            ).until(
                    ExpectedConditions.not(
                            ExpectedConditions.urlContains(
                                    "userDashboard"
                            )
                    )
            );

            System.out.println(
                    "\n# LOGOUT VERIFICATION SUCCESSFUL"
            );

        } catch (Exception e) {

            System.out.println(
                    "\n# LOGOUT VERIFICATION FAILED"
            );

            System.out.println(
                    "Current URL: "
                    + driver.getCurrentUrl()
            );

            System.out.println(
                    "Current Title: "
                    + driver.getTitle()
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
    // COMPLETE LOGOUT SESSION
    // =========================================================

    public void logout_session() {

        System.out.println(
                "\n# STARTING LOGOUT"
        );

        try {

            // -------------------------------------------------
            // 1. Make sure we are on dashboard
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions.urlContains(
                            "userDashboard"
                    )
            );

            System.out.println(
                    "Dashboard detected."
            );

            // -------------------------------------------------
            // 2. Wait for Profile
            // -------------------------------------------------

            WebElement profile =
                    waitForProfileButton();

            if (profile == null) {

                throw new RuntimeException(
                        "Profile button was not found "
                        + "during logout."
                );
            }

            System.out.println(
                    "Profile navigation found."
            );

            // -------------------------------------------------
            // 3. Click Profile
            // -------------------------------------------------

            clickProfile();

            // -------------------------------------------------
            // 4. Wait for Logout
            // -------------------------------------------------

            waitForLogoutButton();

            // -------------------------------------------------
            // 5. Click Logout
            // -------------------------------------------------

            clickLogout();

            // -------------------------------------------------
            // 6. Verify Logout
            // -------------------------------------------------

            verifyLogoutSuccess();

        } catch (Exception e) {

            System.out.println(
                    "\n# LOGOUT FAILED"
            );

            System.out.println(
                    "Current URL: "
                    + driver.getCurrentUrl()
            );

            System.out.println(
                    "Current Title: "
                    + driver.getTitle()
            );

            System.out.println(
                    "Reason: "
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