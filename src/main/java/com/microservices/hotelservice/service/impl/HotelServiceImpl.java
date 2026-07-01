package com.microservices.hotelservice.service.impl;

import com.microservices.hotelservice.entity.Hotel;
import com.microservices.hotelservice.repository.HotelRepository;
import com.microservices.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public Hotel findById(String id) {
        validateId(id);
        return hotelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found with id: " + id));
    }

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel createHotel(Hotel hotel) {
        validateHotel(hotel);
        validateDuplicateHotel(hotel);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> createHotelBulk(List<Hotel> hotels) {

        log.info("HotelServiceImpl :: createHotelBulk()");

        if (hotels == null || hotels.isEmpty()) {
            throw new IllegalArgumentException("Hotel list cannot be empty");
        }

        for (Hotel hotel : hotels) {
            validateHotel(hotel);
        }

        // Single DB hit for duplicates (recommended improvement)
        List<String> emails = hotels.stream()
                .map(Hotel::getEmail)
                .filter(StringUtils::hasText)
                .toList();

        List<String> phones = hotels.stream()
                .map(Hotel::getPhone)
                .filter(StringUtils::hasText)
                .toList();

        List<Hotel> existingHotels =
                hotelRepository.findByEmailInOrPhoneIn(emails, phones);

        for (Hotel hotel : hotels) {
            for (Hotel existing : existingHotels) {
                if (existing.getEmail() != null &&
                        existing.getEmail().equals(hotel.getEmail())) {
                    throw new IllegalArgumentException("Duplicate email: " + hotel.getEmail());
                }

                if (existing.getPhone() != null &&
                        existing.getPhone().equals(hotel.getPhone())) {
                    throw new IllegalArgumentException("Duplicate phone: " + hotel.getPhone());
                }
            }
        }

        return hotelRepository.saveAll(hotels);
    }

    @Override
    public Hotel updateHotel(Hotel hotel) {

        validateId(hotel.getId());
        validateHotel(hotel);

        Hotel existingHotel = findById(hotel.getId());

        validateDuplicateHotelOnUpdate(hotel);

        existingHotel.setName(hotel.getName());
        existingHotel.setDescription(hotel.getDescription());
        existingHotel.setAddress(hotel.getAddress());
        existingHotel.setPhone(hotel.getPhone());
        existingHotel.setEmail(hotel.getEmail());

        return hotelRepository.save(existingHotel);
    }

    @Override
    public Hotel deleteHotel(String id) {
        Hotel hotel = findById(id);
        hotelRepository.delete(hotel);
        return hotel;
    }

    @Override
    public List<Hotel> findByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return hotelRepository.findAllById(ids);
    }

    // ===================== VALIDATIONS =====================

    private void validateHotel(Hotel hotel) {

        if (hotel == null) {
            throw new IllegalArgumentException("Hotel cannot be null");
        }

        if (!StringUtils.hasText(hotel.getName())) {
            throw new IllegalArgumentException("Hotel name is required");
        }

        if (!StringUtils.hasText(hotel.getAddress())) {
            throw new IllegalArgumentException("Address is required");
        }

        if (!StringUtils.hasText(hotel.getEmail()) ||
                !hotel.getEmail().contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }

        if (!StringUtils.hasText(hotel.getPhone()) ||
                !hotel.getPhone().matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("Phone must be 10 digits");
        }
    }

    private void validateId(String id) {
        if (id==null) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
    }

    private void validateDuplicateHotel(Hotel hotel) {
        Optional<Hotel> existing =
                hotelRepository.findByEmailOrPhone(hotel.getEmail(), hotel.getPhone());

        if (existing.isPresent()) {
            throw new IllegalArgumentException("Hotel already exists with same email or phone");
        }
    }

    private void validateDuplicateHotelOnUpdate(Hotel hotel) {

        Optional<Hotel> existing =
                hotelRepository.findByEmailOrPhone(hotel.getEmail(), hotel.getPhone());

        if (existing.isPresent() &&
                !existing.get().getId().equals(hotel.getId())) {
            throw new IllegalArgumentException("Duplicate email or phone found");
        }
    }
}