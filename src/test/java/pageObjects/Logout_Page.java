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
    // DESKTOP PROFILE BUTTON
    // =========================================================

    @FindBy(xpath =
            "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button")
    private WebElement usericon;


    // =========================================================
    // DESKTOP USERNAME
    // =========================================================

    @FindBy(xpath =
            "//div[contains(@class,'DesktopNavBar_nameText')]")
    private WebElement username;


    // =========================================================
    // MOBILE / RESPONSIVE PROFILE
    // =========================================================

    /*
     * In GitHub Actions the application is loading the
     * responsive layout.
     *
     * Screenshot shows:
     *
     * Home       Profile
     *
     * Therefore we check for visible "Profile" navigation.
     */
    private By mobileProfile =
            By.xpath(
                    "//*[normalize-space()='Profile']"
            );


    // =========================================================
    // LOGOUT BUTTON
    // =========================================================

    private By logoutButton =
            By.xpath(
                    "//li[normalize-space()='Logout']"
            );


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

        String screenshotDir =
                "target/screenshots/login";

        File directory =
                new File(screenshotDir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        return screenshotDir;
    }


    // =========================================================
    // TAKE SCREENSHOT
    // =========================================================

    private String captureScreenshot(
            String screenshotName) {

        String screenshotDir =
                getScreenshotDirectory();

        String timestamp =
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern(
                                "yyyyMMdd_HHmmss"
                        )
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
    // PAGE DIAGNOSTICS
    // =========================================================

    private void printPageDiagnostics() {

        System.out.println();
        System.out.println(
                "================================================="
        );
        System.out.println(
                "PAGE DIAGNOSTICS"
        );
        System.out.println(
                "================================================="
        );

        try {

            System.out.println(
                    "Current URL: "
                            + driver.getCurrentUrl()
            );

            System.out.println(
                    "Current Title: "
                            + driver.getTitle()
            );


            // -------------------------------------------------
            // PAGE SOURCE
            // -------------------------------------------------

            String pageSource =
                    driver.getPageSource();

            System.out.println();
            System.out.println(
                    "--------------- PAGE SOURCE CHECK ---------------"
            );


            if (pageSource.contains(
                    "DesktopNavBar")) {

                System.out.println(
                        "DesktopNavBar FOUND in page source."
                );

            } else {

                System.out.println(
                        "DesktopNavBar NOT FOUND in page source."
                );
            }


            if (pageSource.contains(
                    "surya99")) {

                System.out.println(
                        "Username 'surya99' FOUND in page source."
                );

            } else {

                System.out.println(
                        "Username 'surya99' NOT FOUND in page source."
                );
            }


            // -------------------------------------------------
            // MOBILE PROFILE CHECK
            // -------------------------------------------------

            if (pageSource.contains(
                    "Profile")) {

                System.out.println(
                        "'Profile' text FOUND in page source."
                );

            } else {

                System.out.println(
                        "'Profile' text NOT FOUND in page source."
                );
            }


            // -------------------------------------------------
            // BODY TEXT
            // -------------------------------------------------

            System.out.println();
            System.out.println(
                    "--------------- VISIBLE BODY TEXT ---------------"
            );

            try {

                WebElement body =
                        driver.findElement(
                                By.tagName("body")
                        );

                String bodyText =
                        body.getText();

                if (bodyText == null
                        || bodyText.trim().isEmpty()) {

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
    // VERIFY DESKTOP PROFILE
    // =========================================================

    private boolean isDesktopProfilePresent() {

        try {

            By profileButton =
                    By.xpath(
                            "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button"
                    );

            return !driver
                    .findElements(profileButton)
                    .isEmpty();

        } catch (Exception e) {

            return false;
        }
    }


    // =========================================================
    // VERIFY DESKTOP USERNAME
    // =========================================================

    private boolean isDesktopUsernamePresent() {

        try {

            By usernameLocator =
                    By.xpath(
                            "//div[contains(@class,'DesktopNavBar_nameText')]"
                    );

            return !driver
                    .findElements(usernameLocator)
                    .isEmpty();

        } catch (Exception e) {

            return false;
        }
    }


    // =========================================================
    // VERIFY MOBILE PROFILE
    // =========================================================

    private boolean isMobileProfilePresent() {

        try {

            return !driver
                    .findElements(mobileProfile)
                    .isEmpty();

        } catch (Exception e) {

            return false;
        }
    }


    // =========================================================
    // VERIFY LOGIN SUCCESS
    // =========================================================

    public void verifyLoginSuccess() {

        System.out.println();
        System.out.println(
                "================================================="
        );
        System.out.println(
                "STARTING LOGIN VERIFICATION"
        );
        System.out.println(
                "================================================="
        );


        try {

            // =================================================
            // STEP 1
            // WAIT FOR DASHBOARD URL
            // =================================================

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


            // =================================================
            // STEP 2
            // WAIT FOR BODY
            // =================================================

            System.out.println(
                    "Waiting for page body..."
            );

            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.tagName("body")
                    )
            );


            // =================================================
            // STEP 3
            // WAIT FOR DASHBOARD UI
            // =================================================

            System.out.println(
                    "Waiting for dashboard UI..."
            );

            /*
             * IMPORTANT:
             *
             * We don't wait only for DesktopNavBar.
             *
             * GitHub Actions is showing responsive UI,
             * so we wait for either:
             *
             * Desktop profile
             * OR
             * Desktop username
             * OR
             * Mobile Profile
             */

            boolean dashboardLoaded =
                    wait.until(driver -> {

                        boolean desktopProfile =
                                isDesktopProfilePresent();

                        boolean desktopUsername =
                                isDesktopUsernamePresent();

                        boolean mobileProfile =
                                isMobileProfilePresent();

                        System.out.println(
                                "Desktop profile: "
                                        + desktopProfile
                                        + " | Desktop username: "
                                        + desktopUsername
                                        + " | Mobile profile: "
                                        + mobileProfile
                        );

                        return desktopProfile
                                || desktopUsername
                                || mobileProfile;
                    });


            // =================================================
            // STEP 4
            // VERIFY RESULT
            // =================================================

            if (dashboardLoaded) {

                System.out.println();
                System.out.println(
                        "================================================="
                );
                System.out.println(
                        "LOGIN VERIFICATION SUCCESSFUL"
                );
                System.out.println(
                        "================================================="
                );

                if (isDesktopProfilePresent()) {

                    System.out.println(
                            "Desktop profile button found."
                    );
                }

                if (isDesktopUsernamePresent()) {

                    System.out.println(
                            "Desktop username found."
                    );
                }

                if (isMobileProfilePresent()) {

                    System.out.println(
                            "Responsive/mobile Profile navigation found."
                    );
                }

                return;
            }


        } catch (Exception e) {

            // =================================================
            // FAILURE DIAGNOSTICS
            // =================================================

            System.out.println();
            System.out.println(
                    "================================================="
            );
            System.out.println(
                    "LOGIN VERIFICATION FAILED"
            );
            System.out.println(
                    "================================================="
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


            // -------------------------------------------------
            // PRINT PAGE DIAGNOSTICS
            // -------------------------------------------------

            printPageDiagnostics();


            // -------------------------------------------------
            // FAILURE SCREENSHOT
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
        System.out.println(
                "================================================="
        );
        System.out.println(
                "STARTING LOGOUT"
        );
        System.out.println(
                "================================================="
        );

        try {

            wait.until(
                    ExpectedConditions.urlContains(
                            "/userDashboard"
                    )
            );


            // =================================================
            // CHECK DESKTOP PROFILE
            // =================================================

            if (isDesktopProfilePresent()) {

                System.out.println(
                        "Desktop profile detected."
                );

                By profileButton =
                        By.xpath(
                                "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button"
                        );

                WebElement user =
                        wait.until(
                                ExpectedConditions.elementToBeClickable(
                                        profileButton
                                )
                        );

                user.click();

                System.out.println(
                        "Desktop profile clicked."
                );


                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                logoutButton
                        )
                ).click();

                System.out.println(
                        "Logout successful."
                );

                return;
            }


            // =================================================
            // MOBILE / RESPONSIVE PROFILE
            // =================================================

            if (isMobileProfilePresent()) {

                System.out.println(
                        "Responsive/mobile Profile detected."
                );

                /*
                 * Click Profile first.
                 *
                 * The exact logout UI may appear after
                 * opening the Profile page/menu.
                 */

                WebElement profile =
                        wait.until(
                                ExpectedConditions.elementToBeClickable(
                                        mobileProfile
                                )
                        );

                profile.click();

                System.out.println(
                        "Mobile Profile clicked."
                );


                // -------------------------------------------------
                // WAIT FOR LOGOUT
                // -------------------------------------------------

                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                logoutButton
                        )
                ).click();

                System.out.println(
                        "Logout successful."
                );

                return;
            }


            // =================================================
            // PROFILE NOT FOUND
            // =================================================

            System.out.println(
                    "Profile button not found."
            );

            captureScreenshot(
                    "logout_profile_not_found"
            );


        } catch (Exception e) {

            System.out.println(
                    "Logout failed: "
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