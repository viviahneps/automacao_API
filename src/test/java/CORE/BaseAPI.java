package API;

import Utils.PropertiesHelper;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.hamcrest.core.StringContains;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import static org.hamcrest.core.StringContains.containsString;

public class BaseAPI {
    public static RequestSpecification request = RestAssured.with();
    public static Response response;
    public Properties properties = new PropertiesHelper().getProperties();




    public void montaHeader(String email,String senha) throws UnsupportedEncodingException, JsonProcessingException, JSONException {

        ObjectMapper mapper = new ObjectMapper();
        JSONObject json = new JSONObject();
        json.put("email",email);
        json.put("password",senha);

        String jsonRequest = mapper.writeValueAsString(json);
        request = RestAssured.with();
        request.given().contentType(ContentType.JSON)
                .header("Content-Type", "application/json")
                .body(jsonRequest)
                .log().all();
    }

    public void POSTRequest( String endpoint) throws Throwable {
        response =
                request
                        .when()
                        .post(properties.getProperty("urlbase") + properties.getProperty(endpoint));
    }
    public void o_código_de_resposta_é(String chave, int codigo) throws Throwable {
        response
                .then()
                .statusCode(codigo)
                .body(containsString(chave))
                .extract().response();
    }
}
