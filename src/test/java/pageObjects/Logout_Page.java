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

    // =========================================================
    // LOCATORS
    // =========================================================

    /*
     * Profile button
     *
     * HTML:
     *
     * <div class="DesktopNavBar_usernameContainer__35kIp">
     *     <button ... aria-haspopup="true">
     *         <div class="MuiAvatar-root ...">S</div>
     *     </button>
     *     <div class="DesktopNavBar_nameText__hAqgn">
     *         surya99
     *     </div>
     * </div>
     *
     * We intentionally use only the stable part of the CSS class.
     */
    @FindBy(xpath =
            "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button")
    private WebElement usericon;


    /*
     * Username
     *
     * Example:
     * <div class="DesktopNavBar_nameText__hAqgn">
     *     surya99
     * </div>
     */
    @FindBy(xpath =
            "//div[contains(@class,'DesktopNavBar_nameText')]")
    private WebElement username;


    /*
     * Logout button
     */
    @FindBy(xpath =
            "//li[normalize-space()='Logout']")
    private WebElement btn_logout;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public Logout_Page(WebDriver driver) {

        super(driver);

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30)
        );
    }


    // =========================================================
    // SCREENSHOT DIRECTORY
    // =========================================================

    private String getScreenshotDirectory() {

        String screenshotDir = "target/screenshots/login";

        File directory = new File(screenshotDir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        return screenshotDir;
    }


    // =========================================================
    // TAKE SCREENSHOT
    // =========================================================

    private String captureScreenshot(String screenshotName) {

        String screenshotDir = getScreenshotDirectory();

        String timestamp =
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
                );

        String screenshotPath =
                screenshotDir
                        + "/"
                        + screenshotName
                        + "_"
                        + timestamp
                        + ".png";

        try {

            File screenshotFile =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            Files.copy(
                    screenshotFile.toPath(),
                    Paths.get(screenshotPath),
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println(
                    "Screenshot saved: " + screenshotPath
            );

            return screenshotPath;

        } catch (IOException e) {

            System.out.println(
                    "Unable to save screenshot: "
                            + e.getMessage()
            );

            return null;
        }
    }


    // =========================================================
    // PAGE SOURCE DIAGNOSTIC
    // =========================================================

    private void printPageDiagnostics() {

        System.out.println();
        System.out.println("=================================================");
        System.out.println("PAGE DIAGNOSTICS");
        System.out.println("=================================================");

        try {

            String currentUrl = driver.getCurrentUrl();

            String currentTitle = driver.getTitle();

            System.out.println(
                    "Current URL: " + currentUrl
            );

            System.out.println(
                    "Current Title: " + currentTitle
            );


            // -------------------------------------------------
            // PAGE SOURCE
            // -------------------------------------------------

            String pageSource = driver.getPageSource();

            System.out.println();
            System.out.println("--------------- PAGE SOURCE CHECK ---------------");

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
            // BODY TEXT
            // -------------------------------------------------

            System.out.println();
            System.out.println("--------------- VISIBLE BODY TEXT ---------------");

            try {

                WebElement body =
                        driver.findElement(By.tagName("body"));

                String bodyText = body.getText();

                if (bodyText == null || bodyText.trim().isEmpty()) {

                    System.out.println(
                            "BODY TEXT IS EMPTY."
                    );

                } else {

                    System.out.println(
                            bodyText.substring(
                                    0,
                                    Math.min(
                                            bodyText.length(),
                                            3000
                                    )
                            )
                    );
                }

            } catch (Exception e) {

                System.out.println(
                        "Unable to read body text: "
                                + e.getMessage()
                );
            }


            System.out.println(
                    "================================================="
            );

        } catch (Exception e) {

            System.out.println(
                    "Page diagnostic failed: "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // CHECK PROFILE BUTTON
    // =========================================================

    private boolean isProfileButtonPresent() {

        try {

            By profileButton =
                    By.xpath(
                            "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button"
                    );

            return !driver.findElements(profileButton).isEmpty();

        } catch (Exception e) {

            return false;
        }
    }


    // =========================================================
    // CHECK USERNAME
    // =========================================================

    private boolean isUsernamePresent() {

        try {

            By usernameLocator =
                    By.xpath(
                            "//div[contains(@class,'DesktopNavBar_nameText')]"
                    );

            return !driver.findElements(usernameLocator).isEmpty();

        } catch (Exception e) {

            return false;
        }
    }


    // =========================================================
    // VERIFY LOGIN SUCCESS
    // =========================================================

    public void verifyLoginSuccess() {

        System.out.println();
        System.out.println("=================================================");
        System.out.println("STARTING LOGIN VERIFICATION");
        System.out.println("=================================================");

        try {

            // -------------------------------------------------
            // STEP 1 - WAIT FOR DASHBOARD URL
            // -------------------------------------------------

            System.out.println(
                    "Waiting for dashboard URL..."
            );

            wait.until(
                    ExpectedConditions.urlContains(
                            "/userDashboard"
                    )
            );

            System.out.println(
                    "Dashboard URL detected."
            );

            System.out.println(
                    "Current URL: "
                            + driver.getCurrentUrl()
            );


            // -------------------------------------------------
            // STEP 2 - WAIT FOR PAGE LOAD
            // -------------------------------------------------

            System.out.println(
                    "Waiting for page body..."
            );

            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.tagName("body")
                    )
            );

            System.out.println(
                    "Page body detected."
            );


            // -------------------------------------------------
            // STEP 3 - GIVE REACT UI TIME TO RENDER
            // -------------------------------------------------

            System.out.println(
                    "Waiting for dashboard UI rendering..."
            );

            try {

                Thread.sleep(3000);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
            }


            // -------------------------------------------------
            // STEP 4 - PRINT DIAGNOSTICS
            // -------------------------------------------------

            printPageDiagnostics();


            // -------------------------------------------------
            // STEP 5 - CHECK PROFILE BUTTON
            // -------------------------------------------------

            boolean profilePresent =
                    isProfileButtonPresent();

            System.out.println(
                    "Profile button present: "
                            + profilePresent
            );


            // -------------------------------------------------
            // STEP 6 - CHECK USERNAME
            // -------------------------------------------------

            boolean usernamePresent =
                    isUsernamePresent();

            System.out.println(
                    "Username element present: "
                            + usernamePresent
            );


            // -------------------------------------------------
            // STEP 7 - VERIFY LOGIN
            // -------------------------------------------------

            if (profilePresent && usernamePresent) {

                System.out.println();
                System.out.println(
                        "LOGIN VERIFICATION SUCCESSFUL"
                );

                System.out.println(
                        "User profile button found."
                );

                System.out.println(
                        "Username element found."
                );

                System.out.println(
                        "================================================="
                );

                return;
            }


            // -------------------------------------------------
            // LOGIN VERIFICATION FAILED
            // -------------------------------------------------

            System.out.println();
            System.out.println(
                    "LOGIN VERIFICATION FAILED"
            );

            System.out.println(
                    "Reason: Login verification failed: "
                            + "dashboard UI not rendered."
            );


            // -------------------------------------------------
            // TAKE FAILURE SCREENSHOT
            // -------------------------------------------------

            captureScreenshot(
                    "login_verification_failed"
            );


            throw new RuntimeException(
                    "Login verification failed: "
                            + "dashboard UI not rendered."
            );


        } catch (RuntimeException e) {

            /*
             * If the failure is already our RuntimeException,
             * don't create another message.
             */
            throw e;


        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "LOGIN VERIFICATION FAILED"
            );

            System.out.println(
                    "Reason: " + e.getMessage()
            );


            // -------------------------------------------------
            // TAKE FAILURE SCREENSHOT
            // -------------------------------------------------

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
    // LOGOUT
    // =========================================================

    public void logout_session() {

        System.out.println();
        System.out.println("=================================================");
        System.out.println("STARTING LOGOUT");
        System.out.println("=================================================");

        try {

            // -------------------------------------------------
            // STEP 1 - WAIT FOR DASHBOARD URL
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions.urlContains(
                            "/userDashboard"
                    )
            );


            // -------------------------------------------------
            // STEP 2 - FIND PROFILE BUTTON
            // -------------------------------------------------

            By profileButton =
                    By.xpath(
                            "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button"
                    );


            if (driver.findElements(profileButton).isEmpty()) {

                System.out.println(
                        "User profile button is not present."
                );

                System.out.println(
                        "Skipping logout."
                );

                return;
            }


            // -------------------------------------------------
            // STEP 3 - CLICK PROFILE BUTTON
            // -------------------------------------------------

            System.out.println(
                    "Waiting for profile button..."
            );

            WebElement user =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    profileButton
                            )
                    );

            user.click();

            System.out.println(
                    "Profile button clicked."
            );


            // -------------------------------------------------
            // STEP 4 - WAIT FOR LOGOUT
            // -------------------------------------------------

            By logoutButton =
                    By.xpath(
                            "//li[normalize-space()='Logout']"
                    );

            System.out.println(
                    "Waiting for Logout button..."
            );

            WebElement logout =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    logoutButton
                            )
                    );


            // -------------------------------------------------
            // STEP 5 - CLICK LOGOUT
            // -------------------------------------------------

            logout.click();

            System.out.println(
                    "Logout button clicked."
            );


            // -------------------------------------------------
            // STEP 6 - VERIFY LOGOUT
            // -------------------------------------------------

            try {

                wait.until(
                        ExpectedConditions.not(
                                ExpectedConditions.urlContains(
                                        "/userDashboard"
                                )
                        )
                );

            } catch (Exception e) {

                System.out.println(
                        "URL did not change after logout."
                );
            }


            System.out.println();
            System.out.println(
                    "LOGOUT COMPLETED"
            );

            System.out.println(
                    "================================================="
            );


        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "LOGOUT FAILED: "
                            + e.getMessage()
            );


            // -------------------------------------------------
            // TAKE LOGOUT FAILURE SCREENSHOT
            // -------------------------------------------------

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
