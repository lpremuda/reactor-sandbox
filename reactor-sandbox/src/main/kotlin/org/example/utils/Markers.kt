package org.example.utils

import net.logstash.logback.marker.LogstashMarker
import net.logstash.logback.marker.Markers.appendEntries

fun markerMap(vararg pairs: Pair<String, Any?>): LogstashMarker {
    return appendEntries(mapOf(*pairs))
}

fun markerMap(map: Map<String, Any?>): LogstashMarker {
    return appendEntries(map)
}

fun markerMap(
    map: Map<String, Any?>,
    vararg pairs: Pair<String, Any?>,
): LogstashMarker {
    return appendEntries(map + mapOf(*pairs))
}
