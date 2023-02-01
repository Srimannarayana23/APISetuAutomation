package Helpers;

import Model.*;

import constants.EndPoints;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.testng.Assert.assertEquals;

public class APIHelper {
    private static final String BASE_URL="https://cdn-api.co-vin.in/api/v2/";
    public APIHelper() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.useRelaxedHTTPSValidation();
    }


    public static StateResponse getAllStates() {
        Response response = RestAssured.given().contentType(ContentType.JSON).get(EndPoints.GET_ALL_STATES).andReturn();
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Ok");
        Type type = new TypeReference<StateResponse>(){}.getType();
        StateResponse stateResponse = response.as(type);
        System.out.println(stateResponse.toString());
        return stateResponse;
    }

    public static DistrictResponse getDistricts(int state_id) {
        Response response = RestAssured.given().pathParam("state_id", String.valueOf(state_id)).contentType(ContentType.JSON).get(EndPoints.GET_DISTRICTS_FOR_STATE).andReturn();
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Ok");
        JsonPath jsnPath = response.jsonPath();
        Type type = new TypeReference<DistrictResponse>(){}.getType();
        DistrictResponse districtResponse = response.as(type);
        System.out.println(districtResponse.toString());
        return districtResponse;
    }

    public static HospitalResponse getHospitals(int district_id, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Response response = RestAssured.given().queryParam("district_id", String.valueOf(district_id)).queryParam("date", formatter.format(date)).contentType(ContentType.JSON).get(EndPoints.GET_HOSPITALS_FOR_DISTRICT).andReturn();
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Ok");
        Type type = new TypeReference<HospitalResponse>(){}.getType();
        HospitalResponse hospitalsResponse = response.as(type);
        System.out.println(hospitalsResponse.toString());
        return hospitalsResponse;
    }


}
