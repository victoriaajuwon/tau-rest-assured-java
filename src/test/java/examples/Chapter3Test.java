package examples;

import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


public class Chapter3Test {

    static Stream<Arguments> zipCodesAndPlaces() {
        return Stream.of(
                Arguments.of("us", "90210", "Beverly Hills"),
                Arguments.of("us", "12345", "Schenectady"),
                Arguments.of("ca", "B2R", "Waverley"),
                Arguments.of("nl", "1001", "Amsterdam")
        );
    }

    @ParameterizedTest
    @MethodSource("zipCodesAndPlaces")
    public void requestZipCodesFromCollection_checkPlaceNameInResponseBody_expectSpecifiedPlaceName(String countryCode, String zipCode, String expectedPlaceName) {

        given().
                pathParam("countryCode", countryCode).pathParam("zipCode", zipCode).
                when().
                get("http://zippopotam.us/{countryCode}/{zipCode}").
                then().
                assertThat().
                body("places[0].'place name'", equalTo(expectedPlaceName));
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {

        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                assertThat().
                body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    @Test
    public void requestUsZipCode12345_checkPlaceNameInResponseBody_expectSchenectady() {

        given().
                when().
                get("http://zippopotam.us/us/12345").
                then().
                assertThat().
                body("places[0].'place name'", equalTo("Schenectady"));
    }

    @Test
    public void requestCaZipCodeB2R_checkPlaceNameInResponseBody_expectWaverley() {

        given().
                when().
                get("http://zippopotam.us/ca/B2R").
                then().
                assertThat().
                body("places[0].'place name'", equalTo("Waverley"));
    }
}
