package examples;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class Chapter5Test {
    @RegisterExtension
    public static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort().dynamicHttpsPort())
        .build();

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {

        given().
                when().
                get("https://0vyv4.wiremockapi.cloud/us/90210").
                then().
                assertThat().
                body("response.places.place.placeName", equalTo("Beverly Hills"));
    }

    @Test
    public void requestDeZipCode24848_checkThirdPlaceNameInResponseBody_expectKropp() {

        given().
                when().
                get("https://0vyv4.wiremockapi.cloud/de/24848").
                then().
                assertThat().
                body("response.places.place[2].placeName", equalTo("Kropp"));
    }

    @Test
    public void requestDeZipCode24848_checkLastPlaceNameInResponseBody_expectKleinBennebek() {

        given().
                when().
                get("https://0vyv4.wiremockapi.cloud/de/24848").
                then().
                assertThat().
                body("response.places.place[-1].placeName", equalTo("Klein Bennebek"));
    }

    @Test
    public void requestDeZipCode24848_checkLatitudeForSecondPlaceInResponseBody_expect5445() {

        given().
                when().
                get("https://0vyv4.wiremockapi.cloud/de/24848").
                then().
                assertThat().
                body("response.places.place[1].@latitude", equalTo("54.45"));
    }

    @Test
    public void requestDeZipCode24848_checkNumberOfPlacesWithStateAbbreviationSH_expect4() {

        given().
                when().
                get("https://0vyv4.wiremockapi.cloud/de/24848").
                then().
                assertThat().
                body("response.places.place.findAll{it.stateAbbreviation=='SH'}", hasSize(4));
    }
}