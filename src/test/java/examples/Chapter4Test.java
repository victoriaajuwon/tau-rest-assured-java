package examples;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Chapter4Test {

    private static RequestSpecification requestSpec;
    @BeforeAll
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://api.zippopotam.us").
                build();
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills_withRequestSpec() {

        given().
                spec(requestSpec).
                when().
                get("us/90210").
                then().assertThat().statusCode(200);
    }


    //response

    private static ResponseSpecification responseSpec;

    @BeforeAll
    public static void createResponseSpecification() {

        responseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills_withResponseSpec() {

        given().
                spec(requestSpec).
                when().
                get("http://zippopotam.us/us/90210").
                then().
                spec(responseSpec).
                and().
                assertThat().
                body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    @Test
    public void requestUsZipCode90210_extractPlaceNameFromResponseBody_assertEqualToBeverlyHills() {

        String placeName =

                given().
                        spec(requestSpec).
                        when().
                        get("http://zippopotam.us/us/90210").
                        then().
                        extract().
                        path("places[0].'place name'");

        assertEquals("Beverly Hills", placeName);
    }

}
