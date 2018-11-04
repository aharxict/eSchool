package academy.softserve.eschool.controller;

import academy.softserve.eschool.dto.ClassDTO;
import academy.softserve.eschool.dto.NYTransitionDTO;
import academy.softserve.eschool.service.ClassServiceImpl;
import academy.softserve.eschool.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students/transition")
@Api(value = "transition", description = "Endpoints for transition to new school year")
public class NYTransitionController {
    @Autowired ClassServiceImpl classService;
    @Autowired StudentService studentService;

    @ApiOperation(value = "Add new classes based on currently classes with new year and name")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Boolean addNewYearClasses(){
        classService.addNewYearClasses();
        return true;
    }


    @ApiOperation(value = "Get true active classes with students", response = ClassDTO.class)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ClassDTO> getActiveClassesWithStudents() {
        return classService.getActiveClassesWithStudents();
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Binding students to new classes, deactivate previous year classes")
    public List<NYTransitionDTO> bindingStudentsToNewClasses(@RequestBody List<NYTransitionDTO> transitionDTOS){
        classService.updateClassStatusById(transitionDTOS, false);
        studentService.studentClassesRebinding(transitionDTOS);
        return transitionDTOS;
    }
}