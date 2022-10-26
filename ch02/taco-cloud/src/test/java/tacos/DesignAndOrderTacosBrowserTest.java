package tacos;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DesignAndOrderTacosBrowserTest {
    private static HtmlUnitDriver browser;

    @LocalServerPort
    private int port;


    @BeforeClass
    public static void setup() {
        browser = new HtmlUnitDriver();
        browser.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
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

    private String homePageUrl() {
        return "http://localhost:" + port + "/";
    }

    private void clickDesignATaco() {
        assertEquals(homePageUrl(), browser.getCurrentUrl());
        WebElement elementByCssSelector = browser.findElementByCssSelector("a[id='design']");
        elementByCssSelector.click();
    }

    private void assertDesignPageElements() {
        assertEquals(designPageUrl(), browser.getCurrentUrl());
        List<WebElement> ingredientGroups = browser.findElementsByClassName("ingredient-group");
        assertEquals(5, ingredientGroups.size());

        WebElement wrapGroup = browser.findElementByCssSelector("div.ingredient-group#wraps");
        List<WebElement> wraps = wrapGroup.findElements(By.tagName("div"));
        assertEquals(2, wraps.size());
        assertIngredient(wrapGroup, 0, "FLTO", "Flour Tortilla");
        assertIngredient(wrapGroup, 1, "COTO", "Corn Tortilla");

        WebElement proteinGroup = browser.findElementByCssSelector("div.ingredient-group#proteins");
        List<WebElement> proteins = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, proteins.size());
        assertIngredient(proteinGroup, 0, "GRBF", "Ground Beef");
        assertIngredient(proteinGroup, 1, "CARN", "Carnitas");

        WebElement cheeseGroup = browser.findElementByCssSelector("div.ingredient-group#cheeses");
        List<WebElement> cheeses = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, cheeses.size());
        assertIngredient(cheeseGroup, 0, "CHED", "Cheddar");
        assertIngredient(cheeseGroup, 1, "JACK", "Monterrey Jack");

        WebElement veggieGroup = browser.findElementByCssSelector("div.ingredient-group#veggies");
        List<WebElement> veggies = proteinGroup.findElements(By.tagName("div"));
        assertEquals(2, veggies.size());
        assertIngredient(veggieGroup, 0, "TMTO", "Diced Tomatoes");
        assertIngredient(veggieGroup, 1, "LETC", "Lettuce");

        WebElement sauceGroup = browser.findElementByCssSelector("div.ingredient-group#sauces");
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
            browser.findElementByCssSelector("input[value='" + ingredient + "']").click();
        }
        browser.findElementByCssSelector("input#name").sendKeys(name);
        browser.findElementByCssSelector("form").submit();
    }

    private void clickBuildAnotherTaco() {
        assertTrue(browser.getCurrentUrl().startsWith(orderDetailsPageUrl()));
        browser.findElementByCssSelector("a[id='another']").click();
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
        browser.findElementByCssSelector("form").submit();
    }

    private String orderDetailsPageUrl() {
        return homePageUrl() + "orders";
    }

    private void fillField(String fieldName, String value) {
        WebElement field = browser.findElementByCssSelector(fieldName);
        field.clear();
        field.sendKeys(value);
    }

}
