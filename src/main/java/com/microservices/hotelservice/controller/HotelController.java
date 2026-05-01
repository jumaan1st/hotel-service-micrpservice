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
    public ResponseEntity<Hotel> CreateHotel(@RequestBody Hotel hotel) {
        Hotel responseHotel = hotelService.CreateHotel(hotel);
        return ResponseEntity.ok().body(responseHotel);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Hotel>> CreateHotelBulk(@RequestBody List<Hotel> hotel) {
        List<Hotel> responseHotel = hotelService.CreateHotelBulk(hotel);
        return ResponseEntity.ok().body(responseHotel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> findById(@PathVariable String id) {
        Hotel responseHotel = hotelService.findById(id);
        return ResponseEntity.ok().body(responseHotel);
    }


    @GetMapping("")
    public List<Hotel> findAll() {
        List<Hotel>hotels = hotelService.findAll();
        return hotels;
    }

    @PutMapping("")
    public ResponseEntity<Hotel> UpdateHotel(@RequestBody Hotel hotel) {
        Hotel responseHotel = hotelService.UpdateHotel(hotel);
        return ResponseEntity.ok().body(responseHotel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Hotel> DeleteHotel(@RequestBody String id) {
        Hotel responseHotel = hotelService.DeleteHotel(id);
        return ResponseEntity.ok().body(responseHotel);
    }

}
