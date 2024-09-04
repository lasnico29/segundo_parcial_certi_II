import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class restfull_booker_tests {

    public static Integer bookingId;
    public static client_RB client = new client_RB();

    @Test
    public void GetBookingConIdCaracteresInvalidos()
    {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        Response response = RestAssured.given().pathParam("id", "%")
                .when().get("/{id}");
        response.then().assertThat().statusCode(400);
        response.then().log().body();
    }

    @Test
    public void GetBookingShowsInfoSuccesfully()
    {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        Response response = RestAssured.given().pathParam("id", "1")
                .when().get("/{id}");
        response.then().assertThat().statusCode(200);
        response.then().log().body();
    }

    @Test
    public void CreateBookingWorksProperly() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        client.setFirstname("Nicolas");
        client.setLastname("Moscoso");
        client.setTotalprice(1230);
        client.setDepositpaid(true);
        client_RB.BookingDates bookingDates = new client_RB.BookingDates();
        bookingDates.setCheckin("2019-01-01");
        bookingDates.setCheckout("2020-02-03");
        client.setBookingdates(bookingDates);
        client.setAdditionalneeds("desayuno");

        ObjectMapper mapper = new ObjectMapper();
        String miJson = mapper.writeValueAsString(client);
        System.out.println(miJson);

        Response response = RestAssured
                .given().contentType(ContentType.JSON).body(miJson).when()
                .post();
        response.then().log().body();
        response.then().assertThat().statusCode(200);

        bookingId = response.jsonPath().getInt("bookingid");
        System.out.println("Booking ID: " + bookingId);

        response.then().assertThat().body("booking.firstname", Matchers.equalTo(client.getFirstname()))
                .body("booking.lastname", Matchers.equalTo(client.getLastname()))
                .body("booking.totalprice", Matchers.equalTo(client.getTotalprice()))
                .body("booking.depositpaid", Matchers.equalTo(client.getDepositpaid()))
                .body("booking.bookingdates.checkin", Matchers.equalTo(client.getBookingdates().getCheckin()))
                .body("booking.bookingdates.checkout", Matchers.equalTo(client.getBookingdates().getCheckout()))
                .body("booking.additionalneeds", Matchers.equalTo(client.getAdditionalneeds()));
        System.out.println(bookingId);
    }
    @Test
    public void GetBookingShowsInfoOfCreateBooking() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        client.setFirstname("Nicolas");
        client.setLastname("Moscoso");
        client.setTotalprice(1230);
        client.setDepositpaid(true);
        client_RB.BookingDates bookingDates = new client_RB.BookingDates();
        bookingDates.setCheckin("2019-01-01");
        bookingDates.setCheckout("2020-02-03");
        client.setBookingdates(bookingDates);
        client.setAdditionalneeds("desayuno");

        ObjectMapper mapper = new ObjectMapper();
        String miJson = mapper.writeValueAsString(client);
        System.out.println(miJson);

        Response response = RestAssured
                .given().contentType(ContentType.JSON).body(miJson).when()
                .post();
        response.then().log().body();
        response.then().assertThat().statusCode(200);

        bookingId = response.jsonPath().getInt("bookingid");
        System.out.println("Booking ID: " + bookingId);
        Response response2 = RestAssured.given().pathParam("id", bookingId)
                .when().get("/{id}");
        response2.then().assertThat().statusCode(200);
        response2.then().log().body();

        response2.then().assertThat().body("firstname", Matchers.equalTo("Nicolas"))
                .body("lastname", Matchers.equalTo("Moscoso"))
                .body("totalprice", Matchers.equalTo(1230))
                .body("depositpaid", Matchers.equalTo(true))
                .body("bookingdates.checkin", Matchers.equalTo("2019-01-01"))
                .body("bookingdates.checkout", Matchers.equalTo("2020-02-03"))
                .body("additionalneeds", Matchers.equalTo("desayuno"));
    }

    @Test
    public void GetBookingParamWithNumbersAndLetters()
    {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/booking";
        Response response = RestAssured.given().pathParam("id", "1hola")
                .when().get("/{id}");
        int statusCode = response.getStatusCode();
        if (statusCode != 400) {
            System.out.println("El código de estado obtenido no es 400. Código de estado recibido: " + statusCode);
        }
        response.then().assertThat().statusCode(400);
        response.then().log().body();
    }
}
