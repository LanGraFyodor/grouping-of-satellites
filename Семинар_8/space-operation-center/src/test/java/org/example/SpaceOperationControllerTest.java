package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpaceOperationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void supportsPolymorphicSatelliteJsonAndMissionApi() throws Exception {
        String constellation = "API-" + UUID.randomUUID();

        mockMvc.perform(post("/api/add-satellites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "constellationName": "%s",
                                  "createConstellationIfMissing": true,
                                  "satelliteParam": {
                                    "type": "IMAGE",
                                    "name": "ДЗЗ-API",
                                    "batteryLevel": 0.9,
                                    "resolution": 0.7
                                  }
                                }
                                """.formatted(constellation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("ДЗЗ-API"))
                .andExpect(jsonPath("$.type").value("IMAGE"));

        mockMvc.perform(post("/api/missions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "targetType": "SINGLE_SATELLITE",
                                  "constellationName": "%s",
                                  "satelliteName": "ДЗЗ-API",
                                  "activateBeforeMission": true
                                }
                                """.formatted(constellation)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/overview"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        org.hamcrest.Matchers.containsString(constellation)
                ));

        mockMvc.perform(delete(
                        "/api/constellations/{constellation}/satellites/{satellite}",
                        constellation,
                        "ДЗЗ-API"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    void rejectsSingleSatelliteMissionWithoutSatelliteName() throws Exception {
        mockMvc.perform(post("/api/missions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "targetType": "SINGLE_SATELLITE",
                                  "constellationName": "Broken",
                                  "activateBeforeMission": true
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void singleSatelliteMissionDoesNotActivateOtherSatellites() throws Exception {
        String constellation = "Single-" + UUID.randomUUID();

        addSatellite(
                constellation,
                "COMMUNICATION",
                "Связь-Не-Трогать",
                """
                "bandwidth": 500.0
                """
        );
        addSatellite(
                constellation,
                "IMAGE",
                "ДЗЗ-Цель",
                """
                "resolution": 1.0
                """
        );

        mockMvc.perform(post("/api/missions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "targetType": "SINGLE_SATELLITE",
                                  "constellationName": "%s",
                                  "satelliteName": "ДЗЗ-Цель",
                                  "activateBeforeMission": true
                                }
                                """.formatted(constellation)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/constellations/{name}/status", constellation))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.satelliteCount").value(2))
                .andExpect(jsonPath("$.activeSatelliteCount").value(1));
    }

    @Test
    void exposesConstellationCreationStatusAndRechargeFacadeOperations() throws Exception {
        String constellation = "Facade-" + UUID.randomUUID();

        mockMvc.perform(post("/api/constellations/{name}", constellation))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/constellations/{name}/status", constellation))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.satelliteCount").value(0));

        mockMvc.perform(post("/api/constellations/{name}/recharge", constellation)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"amount": 0.1}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    private void addSatellite(
            String constellation,
            String type,
            String name,
            String specificParameter
    ) throws Exception {
        mockMvc.perform(post("/api/add-satellites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "constellationName": "%s",
                                  "createConstellationIfMissing": true,
                                  "satelliteParam": {
                                    "type": "%s",
                                    "name": "%s",
                                    "batteryLevel": 0.9,
                                    %s
                                  }
                                }
                                """.formatted(
                                constellation,
                                type,
                                name,
                                specificParameter
                        )))
                .andExpect(status().isCreated());
    }
}
