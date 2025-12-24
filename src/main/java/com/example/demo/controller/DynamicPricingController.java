@GetMapping("/latest/{eventId}")
public ResponseEntity<DynamicPriceRecord> latest(
        @PathVariable Long eventId) {

    return dynamicPricingEngineService.getLatestPrice(eventId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}
