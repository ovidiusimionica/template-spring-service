package ro.nextworks.workshop.tbs.sayhello;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

/**
 * Hello world teller.
 */
@RefreshScope
@RestController
@Api(tags = "Health Service")
class HelloWebController {

  @Value("${hello.intro:Hello}")
  private String helloIntro;

  @Value("${swagger.schema:http}")
  private String swaggerSchema;

  @RequestMapping("/")
  @SuppressWarnings("unused")
  void handleRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.sendRedirect(response
        .encodeRedirectURL(
            appendTrailingSlash(replaceSchema(request.getRequestURL(), request.getScheme()))
                + "swagger-ui.html"));
  }

  private StringBuffer replaceSchema(StringBuffer request, String schema) {
    return request.delete(0, schema.length()).insert(0, swaggerSchema);
  }

  private StringBuffer appendTrailingSlash(StringBuffer request) {
    return request.charAt(request.length() - 1) == '/' ? request : request.append("/");
  }

  @ApiOperation("Plain text Hello Teller that optionally takes your name as parameter")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Tells Hello formally",
          examples = @Example(@ExampleProperty(value = "Hello {yourName} !")))
    }
  )
  @GetMapping(path = {"/hello", "/hello/{yourName}"},
      produces = MediaType.TEXT_PLAIN_VALUE)
  String sayHello(
      @ApiParam(name = "yourName", value = "You can input in this variable your name", example = "John Doe")
      @PathVariable(value = "yourName", required = false) String yourName) {
    String name = yourName != null && !yourName.trim().isEmpty() ? yourName : "World";

    return String.format("%s %s !", helloIntro, name);
  }
}