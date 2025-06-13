package gr.aueb.cf.schoolapp.core.filters;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * αντί να επιστρέφω Page δημιουργώ μια custom class
 * Java 21 θα ηταν record
 */
@Getter
@Setter
public class Paginated<T> {

    List<T> data;
    long totalElements;
    int totalPages;
    int numberOfElements;
    int currentPage;
    int pageSize;

    //constructor populated by Page<T>
    public Paginated(Page<T> page){
        this.data = page.getContent();
        this.totalElements=page.getTotalElements();
        this.totalPages=page.getTotalPages();
        this.numberOfElements=page.getNumberOfElements();
        this.currentPage=page.getNumber();
        this.pageSize=page.getSize();
    }
}
