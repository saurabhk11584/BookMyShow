package com.example.BookMyShow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Booking extends BaseModel {
    @ManyToMany
    private List<ShowSeat> seats;
    @ManyToOne
    private Show show;
    @OneToMany
    private List<Payment> payments;
    @Enumerated(EnumType.ORDINAL) //Assign value 1 to status etc
    private BookingStatus bookingStatus;
    @ManyToOne
    private User user;
    private int price;
    private Date timeOfBooking;
}
