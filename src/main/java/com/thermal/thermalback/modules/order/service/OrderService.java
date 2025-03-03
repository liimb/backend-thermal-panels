package com.thermal.thermalback.modules.order.service;

import com.thermal.thermalback.common.exception.order.OrderErrorCodeEnum;
import com.thermal.thermalback.common.exception.order.OrderException;
import com.thermal.thermalback.modules.account.repository.AccountRepository;
import com.thermal.thermalback.modules.order.controller.OrderRequest;
import com.thermal.thermalback.modules.order.controller.OrderResponse;
import com.thermal.thermalback.modules.order.entity.Order;
import com.thermal.thermalback.modules.order.repository.OrderRepository;
import com.thermal.thermalback.modules.user.material.UserMaterialDto;
import com.thermal.thermalback.modules.user.material.entity.UserMaterial;
import com.thermal.thermalback.modules.user.material.repository.UserMaterialRepository;
import com.thermal.thermalback.modules.user.work.UserWorkDto;
import com.thermal.thermalback.modules.user.work.entity.UserWork;
import com.thermal.thermalback.modules.user.work.repository.UserWorkRepository;
import com.thermal.thermalback.util.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final UserMaterialRepository userMaterialRepository;
    private final UserWorkRepository userWorkRepository;

    public OrderResponse createOrder(OrderRequest orderRequest) throws OrderException {
        if(orderRequest.userId() == null) throw new OrderException(OrderErrorCodeEnum.ORDER_ERROR);
        accountRepository.findById(orderRequest.userId()).orElseThrow(() -> new OrderException(OrderErrorCodeEnum.ORDER_ERROR));

        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.id(orderId);
        order.name(orderRequest.name());
        order.userId(orderRequest.userId());
        order.status(OrderStatus.ON_CONSIDERATION.getValue());

        List<UserMaterial> userMaterials = orderRequest.userMaterials().stream().map(m -> {
            UserMaterial userMaterial = new UserMaterial();
            userMaterial.id(UUID.randomUUID());
            userMaterial.materialId(m.materialId());
            userMaterial.orderId(orderId);
            userMaterial.count(m.count());
            return userMaterial;
        }).toList();

        List<UserWork> userWorks = orderRequest.userWorks().stream().map(w -> {
            UserWork userWork = new UserWork();
            userWork.id(UUID.randomUUID());
            userWork.workId(w.workId());
            userWork.orderId(orderId);
            userWork.count(w.count());
            userWork.comment(w.comment());
            return userWork;
        }).toList();

        orderRepository.saveAndFlush(order);
        userMaterialRepository.saveAllAndFlush(userMaterials);
        userWorkRepository.saveAllAndFlush(userWorks);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.orderId(orderId);
        orderResponse.userId(orderRequest.userId());
        orderResponse.status(order.status());
        orderResponse.userMaterials(orderRequest.userMaterials());
        orderResponse.userWorks(orderRequest.userWorks());

        return orderResponse;
    }

    public OrderResponse editOrder(UUID orderId, OrderRequest orderRequest) throws OrderException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCodeEnum.ORDER_NOT_FOUND));

        accountRepository.findById(orderRequest.userId())
                .orElseThrow(() -> new OrderException(OrderErrorCodeEnum.ORDER_ERROR));

        order.name(orderRequest.name());
        order.userId(orderRequest.userId());

        userMaterialRepository.deleteAllByOrderId(orderId);
        userWorkRepository.deleteAllByOrderId(orderId);

        List<UserMaterial> userMaterials = orderRequest.userMaterials().stream()
                .map(m -> new UserMaterial(UUID.randomUUID(), m.materialId(), orderId, m.count()))
                .toList();

        List<UserWork> userWorks = orderRequest.userWorks().stream()
                .map(w -> new UserWork(UUID.randomUUID(), w.workId(), orderId, w.count(), w.comment()))
                .toList();

        orderRepository.saveAndFlush(order);
        userMaterialRepository.saveAllAndFlush(userMaterials);
        userWorkRepository.saveAllAndFlush(userWorks);

        return new OrderResponse(orderId, orderRequest.userId(), order.status(), orderRequest.userMaterials(), orderRequest.userWorks());
    }

    public void deleteOrder(UUID orderId) throws OrderException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCodeEnum.ORDER_NOT_FOUND));

        userMaterialRepository.deleteAllByOrderId(orderId);
        userWorkRepository.deleteAllByOrderId(orderId);
        orderRepository.delete(order);
    }

    public List<OrderResponse> getAllByUser(UUID id) throws OrderException {
        List<Order> orders = orderRepository.findAllByUserId(id);
        if (orders.isEmpty()) {
            throw new OrderException(OrderErrorCodeEnum.ORDER_NOT_FOUND);
        }

        return orders.stream().map(order -> {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.orderId(order.id());
            orderResponse.userId(order.userId());
            orderResponse.status(order.status());

            List<UserMaterialDto> userMaterials = userMaterialRepository.findAllByOrderId(order.id()).stream().map(mat -> {
                UserMaterialDto materialDto = new UserMaterialDto();
                materialDto.materialId(mat.materialId());
                materialDto.count(mat.count());
                return materialDto;
            }).toList();

            List<UserWorkDto> userWorks = userWorkRepository.findAllByOrderId(order.id()).stream().map(wor -> {
                UserWorkDto workDto = new UserWorkDto();
                workDto.workId(wor.workId());
                workDto.count(wor.count());
                return workDto;
            }).toList();

            orderResponse.userMaterials(userMaterials);
            orderResponse.userWorks(userWorks);
            return orderResponse;
        }).toList();
    }
}
