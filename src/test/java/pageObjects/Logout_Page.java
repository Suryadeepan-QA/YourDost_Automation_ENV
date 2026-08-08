package pageObjects;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Logout_Page extends BasePage {

    private WebDriverWait wait;

    public Logout_Page(WebDriver driver) {

        super(driver);

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30)
        );
    }


    // =========================================================
    // LOCATORS
    // =========================================================

    private final By profileButton = By.xpath(
            "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button"
    );

    private final By usernameElement = By.xpath(
            "//div[contains(@class,'DesktopNavBar_nameText')]"
    );

    private final By logoutButton = By.xpath(
            "//li[normalize-space()='Logout']"
    );


    // =========================================================
    // VERIFY LOGIN
    // =========================================================

    public void verifyLoginSuccess() {

        System.out.println();
        System.out.println("=================================================");
        System.out.println("STARTING LOGIN VERIFICATION");
        System.out.println("=================================================");

        try {

            // -------------------------------------------------
            // 1. CURRENT URL
            // -------------------------------------------------

            System.out.println(
                    "Current URL: "
                            + driver.getCurrentUrl()
            );


            // -------------------------------------------------
            // 2. CURRENT TITLE
            // -------------------------------------------------

            System.out.println(
                    "Current Title: "
                            + driver.getTitle()
            );


            // -------------------------------------------------
            // 3. WAIT FOR DASHBOARD URL
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


            // -------------------------------------------------
            // 4. WAIT FOR BODY
            // -------------------------------------------------

            System.out.println(
                    "Waiting for body..."
            );

            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.tagName("body")
                    )
            );

            System.out.println(
                    "Body found."
            );


            // -------------------------------------------------
            // 5. WAIT FOR APPLICATION TO RENDER
            // -------------------------------------------------

            System.out.println(
                    "Waiting 5 seconds for React application..."
            );

            try {

                Thread.sleep(5000);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
            }


            // =================================================
            // PAGE DIAGNOSTICS
            // =================================================

            System.out.println();
            System.out.println("=================================================");
            System.out.println("PAGE DIAGNOSTICS");
            System.out.println("=================================================");


            // -------------------------------------------------
            // URL
            // -------------------------------------------------

            System.out.println(
                    "URL: "
                            + driver.getCurrentUrl()
            );


            // -------------------------------------------------
            // TITLE
            // -------------------------------------------------

            System.out.println(
                    "TITLE: "
                            + driver.getTitle()
            );


            // -------------------------------------------------
            // PAGE SOURCE LENGTH
            // -------------------------------------------------

            String pageSource =
                    driver.getPageSource();

            System.out.println(
                    "PAGE SOURCE LENGTH: "
                            + pageSource.length()
            );


            // -------------------------------------------------
            // BODY HTML
            // -------------------------------------------------

            WebElement body =
                    driver.findElement(
                            By.tagName("body")
                    );

            String bodyHtml =
                    body.getAttribute("innerHTML");

            System.out.println(
                    "BODY HTML LENGTH: "
                            + bodyHtml.length()
            );


            // -------------------------------------------------
            // BODY TEXT
            // -------------------------------------------------

            String bodyText =
                    body.getText();

            System.out.println(
                    "BODY TEXT LENGTH: "
                            + bodyText.length()
            );

            System.out.println();
            System.out.println(
                    "VISIBLE BODY TEXT:"
            );

            System.out.println(
                    bodyText
            );


            // -------------------------------------------------
            // CHECK ROOT ELEMENTS
            // -------------------------------------------------

            System.out.println();
            System.out.println(
                    "ROOT ELEMENT CHECK"
            );

            checkElement(
                    By.id("root"),
                    "React root #root"
            );

            checkElement(
                    By.id("__next"),
                    "Next.js root #__next"
            );

            checkElement(
                    By.id("app"),
                    "Application #app"
            );


            // -------------------------------------------------
            // CHECK DASHBOARD
            // -------------------------------------------------

            System.out.println();
            System.out.println(
                    "DASHBOARD ELEMENT CHECK"
            );

            checkElement(
                    By.xpath(
                            "//*[contains(@class,'DesktopNavBar')]"
                    ),
                    "DesktopNavBar"
            );

            checkElement(
                    By.xpath(
                            "//div[contains(@class,'DesktopNavBar_usernameContainer')]"
                    ),
                    "Username container"
            );

            checkElement(
                    profileButton,
                    "Profile button"
            );

            checkElement(
                    usernameElement,
                    "Username element"
            );


            // -------------------------------------------------
            // CHECK USERNAME
            // -------------------------------------------------

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
            // CHECK DESKTOP NAV
            // -------------------------------------------------

            if (pageSource.contains("DesktopNavBar")) {

                System.out.println(
                        "DesktopNavBar FOUND in page source."
                );

            } else {

                System.out.println(
                        "DesktopNavBar NOT FOUND in page source."
                );
            }


            // -------------------------------------------------
            // IFRAME COUNT
            // -------------------------------------------------

            int iframeCount =
                    driver.findElements(
                            By.tagName("iframe")
                    ).size();

            System.out.println(
                    "IFRAME COUNT: "
                            + iframeCount
            );


            // -------------------------------------------------
            // COOKIES
            // -------------------------------------------------

            System.out.println();
            System.out.println(
                    "COOKIE CHECK"
            );

            Set<Cookie> cookies =
                    driver.manage().getCookies();

            System.out.println(
                    "COOKIE COUNT: "
                            + cookies.size()
            );

            for (Cookie cookie : cookies) {

                System.out.println(
                        "Cookie: "
                                + cookie.getName()
                                + " = "
                                + cookie.getValue()
                );
            }


            // -------------------------------------------------
            // SAVE SCREENSHOT
            // -------------------------------------------------

            saveScreenshot(
                    "login_verification"
            );


            // -------------------------------------------------
            // SAVE PAGE SOURCE
            // -------------------------------------------------

            savePageSource(
                    "login_verification"
            );


            // =================================================
            // FINAL CHECK
            // =================================================

            int profileCount =
                    driver.findElements(
                            profileButton
                    ).size();

            int usernameCount =
                    driver.findElements(
                            usernameElement
                    ).size();


            System.out.println();
            System.out.println(
                    "================================================="
            );

            System.out.println(
                    "PROFILE COUNT: "
                            + profileCount
            );

            System.out.println(
                    "USERNAME COUNT: "
                            + usernameCount
            );


            if (profileCount > 0 ||
                    usernameCount > 0) {

                System.out.println(
                        "LOGIN VERIFICATION SUCCESSFUL"
                );

            } else {

                System.out.println(
                        "LOGIN VERIFICATION FAILED"
                );

                System.out.println(
                        "Dashboard URL exists, but dashboard UI "
                                + "is not present in the DOM."
                );

                throw new RuntimeException(
                        "Login verification failed: "
                                + "dashboard UI not rendered."
                );
            }

        } catch (Exception e) {

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

            throw e;
        }
    }


    // =========================================================
    // CHECK ELEMENT
    // =========================================================

    private void checkElement(
            By locator,
            String name) {

        try {

            int count =
                    driver.findElements(
                            locator
                    ).size();

            System.out.println(
                    name
                            + " -> "
                            + count
                            + " element(s)"
            );

        } catch (Exception e) {

            System.out.println(
                    name
                            + " -> ERROR: "
                            + e.getMessage()
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

            System.out.println(
                    "Current URL: "
                            + driver.getCurrentUrl()
            );


            // -------------------------------------------------
            // DASHBOARD
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions.urlContains(
                            "/userDashboard"
                    )
            );


            // -------------------------------------------------
            // PROFILE BUTTON
            // -------------------------------------------------

            System.out.println(
                    "Checking profile button..."
            );

            if (driver.findElements(
                    profileButton
            ).isEmpty()) {

                System.out.println(
                        "User profile button is not present."
                );

                System.out.println(
                        "Skipping logout."
                );

                return;
            }


            // -------------------------------------------------
            // CLICK PROFILE
            // -------------------------------------------------

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


            // -------------------------------------------------
            // CLICK LOGOUT
            // -------------------------------------------------

            System.out.println(
                    "Waiting for Logout button..."
            );

            WebElement logout =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    logoutButton
                            )
                    );

            logout.click();


            // -------------------------------------------------
            // VERIFY LOGOUT
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions.not(
                            ExpectedConditions.urlContains(
                                    "/userDashboard"
                            )
                    )
            );

            System.out.println(
                    "LOGOUT SUCCESSFUL"
            );

        } catch (Exception e) {

            System.out.println(
                    "LOGOUT FAILED"
            );

            System.out.println(
                    "Reason: "
                            + e.getMessage()
            );

            saveScreenshot(
                    "logout_failed"
            );

            // Logout is cleanup, so don't throw.
        }
    }


    // =========================================================
    // SAVE SCREENSHOT
    // =========================================================

    private void saveScreenshot(
            String name) {

        try {

            File screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(
                                    OutputType.FILE
                            );

            String timestamp =
                    new SimpleDateFormat(
                            "yyyyMMdd_HHmmss"
                    ).format(
                            new Date()
                    );

            String folder =
                    System.getProperty("user.dir")
                            + File.separator
                            + "screenshots"
                            + File.separator
                            + "login";

            File directory =
                    new File(folder);

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

            java.nio.file.Files.copy(
                    screenshot.toPath(),
                    destination.toPath()
            );

            System.out.println(
                    "Screenshot saved: "
                            + destination.getAbsolutePath()
            );

        } catch (Exception e) {

            System.out.println(
                    "Screenshot failed: "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // SAVE PAGE SOURCE
    // =========================================================

    private void savePageSource(
            String name) {

        try {

            String timestamp =
                    new SimpleDateFormat(
                            "yyyyMMdd_HHmmss"
                    ).format(
                            new Date()
                    );

            String folder =
                    System.getProperty("user.dir")
                            + File.separator
                            + "screenshots"
                            + File.separator
                            + "login";

            File directory =
                    new File(folder);

            if (!directory.exists()) {

                directory.mkdirs();
            }

            File file =
                    new File(
                            folder
                                    + File.separator
                                    + name
                                    + "_"
                                    + timestamp
                                    + ".html"
                    );

            try (FileWriter writer =
                         new FileWriter(file)) {

                writer.write(
                        driver.getPageSource()
                );
            }

            System.out.println(
                    "Page source saved: "
                            + file.getAbsolutePath()
            );

        } catch (IOException e) {

            System.out.println(
                    "Page source save failed: "
                            + e.getMessage()
            );
        }
    }
}
