package org.example.scheduler.client;

import org.example.scheduler.domain.MissionRequest;

public interface SpaceOperationGateway {
    void executeMission(MissionRequest request);
}
