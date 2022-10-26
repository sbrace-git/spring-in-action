package tacos;


import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HomePageBrowserTest {

    @LocalServerPort
    private int port;

    private static HtmlUnitDriver browser;

    @BeforeClass
    public static void setup() {
        browser = new HtmlUnitDriver();
        browser.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void teardown() {
        browser.quit();
    }

    @Test
    public void testHomePage() {
        String homePage = "http://localhost:" + port;
        browser.get(homePage);

        String titleText = browser.getTitle();
        Assert.assertEquals("Taco Cloud", titleText);

        String h1Text = browser.findElementByTagName("h1").getText();
        Assert.assertEquals("Welcome to...", h1Text);

        String imgSrc = browser.findElementByTagName("img")
                .getAttribute("src");
        Assert.assertEquals(homePage + "/images/TacoCloud.png", imgSrc);
    }
}
