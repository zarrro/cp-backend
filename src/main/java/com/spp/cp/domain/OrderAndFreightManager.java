package com.spp.cp.domain;

import org.springframework.stereotype.Service;

/**
 * accepts order -> register order in open state, create freight records
 * update order -> allowed in open state, can update goods order type
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
@Service
public class OrderAndFreightManager {
}
