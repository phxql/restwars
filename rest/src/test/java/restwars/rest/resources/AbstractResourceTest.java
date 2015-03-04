//package restwars.rest.resources;
//
//import com.sun.jersey.api.client.WebResource;
//import io.dropwizard.auth.basic.BasicAuthProvider;
//import io.dropwizard.testing.junit.ResourceTestRule;
//import restwars.Data;
//import restwars.rest.DummyAuthenticator;
//
//import javax.ws.rs.core.MediaType;
//
//public abstract class AbstractResourceTest {
//    protected static ResourceTestRule.Builder createRule() {
//        return ResourceTestRule.builder()
//                .addProvider(new BasicAuthProvider<>(new DummyAuthenticator("username", "password", Data.Player1.PLAYER), "test"));
//    }
//
//    protected WebResource.Builder request(ResourceTestRule resources, String url) {
//        return resources.client().resource(url).type(MediaType.APPLICATION_JSON_TYPE);
//    }
//}
