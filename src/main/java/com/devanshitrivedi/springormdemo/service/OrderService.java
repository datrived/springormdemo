package com.devanshitrivedi.springormdemo.service;

import com.devanshitrivedi.springormdemo.entity.Order;
import com.devanshitrivedi.springormdemo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Order createOrUpdate(Order order){
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
