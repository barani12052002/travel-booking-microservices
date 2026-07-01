package com.barani.travel.repository;

import com.barani.travel.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttractionRepository
        extends JpaRepository<Attraction, Long> {

    List<Attraction> findByActiveTrue();

    Optional<Attraction> findByAttractionCode(String attractionCode);

    List<Attraction> findByCityIgnoreCase(String city);
}