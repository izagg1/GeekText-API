package com.geektext.geektext_api.repository;

import com.geektext.geektext_api.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}