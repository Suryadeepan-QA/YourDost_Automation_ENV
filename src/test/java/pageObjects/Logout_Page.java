package pageObjects;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

        this.wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(30)
                );
    }

    // =========================================================
    // URL
    // =========================================================

    private final String DASHBOARD_URL =
            "https://staging-app.yourdost.com/userDashboard/home";

    // =========================================================
    // SCREENSHOT DIRECTORY
    // =========================================================

    private String getScreenshotDirectory() {

        String directory =
                "target/screenshots/login";

        File file =
                new File(directory);

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
    // LOGIN SUCCESS VERIFICATION
    // =========================================================

    public void verifyLoginSuccess() {

        System.out.println(
                "\n# STARTING LOGIN VERIFICATION"
        );

        try {

            // -------------------------------------------------
            // 1. Wait for page
            // -------------------------------------------------

            waitForPageToLoad();

            // -------------------------------------------------
            // 2. Print URL
            // -------------------------------------------------

            System.out.println(
                    "Current URL: "
                    + driver.getCurrentUrl()
            );

            // -------------------------------------------------
            // 3. Print title
            // -------------------------------------------------

            System.out.println(
                    "Current Title: "
                    + driver.getTitle()
            );

            // -------------------------------------------------
            // 4. Wait for dashboard URL
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
            // 5. Make sure desktop resolution is used
            // -------------------------------------------------

            driver.manage()
                    .window()
                    .setSize(
                            new org.openqa.selenium.Dimension(
                                    1920,
                                    1080
                            )
                    );

            System.out.println(
                    "Desktop resolution set: "
                    + driver.manage()
                            .window()
                            .getSize()
            );

            // -------------------------------------------------
            // 6. Wait for body
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions
                            .presenceOfElementLocated(
                                    By.tagName("body")
                            )
            );

            // -------------------------------------------------
            // 7. Wait for dashboard rendering
            // -------------------------------------------------

            waitForDashboardRendering();

            // -------------------------------------------------
            // 8. Try Profile
            // -------------------------------------------------

            WebElement profile =
                    findProfileButton();

            if (profile != null) {

                System.out.println(
                        "\n# LOGIN VERIFICATION SUCCESSFUL"
                );

                System.out.println(
                        "Desktop Profile navigation found."
                );

                return;
            }

            // -------------------------------------------------
            // Profile not found
            // -------------------------------------------------

            System.out.println(
                    "Profile button was not found."
            );

            printDashboardDiagnostics();

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
    // WAIT FOR DASHBOARD RENDERING
    // =========================================================

    private void waitForDashboardRendering() {

        System.out.println(
                "Waiting for dashboard UI..."
        );

        wait.until(
                webDriver -> {

                    try {

                        String readyState =
                                (String)
                                ((JavascriptExecutor) webDriver)
                                        .executeScript(
                                                "return document.readyState"
                                        );

                        if (!"complete".equals(
                                readyState)) {

                            return false;
                        }

                        String bodyText =
                                webDriver
                                        .findElement(
                                                By.tagName("body")
                                        )
                                        .getText();

                        return bodyText != null
                                && bodyText.trim().length() > 0;

                    } catch (Exception e) {

                        return false;
                    }
                }
        );

        System.out.println(
                "Dashboard UI rendered."
        );
    }

    // =========================================================
    // FIND PROFILE BUTTON
    // =========================================================

    private WebElement findProfileButton() {

        System.out.println(
                "Waiting for desktop Profile/User icon..."
        );

        /*
         * IMPORTANT:
         *
         * These are candidate locators.
         *
         * The best solution is still to replace the
         * correct candidate with the actual DOM locator
         * after inspecting the Profile icon.
         */

        By[] profileLocators = {

            // aria-label
            By.xpath(
                    "//*[@aria-label='Profile']"
            ),

            By.xpath(
                    "//*[@aria-label='User']"
            ),

            By.xpath(
                    "//*[@aria-label='Account']"
            ),

            // title
            By.xpath(
                    "//*[@title='Profile']"
            ),

            By.xpath(
                    "//*[@title='User']"
            ),

            By.xpath(
                    "//*[@title='Account']"
            ),

            // image alt
            By.xpath(
                    "//img[contains(" +
                    "translate(@alt," +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                    "'abcdefghijklmnopqrstuvwxyz')," +
                    "'profile')]"
            ),

            By.xpath(
                    "//img[contains(" +
                    "translate(@alt," +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                    "'abcdefghijklmnopqrstuvwxyz')," +
                    "'user')]"
            ),

            // buttons containing profile/user/account text
            By.xpath(
                    "//button[contains(" +
                    "translate(.," +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                    "'abcdefghijklmnopqrstuvwxyz')," +
                    "'profile')]"
            ),

            By.xpath(
                    "//button[contains(" +
                    "translate(.," +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                    "'abcdefghijklmnopqrstuvwxyz')," +
                    "'account')]"
            ),

            // links containing profile/account
            By.xpath(
                    "//a[contains(" +
                    "translate(.," +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                    "'abcdefghijklmnopqrstuvwxyz')," +
                    "'profile')]"
            ),

            By.xpath(
                    "//a[contains(" +
                    "translate(.," +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                    "'abcdefghijklmnopqrstuvwxyz')," +
                    "'account')]"
            )
        };

        // =====================================================
        // TRY EACH LOCATOR
        // =====================================================

        for (By locator : profileLocators) {

            try {

                List<WebElement> elements =
                        driver.findElements(locator);

                for (WebElement element : elements) {

                    if (element.isDisplayed()
                            && element.isEnabled()) {

                        System.out.println(
                                "Profile candidate found: "
                                + locator
                        );

                        return element;
                    }
                }

            } catch (Exception ignored) {
            }
        }

        System.out.println(
                "No Profile candidate was found."
        );

        return null;
    }

    // =========================================================
    // CLICK PROFILE
    // =========================================================

    private void clickProfile() {

        System.out.println(
                "Waiting for desktop Profile..."
        );

        WebElement profile =
                findProfileButton();

        if (profile == null) {

            printDashboardDiagnostics();

            captureScreenshot(
                    "profile_not_found"
            );

            throw new RuntimeException(
                    "Desktop Profile button was not found."
            );
        }

        // -----------------------------------------------------
        // Scroll into view
        // -----------------------------------------------------

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView" +
                        "({block:'center'});",
                        profile
                );

        // -----------------------------------------------------
        // Wait for clickable
        // -----------------------------------------------------

        try {

            wait.until(
                    ExpectedConditions
                            .elementToBeClickable(profile)
            );

        } catch (Exception ignored) {
        }

        // -----------------------------------------------------
        // Click
        // -----------------------------------------------------

        try {

            profile.click();

        } catch (Exception e) {

            System.out.println(
                    "Normal Profile click failed."
            );

            System.out.println(
                    "Trying JavaScript click..."
            );

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            profile
                    );
        }

        System.out.println(
                "Desktop Profile clicked."
        );

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
                    wait.until(
                            ExpectedConditions
                                    .visibilityOfElementLocated(
                                            By.xpath(
                                                    "//*[normalize-space()='Logout']"
                                            )
                                    )
                    );

            System.out.println(
                    "Logout option is visible."
            );

            return logout;

        } catch (Exception e) {

            System.out.println(
                    "Logout option was not found."
            );

            printLogoutDiagnostics();

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
        // Scroll
        // -----------------------------------------------------

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView" +
                        "({block:'center'});",
                        logout
                );

        // -----------------------------------------------------
        // Click
        // -----------------------------------------------------

        try {

            wait.until(
                    ExpectedConditions
                            .elementToBeClickable(logout)
            );

            logout.click();

        } catch (Exception e) {

            System.out.println(
                    "Normal Logout click failed."
            );

            System.out.println(
                    "Trying JavaScript click..."
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

        captureScreenshot(
                "logout_clicked"
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

            wait.until(
                    ExpectedConditions.not(
                            ExpectedConditions.urlContains(
                                    "userDashboard"
                            )
                    )
            );

            System.out.println(
                    "\n# LOGOUT VERIFICATION SUCCESSFUL"
            );

            System.out.println(
                    "Current URL: "
                    + driver.getCurrentUrl()
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
            // 1. Dashboard
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
            // 2. Force desktop
            // -------------------------------------------------

            driver.manage()
                    .window()
                    .setSize(
                            new org.openqa.selenium.Dimension(
                                    1920,
                                    1080
                            )
                    );

            System.out.println(
                    "Desktop browser size: "
                    + driver.manage()
                            .window()
                            .getSize()
            );

            // -------------------------------------------------
            // 3. Wait dashboard
            // -------------------------------------------------

            waitForDashboardRendering();

            // -------------------------------------------------
            // 4. Click Profile
            // -------------------------------------------------

            clickProfile();

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

    // =========================================================
    // PROFILE / DASHBOARD DIAGNOSTICS
    // =========================================================

    private void printDashboardDiagnostics() {

        System.out.println(
                "\n# PROFILE DIAGNOSTICS"
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
                "Browser size: "
                + driver.manage()
                        .window()
                        .getSize()
        );

        // -----------------------------------------------------
        // Buttons
        // -----------------------------------------------------

        List<WebElement> buttons =
                driver.findElements(
                        By.tagName("button")
                );

        System.out.println(
                "Buttons found: "
                + buttons.size()
        );

        int buttonCount = 0;

        for (WebElement button : buttons) {

            try {

                if (!button.isDisplayed()) {
                    continue;
                }

                String text =
                        button.getText();

                String aria =
                        button.getAttribute(
                                "aria-label"
                        );

                String title =
                        button.getAttribute(
                                "title"
                        );

                String className =
                        button.getAttribute(
                                "class"
                        );

                System.out.println(
                        "BUTTON ["
                        + buttonCount
                        + "]"
                );

                System.out.println(
                        " text = [" + text + "]"
                );

                System.out.println(
                        " aria-label = [" + aria + "]"
                );

                System.out.println(
                        " title = [" + title + "]"
                );

                System.out.println(
                        " class = [" + className + "]"
                );

                buttonCount++;

                // Avoid huge GitHub Actions logs
                if (buttonCount >= 30) {
                    break;
                }

            } catch (Exception ignored) {
            }
        }
    }

    // =========================================================
    // LOGOUT DIAGNOSTICS
    // =========================================================

    private void printLogoutDiagnostics() {

        System.out.println(
                "\n# LOGOUT ELEMENT DIAGNOSTICS"
        );

        List<WebElement> elements =
                driver.findElements(
                        By.xpath(
                                "//*[normalize-space()='Logout']"
                        )
                );

        System.out.println(
                "Logout elements found: "
                + elements.size()
        );

        for (WebElement element : elements) {

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

                System.out.println(
                        "Logout aria-label: "
                        + element.getAttribute(
                                "aria-label"
                        )
                );

            } catch (Exception ignored) {
            }
        }
    }
}