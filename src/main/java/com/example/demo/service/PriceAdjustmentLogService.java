package com.example.demo.service;

import com.example.demo.model.PriceAdjustmentLog;
import java.util.List;

public interface PriceAdjustmentLogService {

    PriceAdjustmentLog create(PriceAdjustmentLog log);

    List<PriceAdjustmentLog> getAdjustmentsByEvent(long eventId);

    List<PriceAdjustmentLog> getAll();
}
