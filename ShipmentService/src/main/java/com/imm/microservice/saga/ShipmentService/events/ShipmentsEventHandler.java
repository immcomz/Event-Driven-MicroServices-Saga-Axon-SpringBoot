package com.imm.microservice.saga.ShipmentService.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.imm.microservice.saga.CommonService.events.OrderShippedEvent;
import com.imm.microservice.saga.ShipmentService.data.Shipment;
import com.imm.microservice.saga.ShipmentService.data.ShipmentRepository;

@Component
public class ShipmentsEventHandler {

    private ShipmentRepository shipmentRepository;

    public ShipmentsEventHandler(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @EventHandler
    public void on(OrderShippedEvent event) {
        Shipment shipment
                = new Shipment();
        BeanUtils.copyProperties(event,shipment);
        shipmentRepository.save(shipment);
    }
}
