package com.spp.cp.rest;

import com.spp.cp.db.OrderRepository;
import com.spp.cp.domain.Exceptions;
import com.spp.cp.domain.OrderService;
import com.spp.cp.domain.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getOrdersPage(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
                                     UriComponentsBuilder uriBuilder, HttpServletResponse response) {

        // if page or size are not set, return all results without pagination
        if (!(page.isPresent() && size.isPresent())) {
            return this.orderRepo.findAllOrders();
        } else {
            Pageable pageRequest = PageRequest.of(page.get(), size.get());
            Page<Order> resultPage = this.orderRepo.findAllOrders(pageRequest);

            eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Order>
                    (Order.class, uriBuilder, response, page.get(), resultPage.getTotalPages(), size.get()));

            return this.orderRepo.findAllOrders(pageRequest).getContent();
        }
    }

    @GetMapping("/{orderId}")
    @ResponseBody
    public Order getOrder(@PathVariable Long orderId) {
        Optional<Order> o = orderRepo.findById(orderId);
        if (o.isPresent()) {
            return o.get();
        } else {
            throw new Exceptions.EntityNotFoundException("orderId " + orderId);
        }
    }

    @PostMapping(consumes = "application/json")
    @ResponseBody
    public Order createOrder(@RequestBody CreateOrderParams params) {
        return orderService.createOrder(params);
    }

    @PatchMapping(consumes = "application/json", value = "/{orderId}")
    @ResponseBody
    public Order updateOrder(@PathVariable Long orderId, @RequestBody UpdateOrderParams updateParams) {
        return orderService.updateOrder(orderId, updateParams);
    }
}
