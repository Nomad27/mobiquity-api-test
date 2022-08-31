import java.util.Arrays;
import java.util.List;

import models.Comment;
import models.Post;
import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.reportManagement.extent.ExtentTestManager;
import utils.rest.RestRequest;

public class PostNegativeTests extends BaseTest {
  private Logger log = LogManager.getLogger(PostNegativeTests.class);

  @Test
  @Parameters({"invalidUserName"})
  public void verifyGetUserWithInvalidNameReturnsAnEmptyArray(String invalidUserName) {
    ExtentTestManager.startTest("verifyGetUserWithInvalidNameReturnsAnEmptyArray", "Returns empty user's response If we pass the invalid User name " + invalidUserName);
    log.info("Returns empty user's response If we pass the invalid User name " + invalidUserName);

    List<User> users = Arrays.asList(RestRequest.getUser(invalidUserName).getBody().as(User[].class));
    Assert.assertEquals("One or more users found with name " + invalidUserName, users.size(), 0);
  }

  @Test
  @Parameters({"invalidUserId"})
  public void verifyGetPostsByInvalidUserIdReturnsAnEmptyArray(String invalidUserId) {
    ExtentTestManager.startTest("verifyGetPostsByInvalidUserIdReturnsAnEmptyArray", "Returns empty post response If we pass the invalid user Id " + invalidUserId);
    log.info("Return empty post response If we pass the invalid user Id " + invalidUserId);

    int invalidUserIdInt = Integer.parseInt(invalidUserId);
    List<Post> posts = Arrays.asList(RestRequest.getPosts(invalidUserIdInt).getBody().as(Post[].class));
    Assert.assertEquals("One or more posts found by user with userId " + invalidUserIdInt, posts.size(), 0);
  }

  @Test
  @Parameters({"invalidPostId"})
  public void verifyGetPostCommentsByInvalidPostIdReturnsAnEmptyArray(String invalidPostId) {
    ExtentTestManager.startTest("verifyGetPostCommentsByInvalidPostIdReturnsAnEmptyArray", "Return empty comments response on post If we pass the invalid post Id " + invalidPostId);
    log.info("Return empty comments response on post If we pass the invalid post Id " + invalidPostId);

    int invalidPostIdInt = Integer.parseInt(invalidPostId);
    List<Comment> comments = Arrays.asList(RestRequest.getCommentsOnPost(invalidPostIdInt).getBody().as(Comment[].class));
    Assert.assertEquals("One or more comments found on the post with postId " + invalidPostId, comments.size(), 0);
  }
}
