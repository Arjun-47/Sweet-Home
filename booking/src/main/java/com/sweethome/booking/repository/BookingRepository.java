package com.sweethome.booking.repository;

import com.sweethome.booking.model.BookingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingInfoEntity, Integer> {

    @Modifying
    @Query("update BookingInfoEntity b set b.transactionId = :transactionId where b.bookingId = :bookingId")
    int updateTransaction(@Param(value = "transactionId") int transactionId,
                           @Param(value = "bookingId") int bookingId);
}
