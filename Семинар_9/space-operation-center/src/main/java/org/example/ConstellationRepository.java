package org.example;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class ConstellationRepository {
    private final Map<String, SatelliteConstellation> constellations = new LinkedHashMap<>();

    public void save(SatelliteConstellation constellation) {
        constellations.put(constellation.getConstellationName(), constellation);
    }

    public SatelliteConstellation findByName(String name) {
        return constellations.get(name);
    }

    public Map<String, SatelliteConstellation> findAll() {
        return Collections.unmodifiableMap(constellations);
    }

    public boolean contains(String name) {
        return constellations.containsKey(name);
    }

    public void remove(String name) {
        constellations.remove(name);
    }
}
