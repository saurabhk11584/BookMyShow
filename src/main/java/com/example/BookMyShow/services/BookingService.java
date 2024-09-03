package com.example.BookMyShow.services;

import com.example.BookMyShow.exceptions.InvalidShowException;
import com.example.BookMyShow.exceptions.InvalidUserException;
import com.example.BookMyShow.exceptions.ShowSeatNotAvailableException;
import com.example.BookMyShow.models.*;
import com.example.BookMyShow.repositories.ShowRepository;
import com.example.BookMyShow.repositories.ShowSeatRepository;
import com.example.BookMyShow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculator priceCalculator;

    @Autowired
    BookingService(UserRepository userRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository, PriceCalculator priceCalculator) {
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.showSeatRepository = showSeatRepository;
        this.priceCalculator = priceCalculator;
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, Long showId, List<Long> showSeatIds) throws InvalidUserException, InvalidShowException, ShowSeatNotAvailableException {
        //Take a lock while reading the data
        //release the lock once status get's updated

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            throw new InvalidUserException("Invalid User");
        }
        User user = optionalUser.get();

        Optional<Show> optionalShow = showRepository.findById(showId);
        if(optionalShow.isEmpty()) {
            throw new InvalidShowException("Invalid Show");
        }
        Show show = optionalShow.get();
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        //Check seat available
        for(ShowSeat showSeat:showSeats) {
            if(!showSeat.getSeatStatus().equals(SeatStatus.AVAILABLE)) {
                throw new ShowSeatNotAvailableException("ShowSeat not available");
            }
        }
        List<ShowSeat> finalShowSeats = new ArrayList<>();
        //If yes mark seat as blocked
        for(ShowSeat showSeat:showSeats) {
            showSeat.setSeatStatus(SeatStatus.BLOCKED);
            //Save in database
            //showSeatRepository.save(showSeat);
            finalShowSeats.add(showSeatRepository.save(showSeat));
        }

        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setTimeOfBooking(new Date());
        booking.setShow(show);
        booking.setUser(user);
        booking.setSeats(finalShowSeats);
        booking.setPayments(new ArrayList<>());
        booking.setPrice(priceCalculator.calculatePrice(show, finalShowSeats));

        return booking;
    }
}
