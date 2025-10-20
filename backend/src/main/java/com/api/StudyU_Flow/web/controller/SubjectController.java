package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.request.StudentSubjectRecordRequestDto;
import com.api.StudyU_Flow.domain.dto.request.SubjectAndRecordRequestDto;
import com.api.StudyU_Flow.domain.dto.request.SubjectRequestDto;
import com.api.StudyU_Flow.domain.dto.response.StudentSubjectRecordResponseDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectAndRecordResponseDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectResponseDto;
import com.api.StudyU_Flow.domain.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
@Tag(name = "Subjects and Records", description = "CRUD Subject/Record and additional information")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * @description
     * ES: Endpoint para crear una nueva Asignatura.
     * EN: Endpoint to create a new Subject.
     */
    @PostMapping
    @Operation(
            summary = "Create a new Subject",
            description = "return SubjectResponseDto",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Subject created successfully")
            }
    )
    ResponseEntity<SubjectResponseDto> add(
            @Parameter(description = "Subject data")
            @RequestBody @Valid SubjectRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.subjectService.add(requestDto));
}

    /**
     * @description
     * ES: Endpoint para crear una lista de Asignaturas.
     * EN: Endpoint to create a list Subjects.
     */
    @PostMapping("/addSubjectList")
    @Operation(
            summary = "Create several Subjects",
            description = "return List<SubjectResponseDto>",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Subjects created successfully")
            }
    )
    ResponseEntity<List<SubjectResponseDto>> addSubjectList(
            @Parameter(description = "Subjects data")
            @RequestBody @Valid List<SubjectRequestDto> requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.subjectService.addSubjectList(requestDto));
    }

    /**
     * @description
     * ES: Endpoint para crear un record y asignarlo a una asignatura.
     * EN: Endpoint to create a new record and assign it to a subject.
     */
    @PostMapping("/record/add/{username}/{idSubject}")
    @Operation(
            summary = "Create a new record",
            description = "return StudentSubjectRecordResponseDto",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Record created successfully")
            }
    )
    ResponseEntity<StudentSubjectRecordResponseDto> addRecordToSubject(
            @Parameter(description = "Student username") @PathVariable String username,
            @Parameter(description = "Subject id") @PathVariable Long idSubject,
            @Parameter(description = "Data for Subject Record") @RequestBody @Valid StudentSubjectRecordRequestDto requestDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.subjectService.addRecordToSubject(username, idSubject, requestDto));
    }

    /**
     * @description
     * ES: Endpoint para actualizar los datos del record de una materia.
     * EN: Endpoint to update the data of a subject record.
     */
    @PutMapping("/record/update/{idRecord}")
    @Operation(
            summary = "Update a record",
            description = "return StudentSubjectRecordResponseDto",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Record updated successfully")
            }
    )
    ResponseEntity<StudentSubjectRecordResponseDto> updateRecord(
            @Parameter(description = "Record id") @PathVariable Long idRecord,
            @Parameter(description = "Data for Subject Record") @RequestBody @Valid StudentSubjectRecordRequestDto requestDto
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.subjectService.updateRecord(idRecord, requestDto));
    }

    /**
     * @description
     * ES: Endpoint para crear una nueva Asignatura y su Record con la informacion detallada.
     * EN: Endpoint to create a new Subject and his Record with detail information.
     */
    @PostMapping("/addSubjectAndRecord")
    @Operation(
            summary = "Create a new Subject and Record",
            description = "return SubjectAndRecordResponseDto",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Subject and Record created successfully")
            }
    )
    ResponseEntity<SubjectAndRecordResponseDto> addSubjectAndRecord(
            @Parameter(description = "Combined data for subject and record")
            @RequestBody @Valid SubjectAndRecordRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.subjectService.add(requestDto));
    }

    /**
     * @description
     * ES: Endpoint para crear una lista de Asignaturas y sus Records con la informacion detallada.
     * EN: Endpoint to create a list of Subject and their Records with detail information.
     */
    @PostMapping("/addSubjectAndRecord/List")
    @Operation(
            summary = "Create a list of Subjects and Records",
            description = "return List<SubjectAndRecordResponseDto>",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Subjects and Records created successfully")
            }
    )
    ResponseEntity<List<SubjectAndRecordResponseDto>> addSubjectAndRecordList(
            @Parameter(description = "List of combined data for subject and record")
            @RequestBody @Valid List<SubjectAndRecordRequestDto> requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.subjectService.addSubjectAndRecordList(requestDto));
    }

    /**
     * @description
     * ES: Endpoint para obtener todas las Asignaturas de un estudiante por su nombre de usuario y el id de la carrera que cursa.
     * EN: Endpoint to get all Subjects for a student by username and Student degree id.
     */
    @GetMapping("/{username}/{idStudentDegree}")
    @Operation(
            summary = "Get all Subjects by student",
            description = "Returns a list of SubjectResponseDto for a given student and degree id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Subjects found"),
                    @ApiResponse(responseCode = "204", description = "No subjects found")
            }
    )
    ResponseEntity<List<SubjectResponseDto>> getAllByStudent(
            @Parameter(description = "Student username")
            @PathVariable String username,
            @Parameter(description = "StudentDegree id")
            @PathVariable Long idStudentDegree){

        List<SubjectResponseDto> subjectResponseDtos = this.subjectService.getAllByStudent(username, idStudentDegree);

        if(subjectResponseDtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(subjectResponseDtos);
    }

    /**
     * @description
     * ES: Endpoint para obtener todas las Asignaturas y sus Records de un estudiante por su nombre de usuario y el id de la carrera que cursa.
     * EN: Endpoint to get all Subjects and their Records for a student by username and Student degree id.
     */
    @GetMapping("/getAll/{username}/{idStudentDegree}")
    @Operation(
            summary = "Get all Subjects and Records by student",
            description = "Returns a list of SubjectAndRecordResponseDto for a given student and degree id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Subjects and records found"),
                    @ApiResponse(responseCode = "204", description = "No subjects or records found")
            }
    )
    ResponseEntity<List<SubjectAndRecordResponseDto>> getAllSubjectsAndRecordsByStudent(
            @Parameter(description = "Student username")
            @PathVariable String username,
            @Parameter(description = "StudentDegree id")
            @PathVariable Long idStudentDegree){

        List<SubjectAndRecordResponseDto> subjectAndRecordResponseDtos = this.subjectService
                .getAllSubjectsAndRecordsByStudent(username, idStudentDegree);

        if(subjectAndRecordResponseDtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(subjectAndRecordResponseDtos);
    }

    /**
     * @description
     * ES: Endpoint para obtener todas las Asignaturas de un semestre especifico
     * y sus Records de un estudiante por su nombre de usuario y el id de la carrera que cursa.
     * EN: Endpoint to get all Subjects of a specific semester and their Records for a student by username and Student degree id.
     */
    @GetMapping("/getAll/{username}/{idStudentDegree}/{nSemester}")
    @Operation(
            summary = "Get all Subjects and Records by student and a specific semester",
            description = "Returns a list of SubjectAndRecordResponseDto for a given student and degree id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Subjects and records found"),
                    @ApiResponse(responseCode = "204", description = "No subjects or records found")
            }
    )
    ResponseEntity<List<SubjectAndRecordResponseDto>> getAllSubjectsAndRecordsBySemester(
            @Parameter(description = "Student username")
            @PathVariable String username,
            @Parameter(description = "StudentDegree id")
            @PathVariable Long idStudentDegree,
            @Parameter(description = "int nSemestre")
            @PathVariable Integer nSemester){

        List<SubjectAndRecordResponseDto> subjectAndRecordResponseDtos = this.subjectService
                .getAllSubjectsAndRecordsBySemester(username, idStudentDegree, nSemester);

        if(subjectAndRecordResponseDtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(subjectAndRecordResponseDtos);
    }

    /**
     * @description
     * ES: Endpoint para obtener la Asignatura y su Record por el id de la asignatura.
     * EN: Endpoint to get the Subject and its Record by subject id.
     */
    @GetMapping("/{idSubject}")
    @Operation(
            summary = "Get Subject and Record by id",
            description = "Returns a SubjectAndRecordResponseDto for the given subject id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Subject and record found")
            }
    )
    ResponseEntity<SubjectAndRecordResponseDto> getSubjectAndRecordByIdSubject(
            @Parameter(description = "Subject id") @PathVariable Long idSubject){
        return ResponseEntity.ok(this.subjectService.getSubjectAndRecordByIdSubject(idSubject));
    }

    /**
     * @description
     * ES: Endpoint para eliminar una Asignatura y su Record por el id de la asignatura.
     * EN: Endpoint to delete the Subject and its Record by subject id.
     */
    @DeleteMapping("/{idSubject}")
    @Operation(
            summary = "Delete Subject and Record by id",
            description = "Deletes the subject and its record for the given subject id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Subject and record deleted successfully")
            }
    )
    ResponseEntity<Void> deleteSubjectAndRecordByIdSubject(
            @Parameter(description = "Subject id") @PathVariable Long idSubject){
        this.subjectService.deleteSubjectAndRecordByIdSubject(idSubject);
        return ResponseEntity.ok().build();
    }

}
