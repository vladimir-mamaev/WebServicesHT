package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.api.model.AddProduct;
import org.openqa.selenium.Cookie;
import page.CartPage;
import page.InitialHomePage;
import utils.FileUtil;
import utils.JsonUtils;
import utils.PropertiesUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static api.Specifications.*;
import static driver.SingletonDriver.getDriver;
import static helper.WebDriverWaiter.waitForPageReadyState;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class StepdefsAPI {

    public StepdefsAPI() {
    }

    private static final String BASE_URL = PropertiesUtil.get("url");
    private static final String CREATE_CART_URL = PropertiesUtil.get("url.cart.create");
    private static final String ADD_PRODUCT_URL = PropertiesUtil.get("url.add.product");
    private static final String PRODUCT_CODE = PropertiesUtil.get("product.code");
    private static final int IMPLICITLY_WAIT_TIMEOUT = Integer.parseInt(PropertiesUtil.get("implicitly.wait.timeout"));
    private static final String GUID = "guid";
    InitialHomePage initialHomePage = new InitialHomePage();
    CartPage cartPage = new CartPage();
    private Map<String, String> storage = new HashMap<>();

    @After
    public void webDriverClose() {
        getDriver().quit();

    }

    @Given("User created cart via api")
    public void createCart() {
        installSpecification(responseSpecification201(), requestSpecification(BASE_URL));
        Response response = given()
                .when()
                .post(CREATE_CART_URL)
                .then()
                .log().all()
                .extract()
                .response();
        JsonPath jsonPath = response.jsonPath();
        String guid = jsonPath.get(GUID);
        storage.put(GUID, guid);
    }


    @When("User added product {string} with quantity {int} via api with {string} payload")
    public void userAddedProductWithQuantityViaApiWithPayload(String productCode, int quantity, String fileName) throws IOException {
        AddProduct body = JsonUtils.toObject(FileUtil.generateStringFromFile(fileName), AddProduct.class);
        installSpecification(responseSpecification200(), requestSpecification(BASE_URL));
        String jsonSchema = FileUtil.generateStringFromFile("addProductResponse");
        Response response = given()
                .when()
                .body(body)
                .post(ADD_PRODUCT_URL + storage.get(GUID) + "/entries")
                .then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema(jsonSchema))
                .log().all().extract()
                .response();
        String actualProductCode = response.jsonPath().getString(PRODUCT_CODE);
        int actualQuantity = response.jsonPath().getInt("quantity");
        assertEquals(actualProductCode, productCode);
        assertEquals(actualQuantity, quantity);
    }


    @When("I open the {string}")
    public void openInitialHomePage(String pageName) {
        getDriver();
        getDriver().manage().deleteAllCookies();
        initialHomePage.openPage(pageName);
        waitForPageReadyState(IMPLICITLY_WAIT_TIMEOUT);
        initialHomePage.clickOnAcceptCookies();
    }

    @And("Authenticate to web application by adding {string}")
    public void authenticateToWebApplicationByAdding(String cookiesName) {
        getDriver().manage().addCookie(new Cookie(cookiesName, storage.get(GUID)));
        getDriver().manage().getCookieNamed(cookiesName);
        getDriver().navigate().refresh();
        initialHomePage.clickOnCartButton();
    }

    @And("Navigate to cart page and verify that it contains {string} product")
    public void verifyThatCartContainsProduct(String productName) {
        waitForPageReadyState(IMPLICITLY_WAIT_TIMEOUT);
        cartPage.clickButtonUsingJS(cartPage.getProductLink());
        waitForPageReadyState(IMPLICITLY_WAIT_TIMEOUT);
        assertTrue(getDriver().getCurrentUrl().contains(productName));
    }
}
