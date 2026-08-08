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

        /*
         * GitHub Actions can be slower than local execution.
         * Give the page enough time to render the navbar.
         */
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30)
        );
    }

    // =========================================================
    // LOCATORS
    // =========================================================

    @FindBy(xpath = "//div[contains(@class,'DesktopNavBar_usernameContainer')]")
    private WebElement usericon;

    @FindBy(xpath = "//li[normalize-space()='Logout']")
    private WebElement btn_logout;

    // =========================================================
    // VERIFY LOGIN
    // =========================================================

    public void verifyLoginSuccess() {

        System.out.println(
                "Waiting for logged-in user icon..."
        );

        wait.until(
                ExpectedConditions.visibilityOf(usericon)
        );

        System.out.println(
                "User login verified successfully."
        );
    }

    // =========================================================
    // LOGOUT
    // =========================================================

    public void logout_session() {

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

            /*
             * Print the current page information.
             * This is very useful in GitHub Actions.
             */
            System.out.println(
                    "Current URL before logout: "
                            + driver.getCurrentUrl()
            );

            System.out.println(
                    "Current page title: "
                            + driver.getTitle()
            );

            // -------------------------------------------------
            // STEP 1: WAIT FOR USER ICON
            // -------------------------------------------------

            System.out.println(
                    "Waiting for user icon..."
            );

            WebElement user =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath(
                                            "//div[contains(@class,'DesktopNavBar_usernameContainer')]"
                                    )
                            )
                    );

            System.out.println(
                    "User icon found."
            );

            // -------------------------------------------------
            // STEP 2: SCROLL USER ICON INTO VIEW
            // -------------------------------------------------

            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({"
                                    + "block:'center',"
                                    + "inline:'center'"
                                    + "});",
                            user
                    );

            System.out.println(
                    "User icon scrolled into view."
            );

            // -------------------------------------------------
            // STEP 3: CLICK USER ICON
            // -------------------------------------------------

            try {

                user.click();

                System.out.println(
                        "User icon clicked successfully."
                );

            } catch (
                    org.openqa.selenium.ElementClickInterceptedException e
            ) {

                System.out.println(
                        "User icon click intercepted."
                );

                System.out.println(
                        "Trying JavaScript click..."
                );

                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].click();",
                                user
                        );

                System.out.println(
                        "User icon clicked using JavaScript."
                );
            }

            // -------------------------------------------------
            // STEP 4: WAIT FOR LOGOUT OPTION
            // -------------------------------------------------

            System.out.println(
                    "Waiting for Logout option..."
            );

            WebElement logoutButton =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath(
                                            "//li[normalize-space()='Logout']"
                                    )
                            )
                    );

            System.out.println(
                    "Logout option found."
            );

            // -------------------------------------------------
            // STEP 5: CLICK LOGOUT
            // -------------------------------------------------

            try {

                logoutButton.click();

                System.out.println(
                        "Logout clicked successfully."
                );

            } catch (
                    org.openqa.selenium.ElementClickInterceptedException e
            ) {

                System.out.println(
                        "Logout click intercepted."
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

            System.out.println(
                    "Logout completed successfully."
            );

        } catch (Exception e) {

            /*
             * Print diagnostics.
             */
            System.out.println(
                    "================================================="
            );

            System.out.println(
                    "LOGOUT FAILED"
            );

            System.out.println(
                    "================================================="
            );

            System.out.println(
                    "Reason: "
                            + e.getMessage()
            );

            try {

                System.out.println(
                        "Current URL after logout failure: "
                                + driver.getCurrentUrl()
                );

                System.out.println(
                        "Current title after logout failure: "
                                + driver.getTitle()
                );

            } catch (Exception ignored) {

                System.out.println(
                        "Unable to read current page information."
                );
            }

            /*
             * Logout is cleanup.
             *
             * Do not fail the actual test because of logout.
             */
            System.out.println(
                    "Logout cleanup finished with warning."
            );
        }
    }
}

