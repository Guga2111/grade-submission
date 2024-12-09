package com.ltp.gradesubmission;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class GradeController {

    List<Grade> studentsGrades = new ArrayList<>();

    @GetMapping("/")
    public String gradeForm(Model model, @RequestParam(required = false) String id){
        Grade grade;

        if(getGradeIndex(id) == Constants.NOT_FOUND){
            grade = new Grade();
        } else{
            grade = studentsGrades.get(getGradeIndex(id));
        }

        model.addAttribute("grade", grade);
        return "form";
    }

    @PostMapping("/handleSubmit")
    public String submitForm(Grade grade){

        if(getGradeIndex(grade.getId()) == Constants.NOT_FOUND){
            studentsGrades.add(grade);
        }
        else{
            studentsGrades.set(getGradeIndex(grade.getId()), grade);
        }

        return "redirect:/grades";
    }

    @GetMapping("/grades")
    public String getGrades(Model model){
        model.addAttribute("grades", studentsGrades);
        return "grades";
    }

    public Integer getGradeIndex(String id){
        for( int i = 0; i < studentsGrades.size(); i++){
            if(studentsGrades.get(i).getId().equals(id)) return i;
        }
        return Constants.NOT_FOUND;

    }
}
