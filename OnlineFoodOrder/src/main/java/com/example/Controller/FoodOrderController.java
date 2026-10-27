package com.example.Controller;


import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.Model.FoodOrder;
import com.example.Service.FoodOrderService;

@RestController
@RequestMapping("/orders")
@Validated
public class FoodOrderController {

    @Autowired
    private FoodOrderService service;

    // Add Order
    
    @PostMapping
    public FoodOrder addOrder(@Valid @RequestBody FoodOrder order) {
        return service.addOrder(order);
    }

   
    @GetMapping
    public List<FoodOrder> getAllOrders() {
        return service.getAllOrders();
    }

    // get by id
    @GetMapping("/{id}")
    public FoodOrder getOrderById(@PathVariable Long id) {
        return service.getOrderById(id);
    }

    // Update 
    @PutMapping("/{id}")
    public FoodOrder updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody FoodOrder order) {
        return service.updateOrder(id, order);
    }

    // Deleteorder
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        return service.deleteOrder(id);
    }

    // Partial Update
    @PatchMapping("/{id}")
    public FoodOrder partialUpdateOrder(
            @PathVariable Long id,
            @RequestBody FoodOrder order) {
        return service.partialUpdateOrder(id, order);
    }

    // Search Food By Partial Name
    @GetMapping("/search")
    public List<FoodOrder> searchByFoodItem(
            @RequestParam String keyword) {
        return service.searchByFoodItem(keyword);
    }

    //  Get Orders Within Budget
    @GetMapping("/budget")
    public List<FoodOrder> getOrdersWithinBudget(
            @RequestParam BigDecimal maxPrice) {
        return service.getOrdersWithinBudget(maxPrice);
    }

    // Get Orders By Status
    @GetMapping("/status")
    public List<FoodOrder> getOrdersByStatus(
            @RequestParam String status) {
        return service.getOrdersByStatus(status);
    }
}
