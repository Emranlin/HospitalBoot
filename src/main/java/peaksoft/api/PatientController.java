package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Patient;
import peaksoft.enums.Gender;
import peaksoft.service.DepartmentService;
import peaksoft.service.PatientService;

@Controller
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final DepartmentService departmentService;


    @GetMapping("/{hospitalId}")
    public String getAll(@PathVariable Long hospitalId, Model model){
        model.addAttribute("patients", patientService.getAllPatients(hospitalId));
        return "patient/patients";
    }

    @GetMapping("/{hospitalId}/new")
    public String createNewPatient(@PathVariable("hospitalId") Long hospitalId, Model model){
        model.addAttribute("newPatient", new Patient());
        model.addAttribute(hospitalId);
        model.addAttribute("departments",departmentService.getAllDepartments(hospitalId));
        return "patient/savePage";
    }

    @PostMapping("/{hospitalId}/save")
    public String savePatient(@PathVariable Long hospitalId, @ModelAttribute("patient")  Patient patient ) {
       patientService.save(hospitalId, patient);
       return "redirect:/patients/"+hospitalId;
    }


    @GetMapping("/{hospitalId}/{patientId}/edit")
    private String getUpdateForm(  Model model,@PathVariable("patientId") Long patientId, @PathVariable("hospitalId") Long hospitalId){
        model.addAttribute("patient", patientService.getPatientById(patientId));
        model.addAttribute("hospitalId",hospitalId);
        model.addAttribute("genders", Gender.values());
        return "patient/edit";
    }
    @PostMapping("/{hospitalId}/{patientId}/update")
    private String updatePatient(@PathVariable("hospitalId") Long hospitalId,
                                 @PathVariable("patientId") Long patientId,
                                 @ModelAttribute("patient") Patient patient ) {
        patientService.updatePatient(patientId,patient);
        return "redirect:/patients/"+hospitalId;
    }

    @GetMapping("/{hospitalId}/{patientId}/delete")
    public String deletePatient(@PathVariable("hospitalId") Long hospitalId,
                                @PathVariable("patientId") Long patientId){
        patientService.deletePatient(patientId);
        return "redirect:/patients/"+hospitalId;
    }

}