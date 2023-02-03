package com.portfolio.moveandgroove.activity.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record ActivityDTO(Long id, String name, LocalDate creationDate, LocalTime duration) {}
