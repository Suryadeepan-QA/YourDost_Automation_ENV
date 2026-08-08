package pageObjects;

import java.time.Duration;
import java.util.List;

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
                "Checking whether user is logged in..."
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
             * First check whether the username/user icon
             * actually exists on the current page.
             *
             * findElements() does not throw TimeoutException.
             */
            By userIconLocator = By.xpath(
                    "//div[contains(@class,'DesktopNavBar_usernameContainer')]"
            );

            List<WebElement> userIcons =
                    driver.findElements(userIconLocator);

            if (userIcons.isEmpty()) {

                System.out.println(
                        "User icon is not present on the current page."
                );

                System.out.println(
                        "Skipping logout."
                );

                return;
            }

            /*
             * Wait for user icon to become clickable.
             */
            WebElement user =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    userIconLocator
                            )
                    );

            System.out.println(
                    "User icon found."
            );

            /*
             * Scroll into view before clicking.
             */
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({"
                                    + "block:'center',"
                                    + "inline:'center'"
                                    + "});",
                            user
                    );

            /*
             * Try normal Selenium click.
             */
            try {

                user.click();

                System.out.println(
                        "User icon clicked."
                );

            } catch (org.openqa.selenium.ElementClickInterceptedException e) {

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
            // Wait for Logout option
            // -------------------------------------------------

            By logoutLocator = By.xpath(
                    "//li[normalize-space()='Logout']"
            );

            List<WebElement> logoutElements =
                    driver.findElements(logoutLocator);

            if (logoutElements.isEmpty()) {

                System.out.println(
                        "Logout option is not present."
                );

                System.out.println(
                        "Skipping logout."
                );

                return;
            }

            WebElement logoutButton =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    logoutLocator
                            )
                    );

            System.out.println(
                    "Logout option found."
            );

            /*
             * Try normal click.
             */
            try {

                logoutButton.click();

                System.out.println(
                        "Logout clicked successfully."
                );

            } catch (org.openqa.selenium.ElementClickInterceptedException e) {

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

        } catch (Exception e) {

            /*
             * Logout is cleanup.
             *
             * Do not throw the exception here because a logout
             * problem should not hide the actual result of the
             * booking test.
             */
            System.out.println(
                    "Logout could not be completed."
            );

            System.out.println(
                    "Reason: " + e.getMessage()
            );

            System.out.println(
                    "Continuing test cleanup..."
            );
        }

        System.out.println(
                "================================================="
        );

        System.out.println(
                "LOGOUT PROCESS COMPLETED"
        );

        System.out.println(
                "================================================="
        );
    }
}

