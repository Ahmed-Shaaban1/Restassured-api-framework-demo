package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);
    private final String baseUrl;
    private static final int MAX_RETRIES = 3;
    private static final long INITIAL_BACKOFF_MS = 1000;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.baseURI = baseUrl;
    }

    public Response get(String endpoint) {
        logger.info("GET request to: {}", endpoint);
        Response response = RetryHelper.executeWithRetry(() -> {
            return given()
                    .log().all()
                    .get(endpoint);
        }, MAX_RETRIES, INITIAL_BACKOFF_MS);
        logger.info("Response: {}", response.asPrettyString());
        return response;
    }

    public Response post(String endpoint, Object body) {
        logger.info("POST request to: {}", endpoint);
        logger.info("Request Body: {}", body);
        Response response = RetryHelper.executeWithRetry(() -> {
            return given()
                    .header("Content-Type", "application/json")
                    .body(body)
                    .log().all()
                    .post(endpoint);
        }, MAX_RETRIES, INITIAL_BACKOFF_MS);
        logger.info("Response: {}", response.asPrettyString());
        return response;
    }

    public Response put(String endpoint, Object body) {
        logger.info("PUT request to: {}", endpoint);
        logger.info("Request Body: {}", body);
        Response response = RetryHelper.executeWithRetry(() -> {
            return given()
                    .header("Content-Type", "application/json")
                    .body(body)
                    .log().all()
                    .put(endpoint);
        }, MAX_RETRIES, INITIAL_BACKOFF_MS);
        logger.info("Response: {}", response.asPrettyString());
        return response;
    }

    public Response delete(String endpoint) {
        logger.info("DELETE request to: {}", endpoint);
        Response response = RetryHelper.executeWithRetry(() -> {
            return given()
                    .log().all()
                    .delete(endpoint);
        }, MAX_RETRIES, INITIAL_BACKOFF_MS);
        logger.info("Response: {}", response.asPrettyString());
        return response;
    }
}
