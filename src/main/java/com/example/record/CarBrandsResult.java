package com.example.record;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record CarBrandsResult(List<String> filteredByLength, List<String> filteredByA, Set<String> filteredByAudF,
                              Set<String> filteredAudi, Map<String, List<String>> groupedByPrefix) {
}
