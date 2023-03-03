package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Hospital;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.HospitalService;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;




    @Override
    public void save(Hospital hospital) {
        hospitalRepository.save(hospital);

    }

    @Override
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    @Override
    public Hospital getHospitalById(Long id) {
        return hospitalRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found by" + id + "in this dataBase"));
    }

    @Override
    public void deleteHospital(Long id) {
        hospitalRepository.deleteById(id);

    }

    @Override
    public void updateHospital(Long id, Hospital newHospital) {
        Hospital hospital = hospitalRepository.findById(id).get();
        hospital.setName(newHospital.getName());
        hospital.setAddress(newHospital.getAddress());
        hospital.setLogo(newHospital.getLogo());
        hospitalRepository.save(hospital);

    }

    @Override
    public List<Hospital> getAllHospital(String keyWord) {
        if(keyWord !=null && !keyWord.trim().isEmpty()){
            return hospitalRepository.getAllBy(keyWord);
        }else {
            return hospitalRepository.findAll();
        }

    }
}
