package com.pfernand.pfonboard.pfonboard;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.pfernand.pfonboard.pfonboard.PfOnboardApplication;
import com.pfernand.pfonboard.pfonboard.core.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.embedded.RedisServer;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations="classpath:test.properties")
@SpringBootTest(classes = {PfOnboardApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PfOnboardIT {

    private static final String USER_EMAIL = "paulo@mail.com";
    private static final User.UserBuilder USER = User.builder()
            .firstName("Paulo")
            .lastName("Fernandes")
            .email(USER_EMAIL);

    private static final String USER_HOST = "/user";

    private static final int USER_PORT = 8089;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    private RedisServer redisServer;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(USER_PORT);

    @Before
    public void setUp() throws Exception {
        redisServer = new RedisServer(6378);
        redisServer.start();
    }

    @After
    public void tearDown() {
        redisServer.stop();
    }

    @Test
    public void startOnboard() {
        // Given
        // When
        ResponseEntity<User> responseEntity = cacheUser();

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualToComparingOnlyGivenFields(USER.build(), "firstName", "lastName", "email");
    }

    @Test
    public void startOnboardWithRegisteredUser() {
        // Given
        final String url = "/onboard/email";
        final String stubUrl = "/user/" + USER_EMAIL;
        final HttpEntity<User> httpEntity = new HttpEntity<>(USER.build());

        // When
        stubFor(get(urlEqualTo(stubUrl))
                .willReturn(aResponse()
                        .withStatus(200)));
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody()).contains("Email: " + USER_EMAIL + " already associated to a user.");
    }

    @Test
    public void createUser() {
        // Given
        final String url = "/onboard";
        final User user = USER.password("password").build();
        final HttpEntity<User> httpEntity = new HttpEntity<>(user);
        final String stubUrl = "/user";
        cacheUser();

        // When
        stubFor(post(urlEqualTo(stubUrl))
                .willReturn(aResponse()
                        .withStatus(200)));
        ResponseEntity<User> responseEntity = testRestTemplate.exchange(url, HttpMethod.POST, httpEntity, User.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualToComparingOnlyGivenFields(user, "firstName", "lastName", "email", "password");


    }

    private ResponseEntity<User> cacheUser() {
        final String url = "/onboard/email";
        final String stubUrl = USER_HOST + "/" + USER_EMAIL;
        final HttpEntity<User> httpEntity = new HttpEntity<>(USER.build());
        stubFor(get(urlEqualTo(stubUrl))
                .willReturn(aResponse()
                        .withStatus(404)));
        return testRestTemplate.exchange(url, HttpMethod.POST, httpEntity, User.class);
    }

}
