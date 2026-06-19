package org.example.scheduler;

import org.example.scheduler.client.SpaceOperationClient;
import org.example.scheduler.domain.MissionRequest;
import org.example.scheduler.domain.MissionTargetType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class SpaceOperationClientTest {
    @Test
    void postsMissionToMainService() {
        RestClient.Builder builder = RestClient.builder()
                .baseUrl("http://localhost:8080/api");
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        SpaceOperationClient client = new SpaceOperationClient(builder.build());

        server.expect(requestTo("http://localhost:8080/api/missions"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andRespond(withSuccess());

        client.executeMission(new MissionRequest(
                MissionTargetType.CONSTELLATION,
                "Geo",
                null,
                true
        ));

        server.verify();
    }
}
