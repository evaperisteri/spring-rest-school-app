package gr.aueb.cf.schoolapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="personal_information")
public class PersonalInfo extends AbstractEntity{

    private Long id;
    private String amka;
    private String identityNumber;
    private String placeOfBirth;
    private String municipalityOfRegistration;
    @OneToOne
    @JoinColumn(name = "amka_file_id")
    private Attachment amkaFile;
}
