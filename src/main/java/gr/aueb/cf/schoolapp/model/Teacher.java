package gr.aueb.cf.schoolapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String uuid;
    @Column(name="is_active")
    private boolean isActive;
    //private boolean isAUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_info_id")
    private PersonalInfo personalInfo;

    public void initializeUUID(){
        if(uuid==null) uuid = UUID.randomUUID().toString();
    }
}
