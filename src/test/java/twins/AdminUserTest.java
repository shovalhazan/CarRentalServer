package twins;


import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import twins.boundaries.UserBoundary;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminUserTest {
	private RestTemplate restTemplate;
	private int port;

	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
	}
	
	
	@LocalServerPort // inject port number to test case
	public void setPort(int port) {
		this.port = port;
	}
	
	@Test
	public void testGetAllUsers() throws Exception{
		String URL = "http://localhost:"+port+"/twins/admin/users/{userSpace}/{userEmail}";
		UserBoundary[] userB = this.restTemplate.getForObject(URL, UserBoundary[].class, "demoSpace","shiraz@gmail.com");
		assertThat(userB)
			.isNotNull()
			.isEmpty();
	}
	
	
	

}

