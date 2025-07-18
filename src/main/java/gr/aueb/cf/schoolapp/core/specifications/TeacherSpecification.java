package gr.aueb.cf.schoolapp.core.specifications;

import gr.aueb.cf.schoolapp.model.PersonalInfo;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.model.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class TeacherSpecification {

    //private constructor γιατί είναι utility class και δεν θέλουμε να γίνει instantiate. Μόνο public static methods
    private TeacherSpecification(){

    }
    //return lambda που υλοποιεί την .toPredicate (την μοναδική μέθοδο του Specification)
    public static Specification<Teacher> teacherUserAfmIs(String afm){
        return(
                (root, query, criteriaBuilder)->{
                    //δεν λαμβανει υποψιν το φιλτρο
                    if(afm==null || afm.isBlank()) return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                    Join<Teacher, User> user = root.join("user");

                    return criteriaBuilder.equal(user.get("afm"), afm);
                }
            );
    }

    public static Specification<Teacher> teacherIsActive(Boolean isActive) {
        return(
                (root, query, criteriaBuilder)->{
                    if(isActive == null) return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                    Join<Teacher, User> user = root.join("user");
                    return criteriaBuilder.equal(user.get("isActive"), isActive);
                }
            );
    }

    public static Specification<Teacher> teacherPersonalInfoAmkaIs(String amka) {
        return(
                (root, query, criteriaBuilder)->{
                    if(amka == null|| amka.isBlank()) return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
                    Join<Teacher, PersonalInfo> personalInfo = root.join("personalInfo");
                    //.equal συγκρινει το αμκα της εφαρμογησ με το αμκα της βασης
                    return criteriaBuilder.equal(personalInfo.get("amka"), amka);
                }
        );
    }

    public static Specification<Teacher> teacherStringFieldLike(String field, String value) {
        return(
                //.trim().isEmpty() κάνει τα ιδια με την .isBlank()
                (root, query, criteriaBuilder)->{
                    if(value == null|| value.trim().isEmpty()) return criteriaBuilder.isTrue(criteriaBuilder.literal(true));

                    return criteriaBuilder.like(criteriaBuilder.upper(root.get(field)), "%"+value.toUpperCase()+"%");
                }
        );
    }

}
