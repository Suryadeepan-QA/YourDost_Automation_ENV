package pageObjects;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Logout_Page extends BasePage {

    private WebDriverWait wait;

    public Logout_Page(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // =========================================================
    // LOCATORS
    // =========================================================

    /*
     * The actual HTML is:
     *
     * <div class="DesktopNavBar_usernameContainer__35kIp">
     *     <button ... aria-haspopup="true">
     *         <div class="MuiAvatar-root ...">S</div>
     *     </button>
     *
     * So we locate the clickable BUTTON inside the container.
     */
    private By profileButton = By.xpath(
            "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button"
    );

    /*
     * Username text
     *
     * Example:
     * <div class="DesktopNavBar_nameText__hAqgn">surya99</div>
     *
     * We intentionally do NOT use the generated suffix __hAqgn.
     */
    private By usernameElement = By.xpath(
            "//div[contains(@class,'DesktopNavBar_nameText')]"
    );

    /*
     * Logout menu item.
     */
    private By logoutButton = By.xpath(
            "//li[normalize-space()='Logout']"
    );

    /*
     * Alternative logout locator in case the application renders
     * the logout item differently.
     */
    private By logoutText = By.xpath(
            "//*[normalize-space()='Logout']"
    );


    // =========================================================
    // VERIFY LOGIN SUCCESS
    // =========================================================

    public void verifyLoginSuccess() {

        System.out.println();
        System.out.println("=================================================");
        System.out.println("STARTING LOGIN VERIFICATION");
        System.out.println("=================================================");

        try {

            System.out.println("Current URL : " + driver.getCurrentUrl());
            System.out.println("Current Title : " + driver.getTitle());

            // -------------------------------------------------
            // STEP 1 - Wait for dashboard URL
            // -------------------------------------------------

            System.out.println("Waiting for dashboard URL...");

            wait.until(ExpectedConditions.urlContains("/userDashboard"));

            System.out.println("Dashboard URL detected.");
            System.out.println("URL : " + driver.getCurrentUrl());


            // -------------------------------------------------
            // STEP 2 - Wait for page body
            // -------------------------------------------------

            System.out.println("Waiting for page body...");

            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.tagName("body")
                    )
            );

            System.out.println("Body element found.");


            // -------------------------------------------------
            // STEP 3 - Give React time to render
            // -------------------------------------------------

            System.out.println("Waiting for application UI to render...");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }


            // -------------------------------------------------
            // STEP 4 - Check profile button
            // -------------------------------------------------

            System.out.println();
            System.out.println("Checking profile button...");

            int profileCount = driver.findElements(profileButton).size();

            System.out.println(
                    "Profile button count: " + profileCount
            );

            if (profileCount > 0) {

                System.out.println("Profile button FOUND.");

                WebElement profile = driver.findElement(profileButton);

                System.out.println(
                        "Profile button displayed: "
                                + profile.isDisplayed()
                );

                System.out.println(
                        "Profile button enabled: "
                                + profile.isEnabled()
                );

            } else {

                System.out.println("Profile button NOT FOUND.");
            }


            // -------------------------------------------------
            // STEP 5 - Check username
            // -------------------------------------------------

            System.out.println();
            System.out.println("Checking username element...");

            int usernameCount = driver.findElements(usernameElement).size();

            System.out.println(
                    "Username element count: " + usernameCount
            );

            if (usernameCount > 0) {

                WebElement username =
                        driver.findElement(usernameElement);

                System.out.println(
                        "Username text: " + username.getText()
                );

            } else {

                System.out.println("Username element NOT FOUND.");
            }


            // -------------------------------------------------
            // STEP 6 - Check page source
            // -------------------------------------------------

            System.out.println();
            System.out.println("=================================================");
            System.out.println("PAGE SOURCE CHECK");
            System.out.println("=================================================");

            String pageSource = driver.getPageSource();

            System.out.println(
                    "Page source length: " + pageSource.length()
            );


            if (pageSource.contains("DesktopNavBar")) {

                System.out.println(
                        "DesktopNavBar FOUND in page source."
                );

            } else {

                System.out.println(
                        "DesktopNavBar NOT FOUND in page source."
                );
            }


            if (pageSource.contains("surya99")) {

                System.out.println(
                        "Username 'surya99' FOUND in page source."
                );

            } else {

                System.out.println(
                        "Username 'surya99' NOT FOUND in page source."
                );
            }


            // -------------------------------------------------
            // STEP 7 - Save screenshot
            // -------------------------------------------------

            captureDiagnosticScreenshot("login_verification");


            // -------------------------------------------------
            // STEP 8 - Print useful body text
            // -------------------------------------------------

            System.out.println();
            System.out.println("=================================================");
            System.out.println("VISIBLE BODY TEXT");
            System.out.println("=================================================");

            try {

                String bodyText = driver
                        .findElement(By.tagName("body"))
                        .getText();

                System.out.println(bodyText);

            } catch (Exception e) {

                System.out.println(
                        "Unable to read body text: "
                                + e.getMessage()
                );
            }


            // -------------------------------------------------
            // FINAL DECISION
            // -------------------------------------------------

            if (profileCount > 0 || usernameCount > 0) {

                System.out.println();
                System.out.println("LOGIN VERIFICATION SUCCESSFUL");

            } else {

                System.out.println();
                System.out.println("LOGIN VERIFICATION FAILED");

                System.out.println(
                        "Dashboard URL exists, but profile/username "
                                + "is not present in the DOM."
                );

                throw new RuntimeException(
                        "Login verification failed: "
                                + "profile button and username were not found."
                );
            }

        } catch (Exception e) {

            System.out.println();
            System.out.println("=================================================");
            System.out.println("LOGIN VERIFICATION FAILED");
            System.out.println("=================================================");

            try {
                System.out.println(
                        "Current URL after failure: "
                                + driver.getCurrentUrl()
                );

                System.out.println(
                        "Current Title after failure: "
                                + driver.getTitle()
                );

            } catch (Exception ignored) {
            }

            System.out.println(
                    "Reason: " + e.getMessage()
            );

            // Capture screenshot when verification fails
            captureDiagnosticScreenshot("login_verification_failed");

            // Re-throw so TestNG correctly marks the test as failed
            throw e;
        }
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    public void logout_session() {

        System.out.println();
        System.out.println("=================================================");
        System.out.println("STARTING LOGOUT");
        System.out.println("=================================================");

        try {

            System.out.println(
                    "Current URL: " + driver.getCurrentUrl()
            );


            // -------------------------------------------------
            // STEP 1 - Make sure we are on dashboard
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions.urlContains("/userDashboard")
            );


            // -------------------------------------------------
            // STEP 2 - Check profile button
            // -------------------------------------------------

            System.out.println(
                    "Checking profile button..."
            );

            if (driver.findElements(profileButton).isEmpty()) {

                System.out.println(
                        "User profile button is NOT present."
                );

                System.out.println(
                        "Skipping logout."
                );

                return;
            }

            System.out.println(
                    "User profile button found."
            );


            // -------------------------------------------------
            // STEP 3 - Click profile button
            // -------------------------------------------------

            System.out.println(
                    "Waiting for profile button to become clickable..."
            );

            WebElement profile =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    profileButton
                            )
                    );

            System.out.println(
                    "Profile button is clickable."
            );

            profile.click();

            System.out.println(
                    "Profile button clicked."
            );


            // -------------------------------------------------
            // STEP 4 - Wait for Logout
            // -------------------------------------------------

            System.out.println(
                    "Waiting for Logout option..."
            );

            WebElement logout =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    logoutButton
                            )
                    );

            System.out.println(
                    "Logout option found."
            );

            logout.click();

            System.out.println(
                    "Logout button clicked."
            );


            // -------------------------------------------------
            // STEP 5 - Verify logout
            // -------------------------------------------------

            System.out.println(
                    "Waiting for logout to complete..."
            );

            wait.until(
                    ExpectedConditions.not(
                            ExpectedConditions.urlContains(
                                    "/userDashboard"
                            )
                    )
            );

            System.out.println();
            System.out.println("LOGOUT SUCCESSFUL");


        } catch (Exception e) {

            System.out.println();
            System.out.println("=================================================");
            System.out.println("LOGOUT FAILED");
            System.out.println("=================================================");

            System.out.println(
                    "Reason: " + e.getMessage()
            );

            try {

                System.out.println(
                        "Current URL after logout failure: "
                                + driver.getCurrentUrl()
                );

                System.out.println(
                        "Current Title after logout failure: "
                                + driver.getTitle()
                );

            } catch (Exception ignored) {
            }

            captureDiagnosticScreenshot("logout_failed");

            /*
             * Do not throw here if logout is only cleanup.
             *
             * This prevents @AfterClass cleanup from making the
             * actual test result confusing.
             */
        }
    }


    // =========================================================
    // DIAGNOSTIC SCREENSHOT
    // =========================================================

    private void captureDiagnosticScreenshot(String name) {

        try {

            if (driver == null) {
                return;
            }

            File screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            String timestamp =
                    new SimpleDateFormat(
                            "yyyyMMdd_HHmmss"
                    ).format(new Date());

            String folder =
                    System.getProperty("user.dir")
                            + File.separator
                            + "screenshots";

            File directory = new File(folder);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            File destination =
                    new File(
                            folder
                                    + File.separator
                                    + name
                                    + "_"
                                    + timestamp
                                    + ".png"
                    );

            FileUtils.copyFile(
                    screenshot,
                    destination
            );

            System.out.println(
                    "Diagnostic screenshot saved: "
                            + destination.getAbsolutePath()
            );

        } catch (Exception e) {

            System.out.println(
                    "Could not capture diagnostic screenshot: "
                            + e.getMessage()
            );
        }
    }
}
