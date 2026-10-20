
package model;

import java.time.LocalDate;

public class Bill {

    private int billId;
    private int patientId;
    private double consultationFee;
    private double medicineFee;
    private double roomFee;
    private LocalDate billDate;

    public Bill(int billId, int patientId,
                double consultationFee,
                double medicineFee, double roomFee) {

        this.billId = billId;
        this.patientId = patientId;
        this.consultationFee = consultationFee;
        this.medicineFee = medicineFee;
        this.roomFee = roomFee;
        this.billDate = LocalDate.now();
    }

    public int getBillId() {
        return billId;
    }

    public int getPatientId() {
        return patientId;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public double getMedicineFee() {
        return medicineFee;
    }

    public double getRoomFee() {
        return roomFee;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public double calculateTotal() {
        return consultationFee + medicineFee + roomFee;
    }
}
