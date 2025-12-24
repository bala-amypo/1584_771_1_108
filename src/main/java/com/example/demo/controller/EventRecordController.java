@GetMapping("/lookup/{eventCode}")
public ResponseEntity<EventRecord> getByCode(
        @PathVariable String eventCode) {

    return eventRecordService.getEventByCode(eventCode)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}
