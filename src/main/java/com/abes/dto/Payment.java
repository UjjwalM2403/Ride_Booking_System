package com.abes.dto;

import com.abes.enums.PaymentMethod;
import com.abes.enums.PaymentStatus;
import java.time.LocalDateTime;

public class Payment {
    private Long paymentId;
    private Long rideId;
    private Double amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private LocalDateTime timestamp;
    
    public Payment() {}
    
    public Payment(Long paymentId, Long rideId, Double amount, PaymentMethod method) {
        this.paymentId = paymentId;
        this.rideId = rideId;
        this.amount = amount;
        this.method = method;
        this.status = PaymentStatus.PENDING;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }
    public Long getRideId() { return rideId; }
    public void setRideId(Long rideId) { this.rideId = rideId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    @Override
    public String toString() {
        return "Payment{id=" + paymentId + ", amount=" + amount + ", method=" + method + ", status=" + status + "}";
    }
}
