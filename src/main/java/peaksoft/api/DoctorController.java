package peaksoft.api;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Department;
import peaksoft.entity.Doctor;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    public DoctorController(DoctorService doctorService, DepartmentService departmentService) {
        this.doctorService = doctorService;
        this.departmentService = departmentService;
    }
    @GetMapping("/{hospitalId}")
    public String getAll(@PathVariable Long hospitalId, Model model){
        model.addAttribute("doctors",doctorService.getAllDoctors(hospitalId));
        return "doctor/doctors";
    }

    @GetMapping("/{hospitalId}/new")
    public String createNewDoctor( @PathVariable Long hospitalId,Model model){
        model.addAttribute("newDoctor", new Doctor());
        model.addAttribute(hospitalId);
        model.addAttribute("departments",departmentService.getAllDepartments(hospitalId));
        return "doctor/savePage";
    }

    @PostMapping("/{hospitalId}/save")
    public String save(@PathVariable Long hospitalId, @ModelAttribute("doctor")  Doctor doctor ){
        doctorService.save(hospitalId ,doctor);
        return "redirect:/doctors/"+hospitalId;


    }
    @GetMapping("/{hospitalId}/{doctorId}/edit")
    public String getUpdateFrom(Model model,@PathVariable("doctorId") Long doctorId,@PathVariable("hospitalId")Long hospitalId){
        model.addAttribute("doctor", doctorService.getDoctorById(doctorId));
        model.addAttribute("hospitalId",hospitalId);
        return "doctor/edit";
    }
    @PostMapping("/{hospitalId}/{doctorId}/update")
    public String update(@PathVariable("doctorId") Long doctorId,
                         @PathVariable("hospitalId") Long hospitalId,
                         @ModelAttribute("doctor")  Doctor doctor){
        doctorService.updateDoctor(doctorId,doctor);
        return "redirect:/doctors/"+hospitalId;

    }


    @GetMapping("/{hospitalId}/{doctorId}/delete")
    public String delete(@PathVariable("hospitalId") Long hospitalId,@PathVariable("doctorId") Long doctorId){
        doctorService.deleteDoctor(doctorId);
        return "redirect:/doctors/"+hospitalId;
    }
    @GetMapping("/{doctorId}/departments")
    public String assign(Model model,@PathVariable ("doctorId")Long doctorId){
        Doctor doctor = doctorService.getDoctorById(doctorId);
        List<Department> department = doctorService.getAllDepartmentByDoctorId(doctorId);
        model.addAttribute("doctor",doctor);
        model.addAttribute("departments",department);
        return "doctor/departments";
    }

    @PostMapping("/{doctorId}/save")
    public String assignDepartmentToDoctor(@PathVariable ("doctorId")Long doctorId,
                                           @RequestParam ("departmentId")List<Long>departmentId){
        doctorService.assignDepartmentsToDoctor(departmentId,doctorId);
        return "redirect:/doctors/"+doctorService.getDoctorById(doctorId).getHospital().getId();
    }
}