
package model;

public class Doctor extends Person {

    private String specialization;
    private double appointmentFee;
    private boolean available;

    public Doctor(int id, String name, int age,
                  String specialization, double appointmentFee) {

        super(id, name, age);

        this.specialization = specialization;
        this.appointmentFee = appointmentFee;
        this.available = true;
    }

    public String getSpecialization() {
        return specialization;
    }

    public double getAppointmentFee() {
        return appointmentFee;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String getRole() {
        return "Doctor";
    }
}
