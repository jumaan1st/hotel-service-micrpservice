package com.microservices.hotelservice.service;

import com.microservices.hotelservice.entity.Hotel;

import java.util.List;
import java.util.UUID;

public interface HotelService {

    Hotel findById(String name);
    List<Hotel> findAll();

    Hotel CreateHotel(Hotel hotel);
    List<Hotel> CreateHotelBulk(List<Hotel> hotel);
    Hotel UpdateHotel(Hotel hotel);
    Hotel DeleteHotel(String id);


}
