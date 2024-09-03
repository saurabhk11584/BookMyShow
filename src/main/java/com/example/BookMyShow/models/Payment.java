package com.example.BookMyShow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

import java.util.Date;

@Getter
@Setter
@Entity
public class Payment extends BaseModel{
    private int amount;
    @Enumerated(EnumType.ORDINAL)
    private PaymentMethod paymentMethod;
    private int referenceNum;
    private Date timeStamp;
    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.ORDINAL)
    private PaymentProvider paymentProvider;

}
