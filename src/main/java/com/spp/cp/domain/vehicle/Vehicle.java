package com.spp.cp.domain.vehicle;

import com.spp.cp.domain.entities.Company;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicle_type")
public class Vehicle {

    private Long id;

    private Company owner;

    private String plateNumber;

    private String frameNumber;

    private Model model;

    private Integer year;

    private Date civilInsuranceVilidity;

    private Date kaskoInsuranceVilidity;

    private Date haulerIsuranceVilidity;

    private Date techicalCheckVilidity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    @ManyToOne
    @JoinColumn(name="model_id", nullable = false)
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Date getCivilInsuranceVilidity() {
        return civilInsuranceVilidity;
    }

    public void setCivilInsuranceVilidity(Date civilInsuranceVilidity) {
        this.civilInsuranceVilidity = civilInsuranceVilidity;
    }

    public Date getKaskoInsuranceVilidity() {
        return kaskoInsuranceVilidity;
    }

    public void setKaskoInsuranceVilidity(Date kaskoInsuranceVilidity) {
        this.kaskoInsuranceVilidity = kaskoInsuranceVilidity;
    }

    public Date getHaulerIsuranceVilidity() {
        return haulerIsuranceVilidity;
    }

    public void setHaulerIsuranceVilidity(Date haulerIsuranceVilidity) {
        this.haulerIsuranceVilidity = haulerIsuranceVilidity;
    }

    public Date getTechicalCheckVilidity() {
        return techicalCheckVilidity;
    }

    public void setTechicalCheckVilidity(Date techicalCheckVilidity) {
        this.techicalCheckVilidity = techicalCheckVilidity;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @ManyToOne
    @JoinColumn(name="owner_company_id", nullable = false)
    public Company getOwner() {
        return owner;
    }

    public void setOwner(Company owner) {
        this.owner = owner;
    }
}
