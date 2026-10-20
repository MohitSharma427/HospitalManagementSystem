
package model;

import java.time.LocalDate;

public class RoomBooking {

    private int bookingId;
    private int patientId;
    private int roomId;
    private int days;
    private LocalDate checkInDate;
    private String status;

    public RoomBooking(int bookingId, int patientId,
                       int roomId, int days,
                       LocalDate checkInDate) {

        this.bookingId = bookingId;
        this.patientId = patientId;
        this.roomId = roomId;
        this.days = days;
        this.checkInDate = checkInDate;
        this.status = "Active";
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getDays() {
        return days;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double calculateRoomCharge(double pricePerDay) {
        return days * pricePerDay;
    }
}
