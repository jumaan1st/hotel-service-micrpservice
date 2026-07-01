package com.microservices.hotelservice.controller;

import com.microservices.hotelservice.entity.Hotel;
import com.microservices.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/hotel")
@RequiredArgsConstructor
@CrossOrigin
public class HotelController {
    private final HotelService hotelService;

    @PostMapping("")
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel responseHotel = hotelService.createHotel(hotel);
        return ResponseEntity.ok().body(responseHotel);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Hotel>> createHotelBulk(@RequestBody List<Hotel> hotel) {
        List<Hotel> responseHotel = hotelService.createHotelBulk(hotel);
        return ResponseEntity.ok().body(responseHotel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> findById(@PathVariable String id) {
        Hotel responseHotel = hotelService.findById(id);
        return ResponseEntity.ok().body(responseHotel);
    }

    @PostMapping("/hotels-by-ids")
    public ResponseEntity<List<Hotel>> findByIds(@RequestBody List<String> ids) {
        List<Hotel> responseHotels = hotelService.findByIds(ids);
        return ResponseEntity.ok().body(responseHotels);
    }

    @GetMapping("")
    public List<Hotel> findAll() {
        List<Hotel> hotels = hotelService.findAll();
        return hotels;
    }

    @PutMapping("")
    public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel) {
        Hotel responseHotel = hotelService.updateHotel(hotel);
        return ResponseEntity.ok().body(responseHotel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Hotel> deleteHotel(@PathVariable String id) {
        Hotel responseHotel = hotelService.deleteHotel(id);
        return ResponseEntity.ok().body(responseHotel);
    }

}
