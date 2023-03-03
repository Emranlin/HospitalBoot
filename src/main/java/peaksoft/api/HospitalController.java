package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Hospital;
import peaksoft.service.DoctorService;
import peaksoft.service.HospitalService;
import peaksoft.service.PatientService;

@Controller
@RequestMapping("/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private final PatientService patientService;


    @GetMapping
    public String getAll(Model model, @RequestParam(value = "keyWord", required = false) String keyWord) {
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("hospitals", hospitalService.getAllHospital(keyWord));
        return "hospitals";
    }


    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("newHospital", new Hospital());
        return "savePage";
    }

    @GetMapping("/details/{hospitalId}")
    public String details(@PathVariable("hospitalId") Long hospitalId, Model model) {
        model.addAttribute("hospital", hospitalService.getHospitalById(hospitalId));
        model.addAttribute("doctorsCount", doctorService.getAllDoctors(hospitalId).size());
        model.addAttribute("patientCount", patientService.getAllPatients(hospitalId).size());
        return "details";
    }

    @PostMapping("/save")
    public String saveHospital(@ModelAttribute("newHospital") Hospital hospital) {
        hospitalService.save(hospital);
        return "redirect:/hospitals";
    }

    @GetMapping("{hospitalId}/delete")
    public String deleteHospital(@PathVariable("hospitalId") Long id) {
        hospitalService.deleteHospital(id);
        return "redirect:/hospitals";
    }

    @GetMapping("/{hospitalId}/edit")
    public String edit(Model model, @PathVariable("hospitalId") Long id) {
        model.addAttribute("hospital", hospitalService.getHospitalById(id));
        return "edit";

    }

    @PostMapping("/{hospitalId}/update")
    public String updateHospital(@PathVariable("hospitalId") Long id, @ModelAttribute("hospital") Hospital hospital) {
        hospitalService.updateHospital(id, hospital);
        return "redirect:/hospitals";

    }

}

