package com.sweethome.booking.repository;

import com.sweethome.booking.model.BookingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingInfoEntity, Integer> {

}
