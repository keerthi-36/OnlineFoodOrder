package com.example.Service;

import com.example.Model.FoodOrder;
import com.example.Exception.OrderNotFoundException;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class FoodOrderService {

    private final ArrayList<FoodOrder> orders = new ArrayList<>();
    private long nextId = 1L;

   
    private FoodOrder copyOrder(FoodOrder order) {
        return new FoodOrder(
                order.getOrderId(),
                order.getCustomerName(),
                order.getFoodItem(),
                order.getQuantity(),
                order.getPrice(),
                order.getOrderStatus()
        );
    }

    // add
    public FoodOrder addOrder(FoodOrder request) {
        FoodOrder order = new FoodOrder(
                nextId++,
                request.getCustomerName().trim(),
                request.getFoodItem().trim(),
                request.getQuantity(),
                request.getPrice(),
                request.getOrderStatus().trim()
        );

        orders.add(order);
        return copyOrder(order);
    }

    // Get All Orders
    public List<FoodOrder> getAllOrders() {
        return orders.stream()
                .map(this::copyOrder)
                .toList();
    }

    // get bt id
    public FoodOrder getOrderById(Long id) {
        FoodOrder order = orders.stream()
                .filter(item -> item.getOrderId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found with ID: " + id));

        return copyOrder(order);
    }

    // Update 
    public FoodOrder updateOrder(Long id, FoodOrder request) {
        FoodOrder order = orders.stream()
                .filter(item -> item.getOrderId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found with ID: " + id));

        order.setCustomerName(request.getCustomerName().trim());
        order.setFoodItem(request.getFoodItem().trim());
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice());
        order.setOrderStatus(request.getOrderStatus().trim());

        return copyOrder(order);
    }

    // Delete Order
    public String deleteOrder(Long id) {
        FoodOrder order = orders.stream()
                .filter(item -> item.getOrderId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found with ID: " + id));

        orders.remove(order);
        return "Order deleted successfully";
    }

    // Partial Update
    public FoodOrder partialUpdateOrder(Long id, FoodOrder request) {
        FoodOrder order = orders.stream()
                .filter(item -> item.getOrderId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found with ID: " + id));

        if (request.getCustomerName() != null) {
            order.setCustomerName(request.getCustomerName().trim());
        }

        if (request.getFoodItem() != null) {
            order.setFoodItem(request.getFoodItem().trim());
        }

        if (request.getQuantity() != null && request.getQuantity()>0) {
            order.setQuantity(request.getQuantity());
        }

        if (request.getPrice() != null) {
            order.setPrice(request.getPrice());
        }

        if (request.getOrderStatus() != null) {
            order.setOrderStatus(request.getOrderStatus().trim());
        }

        return copyOrder(order);
    }

    //  Search Food By Partial Name
    public List<FoodOrder> searchByFoodItem(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException(
                    "Search keyword cannot be empty");
        }

        String searchText = keyword.trim().toLowerCase(Locale.ROOT);

        return orders.stream()
                .filter(order -> order.getFoodItem()
                        .toLowerCase(Locale.ROOT)
                        .contains(searchText))
                .map(this::copyOrder)
                .toList();
    }

    //  Get Orders Within Budget
    public List<FoodOrder> getOrdersWithinBudget(BigDecimal maxPrice) {
        if (maxPrice == null || maxPrice.signum() < 0) {
            throw new IllegalArgumentException(
                    "Maximum price cannot be negative or empty");
        }

        return orders.stream()
                .filter(order -> order.getPrice()
                        .compareTo(maxPrice) <= 0)
                .map(this::copyOrder)
                .toList();
    }

    // Get Orders By Status
    public List<FoodOrder> getOrdersByStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException(
                    "Order status cannot be empty");
        }

        return orders.stream()
                .filter(order -> order.getOrderStatus()
                        .equalsIgnoreCase(status.trim()))
                .map(this::copyOrder)
                .toList();
    }
}