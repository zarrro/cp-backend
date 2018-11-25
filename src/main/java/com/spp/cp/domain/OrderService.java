package com.spp.cp.domain;

import com.spp.cp.domain.entities.Order;
import com.spp.cp.rest.CreateOrderParams;
import com.spp.cp.rest.UpdateOrderParams;
import org.springframework.stereotype.Service;

/**
 * accepts order -> register order in open state, create freight records
 * update order -> allowed in open state, can update goods, order type
 * close order -> set to closed_completed or closed_uncompleted state, no more freight can be added, no more updates, pending freights are canceled
 * delete order -> delete order and related freight record, can be done if all order freights are in pending state
 *
 * add freight to order -> order must be in open state
 * freight states: pending, confirmed, loaded, completed, canceled, canceled_confirmed, canceled_loaded
 * allowed transitions
 *  pending -> confirmed -> loaded -> completed;
 *  pending -> canceled; confirmed -> confirm_canceled; loaded -> loaded_canceled
 * delete freight -> can be done only for pending freights
 *
 * every manager operations is registered in the audit event log
 */

//  in the controller they are called in single method to save two calls to the client

@Service
public interface OrderService {

    /**
     * Creates order.
     *
     * Throw runtime exception in case fail to create order
     *  - IllegalArgumentException for incorrect params
     *
     * @param createParams
     * @return orderId on success.
     */
    Order createOrder(CreateOrderParams createParams);

    /**
     * Update order.
     *
     * Throw runtime exception in case fail to update the order
     *  - IllegalArgumentException for incorrect params
     *  - IllegalStateException if the order is closed
     *
     * @param updateParams
     * @return the createdOrderEntity on success.
     */
    Order updateOrder(Long orderId, UpdateOrderParams updateParams);

}
