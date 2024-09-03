package com.example.BookMyShow.controllers;

import com.example.BookMyShow.dtos.BookMovieRequestDto;
import com.example.BookMyShow.dtos.BookMovieResponseDto;
import com.example.BookMyShow.exceptions.InvalidShowException;
import com.example.BookMyShow.exceptions.InvalidUserException;
import com.example.BookMyShow.exceptions.ShowSeatNotAvailableException;
import com.example.BookMyShow.models.Booking;
import com.example.BookMyShow.models.ResponseStatus;
import com.example.BookMyShow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class BookingController {
    private BookingService bookingService;

    @Autowired
    BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    BookMovieResponseDto bookMovie(BookMovieRequestDto requestDto) throws InvalidUserException, InvalidShowException, ShowSeatNotAvailableException {
        BookMovieResponseDto responseDto = new BookMovieResponseDto();
        Booking booking = bookingService.bookMovie(
                requestDto.getUserId(),
                requestDto.getShowId(),
                requestDto.getShowSeatId()
        );
        try{
            responseDto.setBookingId(booking.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setAmount(booking.getPrice());
        }catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}
