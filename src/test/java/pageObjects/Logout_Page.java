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
    // USER PROFILE BUTTON
    // =========================================================

    /*
     * Actual HTML:
     *
     * <div class="DesktopNavBar_usernameContainer__35kIp">
     *     <button ... aria-haspopup="true">
     *         <div class="MuiAvatar-root ...">S</div>
     *     </button>
     *     <div class="DesktopNavBar_nameText__hAqgn">surya99</div>
     * </div>
     *
     * The clickable element is the BUTTON.
     */

    @FindBy(xpath =
            "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button")
    private WebElement usericon;

    // =========================================================
    // LOGOUT BUTTON
    // =========================================================

    @FindBy(xpath =
            "//li[normalize-space()='Logout']")
    private WebElement btn_logout;


    // =========================================================
    // VERIFY LOGIN
    // =========================================================

    public void verifyLoginSuccess() {

        System.out.println("==============================================");
        System.out.println("VERIFYING LOGIN SUCCESS");
        System.out.println("==============================================");

        try {

            // -------------------------------------------------
            // STEP 1: Print current URL
            // -------------------------------------------------

            System.out.println(
                    "Current URL BEFORE verification: "
                    + driver.getCurrentUrl()
            );

            // -------------------------------------------------
            // STEP 2: Print current page title
            // -------------------------------------------------

            System.out.println(
                    "Current Title BEFORE verification: "
                    + driver.getTitle()
            );

            // -------------------------------------------------
            // STEP 3: Wait until dashboard URL is available
            // -------------------------------------------------

            System.out.println("Waiting for dashboard URL...");

            wait.until(
                    ExpectedConditions.urlContains("/userDashboard")
            );

            System.out.println(
                    "Dashboard URL detected: "
                    + driver.getCurrentUrl()
            );

            // -------------------------------------------------
            // STEP 4: DIAGNOSTICS
            // -------------------------------------------------

            System.out.println("----------------------------------------------");
            System.out.println("STARTING PROFILE BUTTON DIAGNOSTICS");
            System.out.println("----------------------------------------------");

            By profileButton = By.xpath(
                    "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button"
            );

            By usernameContainer = By.xpath(
                    "//div[contains(@class,'DesktopNavBar_usernameContainer')]"
            );

            By usernameText = By.xpath(
                    "//div[contains(@class,'DesktopNavBar_nameText')]"
            );

            // Count profile buttons
            int profileButtonCount =
                    driver.findElements(profileButton).size();

            System.out.println(
                    "Profile button count: "
                    + profileButtonCount
            );

            // Count username containers
            int usernameContainerCount =
                    driver.findElements(usernameContainer).size();

            System.out.println(
                    "Username container count: "
                    + usernameContainerCount
            );

            // Count username text
            int usernameTextCount =
                    driver.findElements(usernameText).size();

            System.out.println(
                    "Username text count: "
                    + usernameTextCount
            );

            // -------------------------------------------------
            // STEP 5: Check username text if available
            // -------------------------------------------------

            if (usernameTextCount > 0) {

                try {

                    WebElement username =
                            driver.findElement(usernameText);

                    System.out.println(
                            "Username displayed: "
                            + username.getText()
                    );

                    System.out.println(
                            "Username visible: "
                            + username.isDisplayed()
                    );

                } catch (Exception e) {

                    System.out.println(
                            "Could not read username: "
                            + e.getMessage()
                    );
                }

            } else {

                System.out.println(
                        "Username element was NOT found."
                );
            }

            // -------------------------------------------------
            // STEP 6: Check profile button visibility
            // -------------------------------------------------

            if (profileButtonCount > 0) {

                try {

                    WebElement profile =
                            driver.findElement(profileButton);

                    System.out.println(
                            "Profile button visible: "
                            + profile.isDisplayed()
                    );

                    System.out.println(
                            "Profile button enabled: "
                            + profile.isEnabled()
                    );

                } catch (Exception e) {

                    System.out.println(
                            "Could not inspect profile button: "
                            + e.getMessage()
                    );
                }

            } else {

                System.out.println(
                        "PROFILE BUTTON NOT FOUND."
                );
            }

            // -------------------------------------------------
            // STEP 7: Print page source information
            // -------------------------------------------------

            System.out.println("----------------------------------------------");
            System.out.println("PAGE SOURCE CHECK");
            System.out.println("----------------------------------------------");

            String pageSource = driver.getPageSource();

            if (pageSource.contains("DesktopNavBar_usernameContainer")) {

                System.out.println(
                        "DesktopNavBar_usernameContainer EXISTS in page source."
                );

            } else {

                System.out.println(
                        "DesktopNavBar_usernameContainer DOES NOT EXIST in page source."
                );
            }

            if (pageSource.contains("surya99")) {

                System.out.println(
                        "Username 'surya99' EXISTS in page source."
                );

            } else {

                System.out.println(
                        "Username 'surya99' DOES NOT EXIST in page source."
                );
            }

            // -------------------------------------------------
            // STEP 8: Wait for profile button
            // -------------------------------------------------

            System.out.println("----------------------------------------------");
            System.out.println(
                    "Waiting for profile button to appear..."
            );

            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            profileButton
                    )
            );

            System.out.println(
                    "Profile button is PRESENT in DOM."
            );

            // -------------------------------------------------
            // STEP 9: Wait for profile button visibility
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            profileButton
                    )
            );

            System.out.println(
                    "Profile button is VISIBLE."
            );

            System.out.println("==============================================");
            System.out.println("LOGIN VERIFICATION SUCCESSFUL");
            System.out.println("==============================================");

        } catch (Exception e) {

            System.out.println("==============================================");
            System.out.println("LOGIN VERIFICATION FAILED");
            System.out.println("==============================================");

            System.out.println(
                    "Current URL after failure: "
                    + driver.getCurrentUrl()
            );

            System.out.println(
                    "Current Title after failure: "
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
    // LOGOUT
    // =========================================================

    public void logout_session() {

        System.out.println("==============================================");
        System.out.println("STARTING LOGOUT");
        System.out.println("==============================================");

        WebDriverWait logoutWait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        try {

            // -------------------------------------------------
            // STEP 1: Print current URL
            // -------------------------------------------------

            System.out.println(
                    "Current URL before logout: "
                    + driver.getCurrentUrl()
            );

            // -------------------------------------------------
            // STEP 2: Wait for dashboard
            // -------------------------------------------------

            logoutWait.until(
                    ExpectedConditions.urlContains("/userDashboard")
            );

            // -------------------------------------------------
            // STEP 3: Profile button locator
            // -------------------------------------------------

            By profileButton = By.xpath(
                    "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button"
            );

            // -------------------------------------------------
            // STEP 4: Check whether profile button exists
            // -------------------------------------------------

            int profileCount =
                    driver.findElements(profileButton).size();

            System.out.println(
                    "Profile button count during logout: "
                    + profileCount
            );

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
            // STEP 5: Wait for profile button clickable
            // -------------------------------------------------

            System.out.println(
                    "Waiting for profile button to become clickable..."
            );

            WebElement user =
                    logoutWait.until(
                            ExpectedConditions.elementToBeClickable(
                                    profileButton
                            )
                    );

            System.out.println(
                    "Profile button is clickable."
            );

            // -------------------------------------------------
            // STEP 6: Click profile button
            // -------------------------------------------------

            user.click();

            System.out.println(
                    "Profile button clicked."
            );

            // -------------------------------------------------
            // STEP 7: Logout button
            // -------------------------------------------------

            By logoutButton = By.xpath(
                    "//li[normalize-space()='Logout']"
            );

            System.out.println(
                    "Waiting for Logout button..."
            );

            WebElement logout =
                    logoutWait.until(
                            ExpectedConditions.elementToBeClickable(
                                    logoutButton
                            )
                    );

            // -------------------------------------------------
            // STEP 8: Click Logout
            // -------------------------------------------------

            logout.click();

            System.out.println(
                    "Logout button clicked."
            );

            System.out.println(
                    "Logout successful."
            );

        } catch (Exception e) {

            System.out.println(
                    "=============================================="
            );

            System.out.println(
                    "LOGOUT FAILED"
            );

            System.out.println(
                    "Reason: "
                    + e.getMessage()
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
                    "=============================================="
            );

            // Do NOT throw the exception here.
            // Logout should not make the actual test fail.
        }
    }
}
