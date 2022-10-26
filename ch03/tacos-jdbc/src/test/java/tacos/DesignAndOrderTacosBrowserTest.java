package tacos;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DesignAndOrderTacosBrowserTest {
    private static HtmlUnitDriver browser;

    @LocalServerPort
    private int port;


    @BeforeAll
    public static void setup() {
        browser = new HtmlUnitDriver();
        browser.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void closeBrowser() {
        browser.quit();
    }

    @Test
    public void testDesignATacoPage_HappyPath() {
        browser.get(homePageUrl());
        clickDesignATaco();
        assertDesignPageElements();
        buildAndSubmitATaco("Basic Taco", "FLTO", "GRBF", "CHED", "TMTO", "SLSA");
        clickBuildAnotherTaco();
        buildAndSubmitATaco("Another Taco", "COTO", "CARN", "JACK", "LETC", "SRCR");
        fillInAndSubmitOrderForm();
        assertEquals(homePageUrl(), browser.getCurrentUrl());
    }


    @Test
    public void testDesignATacoPage_EmptyOrderInfo() {
        browser.get(homePageUrl());
        clickDesignATaco();
        assertDesignPageElements();
        buildAndSubmitATaco("Basic Taco", "FLTO", "GRBF", "CHED", "TMTO", "SLSA");
        submitEmptyOrderForm();
        fillInAndSubmitOrderForm();
        assertEquals(homePageUrl(), browser.getCurrentUrl());
    }

    @Test
    public void testDesignATacoPage_InvalidOrderInfo() {
        browser.get(homePageUrl());
        clickDesignATaco();
        assertDesignPageElements();
        buildAndSubmitATaco("Basic Taco", "FLTO", "GRBF", "CHED", "TMTO", "SLSA");
        submitInvalidOrderForm();
        fillInAndSubmitOrderForm();
        assertEquals(homePageUrl(), browser.getCurrentUrl());
    }

    private void submitInvalidOrderForm() {
        assertTrue(browser.getCurrentUrl().startsWith(orderDetailsPageUrl()));
        fillField("input#name", "I");
        fillField("input#street", "1");
        fillField("input#city", "F");
        fillField("input#state", "C");
        fillField("input#zip", "8");
        fillField("input#ccNumber", "1234432112344322");
        fillField("input#ccExpiration", "14/91");
        fillField("input#ccCVV", "1234");
        browser.findElement(By.tagName("form")).submit();

        assertEquals(orderDetailsPageUrl(), browser.getCurrentUrl());

        List<String> validationErrors = getValidationErrorTexts();
        assertEquals(4, validationErrors.size());
        assertTrue(validationErrors.contains("Please correct the problems below and resubmit."));
        assertTrue(validationErrors.contains("Not a valid credit card number"));
        assertTrue(validationErrors.contains("Must be formatted MM/YY"));
        assertTrue(validationErrors.contains("Invalid CVV"));
    }

    private void submitEmptyOrderForm() {
        assertEquals(currentOrderDetailsPageUrl(), browser.getCurrentUrl());
        browser.findElement(By.tagName("form")).submit();

        assertEquals(orderDetailsPageUrl(), browser.getCurrentUrl());

        List<String> validationErrors = getValidationErrorTexts();
        assertEquals(9, validationErrors.size());
        assertTrue(validationErrors.contains("Please correct the problems below and resubmit."));
        assertTrue(validationErrors.contains("Name is required"));
        assertTrue(validationErrors.contains("Street is required"));
        assertTrue(validationErrors.contains("City is required"));
        assertTrue(validationErrors.contains("State is required"));
        assertTrue(validationErrors.contains("Zip code is required"));
        assertTrue(validationErrors.contains("Not a valid credit card number"));
        assertTrue(validationErrors.contains("Must be formatted MM/YY"));
        assertTrue(validationErrors.contains("Invalid CVV"));
    }

    private List<String> getValidationErrorTexts() {
        return browser.findElements(By.className("validationError"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    private String currentOrderDetailsPageUrl() {
        return homePageUrl() + "orders/current";
    }

    private String homePageUrl() {
        return "http://localhost:" + port + "/";
    }

    private void clickDesignATaco() {
        assertEquals(homePageUrl(), browser.getCurrentUrl());
        WebElement elementByCssSelector = browser.findElement(By.cssSelector("a[id='design']"));
        elementByCssSelector.click();
    }

    private void assertDesignPageElements() {
        assertEquals(designPageUrl(), browser.getCurrentUrl());
        List<WebElement> ingredientGroups = browser.findElements(By.className("ingredient-group"));
        assertEquals(5, ingredientGroups.size());

        WebElement wrapGroup = browser.findElement(By.cssSelector("div.ingredient-group#wraps"));
        List<WebElement> wraps = wrapGroup.findElements(By.tagName("div"));
        assertEquals(2, wraps.size());
        assertIngredient(wrapGroup, 0, "FLTO", "Flour Tortilla");
        assertIngredient(wrapGroup, 1, "COTO", "Corn Tortilla");

        WebElement proteinGroup = browser.findElement(By.cssSelector("div.ingredient-group#proteins"));
        List<WebElement> proteins = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, proteins.size());
        assertIngredient(proteinGroup, 0, "GRBF", "Ground Beef");
        assertIngredient(proteinGroup, 1, "CARN", "Carnitas");

        WebElement cheeseGroup = browser.findElement(By.cssSelector("div.ingredient-group#cheeses"));
        List<WebElement> cheeses = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, cheeses.size());
        assertIngredient(cheeseGroup, 0, "CHED", "Cheddar");
        assertIngredient(cheeseGroup, 1, "JACK", "Monterrey Jack");

        WebElement veggieGroup = browser.findElement(By.cssSelector("div.ingredient-group#veggies"));
        List<WebElement> veggies = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, veggies.size());
        assertIngredient(veggieGroup, 0, "TMTO", "Diced Tomatoes");
        assertIngredient(veggieGroup, 1, "LETC", "Lettuce");

        WebElement sauceGroup = browser.findElement(By.cssSelector("div.ingredient-group#sauces"));
        List<WebElement> sauces = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, sauces.size());
        assertIngredient(sauceGroup, 0, "SLSA", "Salsa");
        assertIngredient(sauceGroup, 1, "SRCR", "Sour Cream");
    }

    private String designPageUrl() {
        return homePageUrl() + "design";
    }

    private void assertIngredient(WebElement ingredientGroup,
                                  int ingredientIdx, String id, String name) {
        List<WebElement> proteins = ingredientGroup.findElements(By.tagName("div"));
        WebElement ingredient = proteins.get(ingredientIdx);
        assertEquals(id,
                ingredient.findElement(By.tagName("input")).getAttribute("value"));
        assertEquals(name,
                ingredient.findElement(By.tagName("span")).getText());
    }

    private void buildAndSubmitATaco(String name, String... ingredients) {
        assertDesignPageElements();

        for (String ingredient : ingredients) {
            browser.findElement(By.cssSelector("input[value='" + ingredient + "']")).click();
        }
        browser.findElement(By.cssSelector("input#name")).sendKeys(name);
        browser.findElement(By.tagName("form")).submit();
    }

    private void clickBuildAnotherTaco() {
        assertTrue(browser.getCurrentUrl().startsWith(orderDetailsPageUrl()));
        browser.findElement(By.cssSelector("a[id='another']")).click();
    }

    private void fillInAndSubmitOrderForm() {
        assertTrue(browser.getCurrentUrl().startsWith(orderDetailsPageUrl()));
        fillField("input#name", "Ima Hungry");
        fillField("input#street", "1234 Culinary Blvd.");
        fillField("input#city", "Foodsville");
        fillField("input#state", "CO");
        fillField("input#zip", "81019");
        fillField("input#ccNumber", "4111111111111111");
        fillField("input#ccExpiration", "10/19");
        fillField("input#ccCVV", "123");
        browser.findElement(By.tagName("form")).submit();
    }

    private String orderDetailsPageUrl() {
        return homePageUrl() + "orders";
    }

    private void fillField(String fieldName, String value) {
        WebElement field = browser.findElement(By.cssSelector(fieldName));
        field.clear();
        field.sendKeys(value);
    }

}
