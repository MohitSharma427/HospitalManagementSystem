
package interfaces;

import model.Doctor;

public interface DoctorRecommendation {

    Doctor suggestDoctor(String disease);
}
