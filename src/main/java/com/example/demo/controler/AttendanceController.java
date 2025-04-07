package com.example.demo.controller;

import com.example.demo.model.Attendance;
import com.example.demo.model.Student;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AttendanceController {
    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/attendance/{id}")
    public String showAttendanceForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentRepo.findById(id).orElse(null));
        return "attendance";
    }

    @PostMapping("/attendance/save")
    public String saveAttendance(@RequestParam Long studentId, @RequestParam String status) {
        Student student = studentRepo.findById(studentId).orElse(null);
        Attendance attendance = new Attendance();
        attendance.setDate(LocalDate.now());
        attendance.setStatus(status);
        attendance.setStudent(student);
        attendanceRepo.save(attendance);
        return "redirect:/";
    }

    @GetMapping("/attendance/history/{id}")
    public String viewAttendanceHistory(@PathVariable Long id, Model model) {
        List<Attendance> records = attendanceRepo.findByStudentId(id);
        model.addAttribute("records", records);
        return "attendance-history";
    }
}
