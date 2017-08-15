package ywcai.ls.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.security.access.annotation.Secured;





@Path("restful")
@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
public class JerseyController {
	
	@GET
	@Path("/test")
	@Secured("ROLE_USER")
	public Response test()
	{

		return Response.ok("test").build();
	}
}
