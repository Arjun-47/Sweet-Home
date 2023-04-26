package com.sweethome.booking.controller;

import com.sweethome.booking.dto.BookingDTO;
import com.sweethome.booking.dto.TransactionDTO;
import com.sweethome.booking.model.BookingInfoEntity;
import com.sweethome.booking.service.BookingService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hotel")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/booking", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingInfoEntity> createBooking(@RequestBody BookingDTO bookingDTO) {
        BookingInfoEntity bookingInfoEntity = modelMapper.map(bookingDTO, BookingInfoEntity.class);
        return ResponseEntity.status(201).body(bookingService.createBooking(bookingInfoEntity));
    }

    @RequestMapping(value = "/booking/{bookingId}/transaction", method = RequestMethod.POST)
    public ResponseEntity<Object> createBookingWithTransaction(@Valid @RequestBody TransactionDTO transactionDTO,
                                                                          @PathVariable(name = "bookingId") int bookingId) {


        if(!bookingService.isBookingExists(bookingId)){
            Map<String, String> errors = new HashMap<>();
            String fieldName = "message";
            String errorMessage = " Invalid Booking Id ";
            errors.put(fieldName, errorMessage);
            errors.put("statusCode", "400");

            return ResponseEntity.status(400).body(errors);
        }

        HttpEntity<TransactionDTO> request = new HttpEntity<>(transactionDTO);
        ResponseEntity<Integer> response = restTemplate
                .exchange("http://localhost:8083/payment/transaction", HttpMethod.POST, request, Integer.class);

        int transactionId = response.getBody().intValue();

        Optional<BookingInfoEntity> bookingInfoEntity = bookingService.updateTransaction(transactionId, bookingId);

        String message = "Booking confirmed for user with aadhaar number: "
                + bookingInfoEntity.get().getAadharNumber()
                +    "    |    "
                + "Here are the booking details:    " + bookingInfoEntity.get().toString();

        System.out.println(message);

        return ResponseEntity.status(201).body(bookingInfoEntity.get());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = "message";
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            errors.put("statusCode","400");
        });
        return errors;
    }
}
