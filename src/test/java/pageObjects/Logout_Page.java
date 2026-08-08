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
     * Do NOT click the parent div.
     *
     * The actual clickable element is the button:
     *
     * <button ... aria-haspopup="true">
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

        System.out.println(
                "Waiting for user profile button..."
        );

        wait.until(
                ExpectedConditions.visibilityOf(usericon)
        );

        System.out.println(
                "User profile button found."
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

            System.out.println(
                    "Current URL: "
                            + driver.getCurrentUrl()
            );

            // -------------------------------------------------
            // STEP 1: WAIT FOR USER PROFILE BUTTON
            // -------------------------------------------------

            System.out.println(
                    "Waiting for user profile button..."
            );

            WebElement profileButton =
                    wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath(
                                            "//div[contains(@class,'DesktopNavBar_usernameContainer')]//button"
                                    )
                            )
                    );

            System.out.println(
                    "User profile button found."
            );


            // -------------------------------------------------
            // STEP 2: SCROLL INTO VIEW
            // -------------------------------------------------

            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({"
                                    + "block:'center',"
                                    + "inline:'center'"
                                    + "});",
                            profileButton
                    );

            System.out.println(
                    "User profile button scrolled into view."
            );


            // -------------------------------------------------
            // STEP 3: CLICK PROFILE BUTTON
            // -------------------------------------------------

            try {

                profileButton.click();

                System.out.println(
                        "User profile button clicked."
                );

            } catch (
                    org.openqa.selenium.ElementClickInterceptedException e
            ) {

                System.out.println(
                        "Normal profile click intercepted."
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
            // STEP 4: WAIT FOR LOGOUT
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
                        "Normal logout click intercepted."
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
            // STEP 6: WAIT FOR LOGOUT TO COMPLETE
            // -------------------------------------------------

            wait.until(
                    ExpectedConditions.not(
                            ExpectedConditions.urlContains(
                                    "/userDashboard/home"
                            )
                    )
            );

            System.out.println(
                    "Logout completed successfully."
            );

            System.out.println(
                    "Current URL after logout: "
                            + driver.getCurrentUrl()
            );

        } catch (Exception e) {

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
                        "Current URL: "
                                + driver.getCurrentUrl()
                );

                System.out.println(
                        "Current title: "
                                + driver.getTitle()
                );

            } catch (Exception ignored) {

                System.out.println(
                        "Unable to get current page information."
                );
            }

            /*
             * Logout is cleanup.
             *
             * Do not make the actual test fail because
             * logout failed.
             */
            System.out.println(
                    "Logout cleanup finished with warning."
            );
        }
    }
}
