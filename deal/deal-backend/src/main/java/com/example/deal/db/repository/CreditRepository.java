package com.example.deal.db.repository;

import com.example.deal.db.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreditRepository extends JpaRepository<Credit, UUID> {

}
