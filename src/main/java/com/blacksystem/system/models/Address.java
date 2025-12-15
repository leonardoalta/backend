package com.blacksystem.system.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "address" )
@Getter
@Setter

public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Long idAddress;

    @Column(name = "name_street_user")
    private String nameStreetUser;

    @Column(name = "number_house_user", length = 50)
    private String numberHouseUser;

    @Column(name = "name_district_user")
    private String nameDistrictUser;

    @Column(name = "zip_code_user", length = 20)
    private String zipCodeUser;


    public Address() {}

    public Long getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Long idAddress) {
        this.idAddress = idAddress;
    }

    public String getNameStreetUser() {
        return nameStreetUser;
    }

    public void setNameStreetUser(String nameStreetUser) {
        this.nameStreetUser = nameStreetUser;
    }

    public String getNumberHouseUser() {
        return numberHouseUser;
    }

    public void setNumberHouseUser(String numberHouseUser) {
        this.numberHouseUser = numberHouseUser;
    }

    public String getNameDistrictUser() {
        return nameDistrictUser;
    }

    public void setNameDistrictUser(String nameDistrictUser) {
        this.nameDistrictUser = nameDistrictUser;
    }

    public String getZipCodeUser() {
        return zipCodeUser;
    }

    public void setZipCodeUser(String zipCodeUser) {
        this.zipCodeUser = zipCodeUser;
    }

    @Override
    public String toString() {
        return "Address{" +
                "idAddress=" + idAddress +
                ", nameStreetUser='" + nameStreetUser + '\'' +
                ", numberHouseUser='" + numberHouseUser + '\'' +
                ", nameDistrictUser='" + nameDistrictUser + '\'' +
                ", zipCodeUser='" + zipCodeUser + '\'' +
                '}';
    }
}
