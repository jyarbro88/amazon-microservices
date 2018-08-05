package com.microservices.orders.services.LineItem;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.repositories.LineItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LineItemService {

    private LineItemRepository lineItemRepository;

    public LineItemService(LineItemRepository lineItemRepository) { this.lineItemRepository = lineItemRepository; }

    public Iterable<LineItem> getAllLineItems(){
        return lineItemRepository.findAll();
    }

    public List<LineItem> findByOrderId(Long id){
        return lineItemRepository.findAllByOrderId(id);
    }

    public Optional<LineItem> findLineItemById(Long id){
        return lineItemRepository.findById(id);
    }

    public void deleteLineItem(Long id){
        lineItemRepository.deleteById(id);
    }
}