package gr.aueb.cf.schoolapp.rest;


import gr.aueb.cf.schoolapp.core.exceptions.*;
import gr.aueb.cf.schoolapp.core.filters.Paginated;
import gr.aueb.cf.schoolapp.core.filters.TeacherFilters;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
//root path
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeacherRestController {
    private final TeacherService teacherService;

    //@Valid ενεργοποιεί τον hibernate validator. Αν υπαρχουν λάθη τα βαζει στο binding result και θα τα κανει extract ο exception handler σε JSON,
    // @RequestPart επειδη στελνουμε και data και αρχειο

    @PostMapping("/teachers/save")
    public ResponseEntity<TeacherReadOnlyDTO> saveTeacher
    (@Valid @RequestPart(name="teacher") TeacherInsertDTO teacherInsertDTO,
     @Nullable @RequestPart(name="amkaFile") MultipartFile amkaFile,
     BindingResult bindingResult)
     throws AppObjectInvalidArgumentException, ValidationException, AppObjectAlreadyExists,
            AppServerException {
        //δεν κανουμε try catch γιατι θα δημιουργηθει ενας κεντρικος handler για τα δικά μας exceptions
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult);
        }

        try {
            TeacherReadOnlyDTO teacherReadOnlyDTO= teacherService.saveTeacher(teacherInsertDTO, amkaFile);
            return new ResponseEntity<>(teacherReadOnlyDTO, HttpStatus.OK);
            //μετατρεπουμε το ioexception σε appserverexception
        } catch(IOException e ){
            throw new AppServerException("Attachment", "Attatchment can not get uploaded");
        }

    }

    @GetMapping("/teachers/paginated")
    public ResponseEntity<Page<TeacherReadOnlyDTO>> getPaginatedTeachers(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="5") int size){
        Page<TeacherReadOnlyDTO> teachersPage = teacherService.getPaginatedTeachers(page, size);
        return new ResponseEntity<>(teachersPage, HttpStatus.OK);
    }

    @PostMapping("/teachers/filtered")
    //με @RequestBody παίρνουμε JSON
    public ResponseEntity<List<TeacherReadOnlyDTO>> getFilteredTeachers(@Nullable @RequestBody TeacherFilters filters)
        throws AppObjectNotAuthorizedException {
        //builder δημιουργεί κενό instance με όλα τα φίλτρα null άρα επιστρέφει όλη τη λίστα αφιλτράριστη
        if (filters==null) TeacherFilters.builder().build();
        return ResponseEntity.ok(teacherService.getTeachersFiltered(filters));
    }

    @PostMapping("/teachers/filtered/paginated")
    public ResponseEntity<Paginated<TeacherReadOnlyDTO>> getTeachersFilteredPaginated
            (@Nullable @RequestBody TeacherFilters filters) throws AppObjectNotAuthorizedException {
        if(filters==null) TeacherFilters.builder().build();
        return ResponseEntity.ok(teacherService.getTeachersFilteredPaginated(filters));
    }

}
