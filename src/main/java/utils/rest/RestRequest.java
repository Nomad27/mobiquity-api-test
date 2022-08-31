package utils.rest;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.globalConstants.ApiEndPoint;
import utils.properties.TestProperties;

import static io.restassured.RestAssured.given;
import static org.apache.log4j.LogManager.getLogger;


public class RestRequest {

  private static final Logger LOGGER = getLogger(RestRequest.class);
  private static final RequestSpecBuilder requestSpecBuilder;
  private static final RequestSpecification requestSpecification;

  static {
    EncoderConfig encoderconfig = new EncoderConfig();
    requestSpecBuilder = new RequestSpecBuilder();

    requestSpecBuilder.setBaseUri("https://" + TestProperties.getProperty("base.url"));
    requestSpecBuilder.setConfig(RestAssured.config().encoderConfig(encoderconfig.appendDefaultContentCharsetToContentTypeIfUndefined(false)));
    requestSpecification = requestSpecBuilder.build();
  }

/*  public static RestRequest init() throws AutomationException {
    return new RestRequest();
  }*/

  private static String getCreatePostPayload(String postTitle, String postBody, int userId) {
    JSONObject createPostJsonObject = new JSONObject();
    createPostJsonObject.put("title", postTitle);
    createPostJsonObject.put("body", postBody);
    createPostJsonObject.put("userId", userId);
    return createPostJsonObject.toString();
  }

  private static String getUpdatePostPayload(String key, String value) {
    JSONObject updatePostJsonObject = new JSONObject();
    updatePostJsonObject.put(key, value);
    return updatePostJsonObject.toString();
  }

  public static Response getAllUsers() {
    Response allUsers = given()
        .spec(requestSpecification)
        .get(ApiEndPoint.usersEndpoint);
    allUsers
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK);

    return allUsers;
  }

  public static Response getUser(String userName) {
    Response user = given()
        .spec(requestSpecification)
        .param("username", userName)
        .get(ApiEndPoint.usersEndpoint);
    user
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK);

    return user;
  }

  public static Response getPosts(int userId) {

    Response allPosts = given()
        .spec(requestSpecification)
        .param("userId", userId)
        .get(ApiEndPoint.postsEndpoint);
    allPosts
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK);

    return allPosts;
  }

  public static Response getCommentsOnPost(int postId) {

    Response allCommentsOnPostResponse = given()
        .spec(requestSpecification)
        .param("postId", postId)
        .get(ApiEndPoint.commentsEndpoint);
    allCommentsOnPostResponse
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK);

    return allCommentsOnPostResponse;
  }

  public static Response createPost(String postTitle, String postBody, int userId) {
    Response createPostResponse = given()
        .contentType(ContentType.JSON)
        .spec(requestSpecification)
        .body(getCreatePostPayload(postTitle, postBody, userId))
        .post(ApiEndPoint.postsEndpoint);
    createPostResponse
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED);

    return createPostResponse;
  }

  public static Response updatePost(String updateAttributeKey, String updateAttributeValue, String postId) {
    Response updatePostResponse = given()
        .contentType(ContentType.JSON)
        .spec(requestSpecification)
        .body(getUpdatePostPayload(updateAttributeKey, updateAttributeValue))
        .put(ApiEndPoint.postsEndpoint + "/" + postId);
    updatePostResponse
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK);

    return updatePostResponse;
  }

  public static Response deletePost(String postId) {
    Response deletePostResponse = given()
        .spec(requestSpecification)
        .delete(ApiEndPoint.postsEndpoint + "/" + postId);
    deletePostResponse
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK);

    return deletePostResponse;
  }

}
