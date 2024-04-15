package dev.chatree.smarthomeapi.service;

import dev.chatree.smarthomeapi.entity.WarrantyEntity;
import dev.chatree.smarthomeapi.model.warranty.WarrantyRequest;
import dev.chatree.smarthomeapi.model.warranty.WarrantyResponse;
import dev.chatree.smarthomeapi.model.warranty.WarrantyStatus;
import dev.chatree.smarthomeapi.repository.WarrantyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class WarrantyService {

    private final WarrantyRepository warrantyRepository;

    public WarrantyService(WarrantyRepository warrantyRepository) {
        this.warrantyRepository = warrantyRepository;
    }

    public List<WarrantyResponse> getAllWarranty() {
        List<WarrantyEntity> warrantyEntityList = warrantyRepository.findAllByOrderByWarrantyDateAsc();
        List<WarrantyResponse> warrantyResponseList = new ArrayList<>();

        for (WarrantyEntity warrantyEntity : warrantyEntityList) {
            WarrantyResponse warrantyResponse = generateWarrantyResponse(warrantyEntity);
            warrantyResponseList.add(warrantyResponse);
        }

        log.info("Get all warranty done!");
        return warrantyResponseList;
    }

    public WarrantyResponse getWarrantyById(Long id) {
        WarrantyEntity warrantyEntity = warrantyRepository.findById(id).orElse(null);
        if (warrantyEntity == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Warranty not found");
        }

        WarrantyResponse warrantyResponse = generateWarrantyResponse(warrantyEntity);

        log.info("Get warranty by id done!");
        return warrantyResponse;
    }

    public void createWarranty(WarrantyRequest warrantyRequest) {
        WarrantyEntity warrantyEntity = new WarrantyEntity();
        warrantyEntity.setBrand(warrantyRequest.getBrand());
        warrantyEntity.setProductName(warrantyRequest.getProductName());
        warrantyEntity.setProductNumber(warrantyRequest.getProductNumber());
        warrantyEntity.setModel(warrantyRequest.getModel());
        warrantyEntity.setSerialNumber(warrantyRequest.getSerialNumber());
        warrantyEntity.setPurchaseDate(LocalDate.parse(warrantyRequest.getPurchaseDate(), DateTimeFormatter.ISO_DATE));
        warrantyEntity.setWarrantyDate(LocalDate.parse(warrantyRequest.getWarrantyDate(), DateTimeFormatter.ISO_DATE));

        warrantyRepository.save(warrantyEntity);
        log.info("Create warranty done!");
    }

    public void updateWarranty(Long id, WarrantyRequest warrantyRequest) {
        WarrantyEntity warrantyEntity = warrantyRepository.findById(id).orElse(null);
        if (warrantyEntity == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Warranty not found");
        }
        warrantyEntity.setBrand(warrantyRequest.getBrand());
        warrantyEntity.setProductName(warrantyRequest.getProductName());
        warrantyEntity.setProductNumber(warrantyRequest.getProductNumber());
        warrantyEntity.setModel(warrantyRequest.getModel());
        warrantyEntity.setSerialNumber(warrantyRequest.getSerialNumber());
        warrantyEntity.setPurchaseDate(LocalDate.parse(warrantyRequest.getPurchaseDate(), DateTimeFormatter.ISO_DATE));
        warrantyEntity.setWarrantyDate(LocalDate.parse(warrantyRequest.getWarrantyDate(), DateTimeFormatter.ISO_DATE));

        warrantyRepository.save(warrantyEntity);
        log.info("Update warranty done!");
    }

    public void deleteWarranty(Long id) {
        log.info("id: {}", id);
        WarrantyEntity warrantyEntity = warrantyRepository.findById(id).orElse(null);
        if (warrantyEntity == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Warranty not found");
        }
        warrantyRepository.delete(warrantyEntity);
        log.info("Delete warranty done!");
    }

    public void deleteMultipleWarranty(List<Long> ids) {
        log.info("ids: {}", ids);
        warrantyRepository.deleteAllById(ids);
        log.info("Delete multiple warranty done!");
    }

    private WarrantyResponse generateWarrantyResponse(WarrantyEntity warrantyEntity) {
        WarrantyResponse warrantyResponse = new WarrantyResponse();
        warrantyResponse.setId(warrantyEntity.getId());
        warrantyResponse.setBrand(warrantyEntity.getBrand());
        warrantyResponse.setProductName(warrantyEntity.getProductName());
        warrantyResponse.setProductNumber(warrantyEntity.getProductNumber());
        warrantyResponse.setModel(warrantyEntity.getModel());
        warrantyResponse.setSerialNumber(warrantyEntity.getSerialNumber());
        warrantyResponse.setPurchaseDate(warrantyEntity.getPurchaseDate().toString());
        warrantyResponse.setWarrantyDate(warrantyEntity.getWarrantyDate().toString());
        warrantyResponse.setStatus(generateWarrantyStatus(warrantyEntity.getWarrantyDate()));

        return warrantyResponse;
    }

    private WarrantyStatus generateWarrantyStatus(LocalDate warrantyDate) {
        if (warrantyDate.isBefore(LocalDate.now())) {
            return WarrantyStatus.OUT_OF_WARRANTY;
        } else {
            return WarrantyStatus.IN_WARRANTY;
        }
    }
}
