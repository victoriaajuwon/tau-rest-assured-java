package examples;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import dataentities.Location;

import io.restassured.http.ContentType;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
public class Chapter6Test {
    @RegisterExtension
    public static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicHttpsPort())
            .build();

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {

        Location location =

                given().
                        when().
                        get("http://api.zippopotam.us/us/90210").
                        as(Location.class);

       assertEquals(
                "Beverly Hills",
                location.getPlaces().get(0).getPlaceName()
        );
    }

    @Test
    public void sendLvZipCode1050_checkStatusCode_expect200() {

        Location location = new Location();
        location.setCountry("Netherlands");

        given().
                contentType(ContentType.JSON).
                body(location).
                log().body().
                when().
                post("http://localhost:9876/lv/1050").
                then().
                assertThat().
                statusCode(200);
    }
}
