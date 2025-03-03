package com.thermal.thermalback.modules.order.controller;

import com.thermal.thermalback.common.exception.order.OrderException;
import com.thermal.thermalback.modules.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) throws OrderException {
        return orderService.createOrder(orderRequest);
    }

    @PostMapping("/edit/{id}")
    public OrderResponse editOrder(@PathVariable UUID id, @RequestBody OrderRequest orderRequest) throws OrderException {
        return orderService.editOrder(id, orderRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@PathVariable UUID id) throws OrderException {
        orderService.deleteOrder(id);
    }

    @GetMapping("/get-all-by-user/{id}")
    public List<OrderResponse> getAllByUser(@PathVariable UUID id) throws OrderException {
        return orderService.getAllByUser(id);
    }
}
