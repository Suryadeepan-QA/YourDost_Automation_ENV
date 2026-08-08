package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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
    // USERNAME - USED TO VERIFY LOGIN SUCCESS
    // =========================================================

    /*
     * HTML:
     *
     * <div class="DesktopNavBar_nameText__hAqgn">
     *     surya99
     * </div>
     *
     * We intentionally use contains() because the
     * DesktopNavBar_nameText class suffix can change.
     */
    @FindBy(xpath = "//div[contains(@class,'DesktopNavBar_nameText')]")
    private WebElement username;


    // =========================================================
    // PROFILE BUTTON
    // =========================================================

    /*
     * Actual clickable element from your HTML:
     *
     * <div class="DesktopNavBar_usernameContainer__35kIp">
     *
     *     <button ... aria-haspopup="true">
     *         <div class="MuiAvatar-root ...">S</div>
     *     </button>
     *
     *     <div class="DesktopNavBar_nameText__hAqgn">
     *         surya99
     *     </div>
     *
     * </div>
     *
     * Therefore we click the BUTTON, not the parent div.
     */
    private By profileButton = By.xpath(
            "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button"
    );


    // =========================================================
    // LOGOUT BUTTON
    // =========================================================

    @FindBy(xpath = "//li[normalize-space()='Logout']")
    private WebElement btn_logout;


    // =========================================================
    // VERIFY LOGIN SUCCESS
    // =========================================================

    public void verifyLoginSuccess() {

        System.out.println();
        System.out.println("==============================================");
        System.out.println("STARTING LOGIN VERIFICATION");
        System.out.println("==============================================");

        try {

            // -------------------------------------------------
            // 1. Print current URL
            // -------------------------------------------------

            System.out.println("Current URL: " + driver.getCurrentUrl());

            // -------------------------------------------------
            // 2. Print current title
            // -------------------------------------------------

            System.out.println("Current Title: " + driver.getTitle());

            // -------------------------------------------------
            // 3. Wait for dashboard URL
            // -------------------------------------------------

            System.out.println("Waiting for dashboard URL...");

            wait.until(
                    ExpectedConditions.urlContains("/userDashboard")
            );

            System.out.println("Dashboard URL detected.");

            // -------------------------------------------------
            // 4. Diagnostics
            // -------------------------------------------------

            System.out.println();
            System.out.println("STARTING PROFILE BUTTON DIAGNOSTICS");

            By usernameLocator = By.xpath(
                    "//div[contains(@class,'DesktopNavBar_nameText')]"
            );

            By profileContainerLocator = By.xpath(
                    "//div[contains(@class,'DesktopNavBar_usernameContainer')]"
            );

            By profileButtonLocator = By.xpath(
                    "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button"
            );

            System.out.println(
                    "Username elements found: "
                    + driver.findElements(usernameLocator).size()
            );

            System.out.println(
                    "Profile containers found: "
                    + driver.findElements(profileContainerLocator).size()
            );

            System.out.println(
                    "Profile buttons found: "
                    + driver.findElements(profileButtonLocator).size()
            );

            // -------------------------------------------------
            // 5. Verify username
            // -------------------------------------------------

            System.out.println();
            System.out.println("Waiting for username...");

            wait.until(
                    ExpectedConditions.visibilityOf(username)
            );

            System.out.println(
                    "Username found: " + username.getText()
            );

            // -------------------------------------------------
            // 6. Verify profile button
            // -------------------------------------------------

            System.out.println();
            System.out.println("Waiting for profile button...");

            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            profileButtonLocator
                    )
            );

            System.out.println("Profile button found.");

            System.out.println();
            System.out.println("LOGIN VERIFICATION SUCCESSFUL");

        } catch (Exception e) {

            System.out.println();
            System.out.println("LOGIN VERIFICATION FAILED");

            System.out.println(
                    "Current URL after failure: "
                    + driver.getCurrentUrl()
            );

            System.out.println(
                    "Current Title after failure: "
                    + driver.getTitle()
            );

            System.out.println(
                    "Reason: " + e.getMessage()
            );

            // -------------------------------------------------
            // PAGE SOURCE DIAGNOSTICS
            // -------------------------------------------------

            try {

                String pageSource = driver.getPageSource();

                System.out.println();
                System.out.println("==============================================");
                System.out.println("PAGE SOURCE CHECK");
                System.out.println("==============================================");

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

            } catch (Exception sourceException) {

                System.out.println(
                        "Unable to read page source: "
                        + sourceException.getMessage()
                );
            }

            throw e;
        }
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    public void logout_session() {

        System.out.println();
        System.out.println("==============================================");
        System.out.println("STARTING LOGOUT");
        System.out.println("==============================================");

        try {

            // -------------------------------------------------
            // 1. Make sure we are on dashboard
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions.urlContains("/userDashboard")
            );

            System.out.println(
                    "Dashboard page confirmed."
            );

            // -------------------------------------------------
            // 2. Profile button locator
            // -------------------------------------------------

            By profileButtonLocator = By.xpath(
                    "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button"
            );

            // -------------------------------------------------
            // 3. Diagnostics
            // -------------------------------------------------

            int profileCount =
                    driver.findElements(profileButtonLocator).size();

            System.out.println(
                    "Profile button count: " + profileCount
            );

            // -------------------------------------------------
            // 4. If profile button does not exist
            // -------------------------------------------------

            if (profileCount == 0) {

                System.out.println(
                        "User profile button is not present."
                );

                System.out.println(
                        "Skipping logout."
                );

                return;
            }

            // -------------------------------------------------
            // 5. Wait for profile button
            // -------------------------------------------------

            System.out.println(
                    "Waiting for profile button to become clickable..."
            );

            WebElement profileButton =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    profileButtonLocator
                            )
                    );

            System.out.println(
                    "Profile button is clickable."
            );

            // -------------------------------------------------
            // 6. Scroll profile button into view
            // -------------------------------------------------

            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({block:'center'});",
                            profileButton
                    );

            // -------------------------------------------------
            // 7. Click profile button
            // -------------------------------------------------

            try {

                System.out.println(
                        "Clicking profile button..."
                );

                profileButton.click();

                System.out.println(
                        "Profile button clicked normally."
                );

            } catch (Exception clickException) {

                System.out.println(
                        "Normal profile click failed."
                );

                System.out.println(
                        "Trying JavaScript click..."
                );

                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].click();",
                                profileButton
                        );

                System.out.println(
                        "Profile button clicked using JavaScript."
                );
            }

            // -------------------------------------------------
            // 8. Wait for Logout button
            // -------------------------------------------------

            System.out.println(
                    "Waiting for Logout button..."
            );

            By logoutButtonLocator = By.xpath(
                    "//li[normalize-space()='Logout']"
            );

            WebElement logoutButton =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    logoutButtonLocator
                            )
                    );

            System.out.println(
                    "Logout button found."
            );

            // -------------------------------------------------
            // 9. Click Logout
            // -------------------------------------------------

            try {

                logoutButton.click();

                System.out.println(
                        "Logout clicked normally."
                );

            } catch (Exception logoutClickException) {

                System.out.println(
                        "Normal logout click failed."
                );

                System.out.println(
                        "Trying JavaScript click..."
                );

                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].click();",
                                logoutButton
                        );

                System.out.println(
                        "Logout clicked using JavaScript."
                );
            }

            // -------------------------------------------------
            // 10. Wait for logout navigation
            // -------------------------------------------------

            try {

                wait.until(
                        ExpectedConditions.not(
                                ExpectedConditions.urlContains(
                                        "/userDashboard"
                                )
                        )
                );

                System.out.println(
                        "Logout successful."
                );

            } catch (Exception logoutNavigationException) {

                System.out.println(
                        "Logout click completed, but dashboard URL "
                        + "is still present."
                );

                System.out.println(
                        "Current URL: "
                        + driver.getCurrentUrl()
                );
            }

        } catch (Exception e) {

            System.out.println();
            System.out.println("LOGOUT FAILED");

            System.out.println(
                    "Reason: " + e.getMessage()
            );

            System.out.println(
                    "Current URL after logout failure: "
                    + driver.getCurrentUrl()
            );

            System.out.println(
                    "Current title after logout failure: "
                    + driver.getTitle()
            );

            System.out.println(
                    "Logout cleanup finished with warning."
            );

            // Do NOT throw the exception here.
            // This allows @AfterClass to close the browser.
        }
    }
}
