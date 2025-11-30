package com.trackmyfix.trackmyfix.services;

import com.trackmyfix.trackmyfix.entity.Action;
import com.trackmyfix.trackmyfix.entity.Movement;

import java.util.List;
import java.util.Map;

public interface IMovementService {
    Map<String, Object> findAll();

    List<Movement> findByAction(Action action);

    Movement findById(Long id);

    List<String> getAllAction();
}
