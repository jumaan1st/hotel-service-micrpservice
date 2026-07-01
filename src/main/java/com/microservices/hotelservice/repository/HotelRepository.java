package com.microservices.hotelservice.repository;

import com.microservices.hotelservice.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, String> {
    List<Hotel> findByEmailInOrPhoneIn(List<String> emails, List<String> phones);

    Optional<Hotel> findByEmailOrPhone(String email, String phone);
}
