package org.example;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Space Operation Center")
public class SpaceOperationController {
    private final SpaceOperationCenterService operationCenter;

    public SpaceOperationController(SpaceOperationCenterService operationCenter) {
        this.operationCenter = operationCenter;
    }

    @PostMapping("/constellations/{constellationName}")
    @Operation(summary = "Создать пустую спутниковую группировку")
    public ResponseEntity<Void> createConstellation(
            @PathVariable String constellationName
    ) {
        operationCenter.createConstellation(constellationName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/add-satellites")
    @Operation(summary = "Создать спутник и добавить его в группировку")
    public ResponseEntity<SatelliteResponse> addSatellite(
            @RequestBody AddSatelliteRequest request
    ) {
        Satellite satellite = operationCenter.addSatellite(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SatelliteResponse.from(satellite));
    }

    @PostMapping("/missions")
    @Operation(summary = "Выполнить миссию группировки или одного спутника")
    public ResponseEntity<Void> executeMission(
            @RequestBody ApiMissionRequest request
    ) {
        operationCenter.executeMission(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/overview")
    @Operation(summary = "Получить текстовую сводку системы")
    public String overview() {
        return operationCenter.getOverview();
    }

    @GetMapping("/constellations/{constellationName}/status")
    @Operation(summary = "Получить состояние группировки")
    public ConstellationStatus constellationStatus(
            @PathVariable String constellationName
    ) {
        return operationCenter.getConstellationStatus(constellationName);
    }

    @PostMapping("/constellations/{constellationName}/recharge")
    @Operation(summary = "Подзарядить все спутники группировки")
    public ResponseEntity<Integer> rechargeConstellation(
            @PathVariable String constellationName,
            @RequestBody RechargeRequest request
    ) {
        return ResponseEntity.ok(
                operationCenter.rechargeConstellation(
                        constellationName,
                        request.amount()
                )
        );
    }

    @DeleteMapping("/constellations/{constellationName}/satellites/{satelliteName}")
    @Operation(summary = "Вывести спутник из эксплуатации")
    public SatelliteResponse retireSatellite(
            @PathVariable String constellationName,
            @PathVariable String satelliteName
    ) {
        return SatelliteResponse.from(
                operationCenter.retireSatellite(constellationName, satelliteName)
        );
    }
}
