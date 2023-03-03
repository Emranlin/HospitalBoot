package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Appointment;
import peaksoft.service.AppointmentService;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;
import peaksoft.service.PatientService;



@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final DepartmentService departmentService;



    @GetMapping("/{hospitalId}")
    public String getAll(@PathVariable Long hospitalId, Model model){
        model.addAttribute("appointments", appointmentService.getAllAppointmentByHospital(hospitalId));
        return "appointment/appointments";
    }

    @GetMapping("/{hospitalId}/new")
    public String create(@PathVariable Long hospitalId, Model model){
        model.addAttribute("newAppointment", new Appointment());
        model.addAttribute("doctors", doctorService.getAllDoctors(hospitalId));
        model.addAttribute("patients", patientService.getAllPatients(hospitalId));
        model.addAttribute("departments", departmentService.getAllDepartments(hospitalId));
        model.addAttribute(hospitalId);
        return "appointment/savePage";
    }

    @PostMapping("/{hospitalId}/save")
    public String save(@PathVariable Long hospitalId, @ModelAttribute("appointment") Appointment appointment){
        appointmentService.save(hospitalId, appointment);
        return "redirect:/appointments/"+hospitalId;
    }
    @GetMapping("/{hospitalId}/{appointmentId}/edit")
    public String getUpdateFrom(Model model,@PathVariable("appointmentId") Long appointmentId,@PathVariable("hospitalId")Long hospitalId) {
        model.addAttribute("appointment", appointmentService.getAppointmentById(appointmentId));
        model.addAttribute("doctors", doctorService.getAllDoctors(hospitalId));
        model.addAttribute("patients", patientService.getAllPatients(hospitalId));
        model.addAttribute("departments", departmentService.getAllDepartments(hospitalId));
        model.addAttribute("hospitalId", hospitalId);
        return "appointment/edit";
    }
    @PostMapping("/{hospitalId}/{appointmentId}/update")
    public String update(@PathVariable("hospitalId")Long hospitalId,@PathVariable("appointmentId")Long appointmentId,@ModelAttribute("appointment") Appointment appointment){
        appointmentService.updateAppointment(appointmentId,appointment);
        return "redirect:/appointments/"+hospitalId;
    }

    @GetMapping("/{hospitalId}/{appointmentId}/delete")
    public String delete(@PathVariable("hospitalId") Long hospitalId,@PathVariable("appointmentId") Long appointmentId,
                         @ModelAttribute("appointment") Appointment appointment){
        appointmentService.deleteAppointment(appointmentId,hospitalId );
        return "redirect:/appointments/"+hospitalId;
    }
}