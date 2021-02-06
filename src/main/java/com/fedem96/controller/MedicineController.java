package com.fedem96.controller;

import com.fedem96.dao.MedicineDao;
import com.fedem96.dto.MedicineDto;
import com.fedem96.mapper.MedicineMapper;
import com.fedem96.model.Company;
import com.fedem96.model.Medicine;
import com.fedem96.model.ModelFactory;
import com.fedem96.model.Principle;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicineController {

    @Inject
    private MedicineDao medicineDao;
    @Inject
    private MedicineMapper medicineMapper;

    @Transactional
    public Map<Long, Medicine> addMedicines(Table tab, Map<Long, Company> mapCompanies, Map<String, Principle> mapPrinciples){
        Map<Long, Medicine> map = new HashMap<>();
        for (Row row: tab){
            long code = row.getInt("sm_field_codice_farmaco");
            Medicine medicine = medicineDao.findByCode(code);
            if(medicine == null){
                medicine = ModelFactory.medicine();
            }
            medicine.setCode(code);
            medicine.setDescription(row.getString("sm_field_descrizione_farmaco"));
            medicine.setLinkFi(row.getString("sm_field_link_fi"));
            medicine.setLinkRcp(row.getString("sm_field_link_rcp"));
            medicine.setCompany(mapCompanies.get((long) row.getInt("sm_field_codice_ditta")));
            medicine.setPrinciple(mapPrinciples.get(row.getString("sm_field_codice_atc")));
            medicineDao.save(medicine);
            map.put(code, medicine);
        }
        return map;
    }

    public List<Medicine> search(String medicine){
        return medicineDao.search(medicine);
    }

    public MedicineDto getById(Long medicineId) {
        return medicineMapper.convert(medicineDao.findById(medicineId));
    }

    public List<MedicineDto> getAllMedicines() {
        return medicineMapper.convert(medicineDao.getAll());
    }
}