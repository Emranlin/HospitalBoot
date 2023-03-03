package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Department;
import peaksoft.service.DepartmentService;

@Controller
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;



    @GetMapping("/{hospitalId}")
    public String getAll(@PathVariable("hospitalId") Long hospitalId, Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments(hospitalId));
        model.addAttribute("hospitalId", hospitalId);
        return "department/departments";
    }

    @GetMapping("/{hospitalId}/new")
    public String create(Model model, @PathVariable("hospitalId") Long hospitalId) {
        model.addAttribute("newDepartment", new Department());
        model.addAttribute("hospitalId", hospitalId);
        return "department/savePage";
    }


    @PostMapping("/{hospitalId}/save")
    public String save(@PathVariable("hospitalId") Long hospitalId,  Department department){
        departmentService.save(hospitalId, department);
            return "redirect:/departments/" + hospitalId;
        }


    @GetMapping("/{hospitalId}/{departmentId}/edit")
    public String getUpdate(Model model,@PathVariable ("departmentId") Long departmentId
            ,@PathVariable("hospitalId") Long hospitalId) {
        model.addAttribute( departmentService.getDepartmentById(departmentId));
        model.addAttribute("hospitalId",hospitalId);
        return "department/edit";
    }


    @PostMapping("/{hospitalId}/{departmentId}/update")



    public String update(@PathVariable Long hospitalId,@PathVariable Long departmentId,
                         @ModelAttribute("department") Department department) {
        departmentService.updateDepartment(departmentId,department);
        return "redirect:/departments/"+hospitalId;


        }

    @GetMapping("/{hospitalId}/{departmentId}/delete")
    public String delete(@PathVariable("hospitalId") Long hospitalId, @PathVariable("departmentId") Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return "redirect:/departments/"+hospitalId;
    }
}