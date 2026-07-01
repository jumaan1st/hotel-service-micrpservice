package com.microservices.hotelservice.service;

import com.microservices.hotelservice.entity.Hotel;

import java.util.List;
import java.util.UUID;

public interface HotelService {

    Hotel findById(String name);
    List<Hotel> findAll();

    Hotel createHotel(Hotel hotel);
    List<Hotel> createHotelBulk(List<Hotel> hotel);
    Hotel updateHotel(Hotel hotel);
    Hotel deleteHotel(String id);
    List<Hotel> findByIds(List<String> ids);


}
