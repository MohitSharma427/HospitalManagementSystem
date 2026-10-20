
package model;

public class Patient extends Person {

    private String disease;
    private String phone;

    public Patient(int id, String name, int age,
                   String disease, String phone) {

        super(id, name, age);

        this.disease = disease;
        this.phone = phone;
    }

    public String getDisease() {
        return disease;
    }

    public String getPhone() {
        return phone;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    @Override
    public String getRole() {
        return "Patient";
    }
}
