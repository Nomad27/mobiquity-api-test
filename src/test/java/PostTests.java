import java.util.Arrays;
import java.util.List;

import io.restassured.response.Response;
import models.Comment;
import models.Post;
import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.reportManagement.extent.ExtentTestManager;
import utils.rest.RestRequest;

import static org.hamcrest.Matchers.equalTo;

public class PostTests extends BaseTest {

  private Response allCommentsOnUserPostsResponse;
  private int userId;
  private List<Post> allPostsByUser;
  private List<Comment> allCommentsOnUserPosts;
  private Logger log = LogManager.getLogger(PostTests.class);

  @Test
  public void verifyGetAllUsers() {

    ExtentTestManager.startTest("verifyGetAllUsers", "Verify all the user's details");
    log.info("Verify all the user's details");
    RestRequest.getAllUsers()
        .then()
        .assertThat()
        .body("size()", Matchers.greaterThan(0));
  }

  @Test
  @Parameters({"userName"})
  public void verifyGetUser(String userName) {

    ExtentTestManager.startTest("verifyGetUser", "Verify the details for the user " + userName);
    log.info("Get the details of user " + userName);

    final Response restResponse = RestRequest.getUser(userName);
    List<User> userResponse = Arrays.asList(restResponse.getBody().as(User[].class));

    if (userResponse.size() > 0) {
      userId = userResponse.get(0).getId();
      log.info("userId for the username " + userName + " = " + userId);
    } else {
      Assert.fail("No user found with username = " + userName);
    }
  }

  @Test(dependsOnMethods = "verifyGetUser")
  public void verifyGetPostsByUserId() {

    ExtentTestManager.startTest("verifyGetPostsByUserId", "Verify all the post for the user Id " + userId);
    log.info("Verify all the post for the user Id " + userId);

    final Response restResponse = RestRequest.getPosts(userId);
    allPostsByUser = Arrays.asList(restResponse.getBody().as(Post[].class));
    allPostsByUser.forEach(post -> Assert.assertEquals(post.getUserId(), userId));
  }

  @Test(dependsOnMethods = "verifyGetPostsByUserId")
  public void verifyPostComments() {

    ExtentTestManager.startTest("verifyPostComments", "Verify the comments on post for the user Id " + userId);
    log.info("Verify the comments on post for the user Id " + userId);

    allPostsByUser.forEach(post -> {
      int postId = post.getId();
      allCommentsOnUserPostsResponse = RestRequest.getCommentsOnPost(postId);
      allCommentsOnUserPosts = Arrays.asList(allCommentsOnUserPostsResponse.getBody().as(Comment[].class));
      String emailRegex = "^(.+)@(.+)$";
      allCommentsOnUserPosts.forEach(comment -> {
        Assert.assertTrue(comment.getEmail().matches(emailRegex));
      });
    });
  }

  @Test(dependsOnMethods = {"verifyGetUser"})
  @Parameters({"postTitle", "postBody"})
  public void verifyCreatePost(String postTitle, String postBody) {

    ExtentTestManager.startTest("verifyCreatePost", "Verify the created post for the user Id " + userId);
    log.info("Verify the created post for the user Id " + userId);

    final Response restResponse = RestRequest.createPost(postTitle, postBody, userId);
    restResponse.then()
        .assertThat()
        .body("title", equalTo(postTitle))
        .body("body", equalTo(postBody))
        .body("userId", equalTo(userId));
  }

  @Test()
  @Parameters({"updatedPostTitle", "fakePostId"})
  public void verifyUpdatePost(String updatedPostTitle, String postId) {

    ExtentTestManager.startTest("verifyUpdatePost", "Verify the updated post for the user Id " + userId);
    log.info("Verify the updated post for the user Id " + userId);

    RestRequest.updatePost("title", updatedPostTitle, postId)
        .then()
        .assertThat()
        .body("title", equalTo(updatedPostTitle));
  }

  @Test(dependsOnMethods = {"verifyGetUser"})
  @Parameters({"fakePostId"})
  public void verifyDeletePost(String postId) {

    ExtentTestManager.startTest("verifyDeletePost", "Verify the post is deleted for the user Id " + userId);
    log.info("Verify the post is deleted for the user Id " + userId);

    RestRequest.deletePost(postId)
        .then()
        .assertThat()
        .body(equalTo("{}"));
  }
}

