package com.sweethome.booking.service;

import com.sweethome.booking.model.BookingInfoEntity;
import com.sweethome.booking.repository.BookingRepository;
import com.sweethome.booking.util.BookingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;
    public BookingInfoEntity createBooking(BookingInfoEntity bookingInfoEntity) {

        long days = BookingHelper.getDifferenceDays(bookingInfoEntity.getFromDate(),
                bookingInfoEntity.getToDate());

        int numOfRooms = bookingInfoEntity.getNumOfRooms();
        int totalPrice = (int) (1000*numOfRooms*days);

        ArrayList<String> roomList = BookingHelper.getRandomNumbers((int) days);

        String roomNumbers = BookingHelper.getRoomNumbers(roomList);

        bookingInfoEntity.setRoomNumbers(roomNumbers);
        bookingInfoEntity.setRoomPrice(totalPrice);

        return bookingRepository.save(bookingInfoEntity);
    }
    @Transactional
    public Optional<BookingInfoEntity> updateTransaction(int transactionId, int bookingId) {

        int isUpdate = bookingRepository.updateTransaction(transactionId, bookingId);

        if(isUpdate>0){
            return bookingRepository.findById(bookingId);
        }

        return null;
    }

    public boolean isBookingExists(int bookingId) {

        return bookingRepository.existsById(bookingId);
    }
}
