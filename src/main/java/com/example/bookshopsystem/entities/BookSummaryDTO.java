package com.example.bookshopsystem.entities;

import java.math.BigDecimal;

public interface BookSummaryDTO {
    String getTitle();
    EditionType getEditionType();
    AgeRestriction getAgeRestriction();
    BigDecimal getPrice();
}
