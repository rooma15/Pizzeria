package by.lobovich.delivery.entity;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "addresses")
@Proxy
public class Address extends BaseEntity {

    @Column(name = "city")
    private String cityName;

    @Column(name = "street")
    private String streetName;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "stage_number")
    private String stageNumber;

    @Column(name = "flat_number")
    private String flatNumber;

    @Column(name = "entrance_number")
    private String entranceNumber;

    @ManyToOne
    @JoinColumn(name = "personal_info_id")
    private PersonalInfo personalInfo;

    public String getFullAddress() {
        return cityName + ", st. " + streetName + ", " + houseNumber
                + " - " + flatNumber + ", ent. " + entranceNumber
                + ", fl. " + stageNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(cityName, address.cityName) &&
                Objects.equals(streetName, address.streetName) &&
                Objects.equals(houseNumber, address.houseNumber) &&
                Objects.equals(stageNumber, address.stageNumber) &&
                Objects.equals(flatNumber, address.flatNumber) &&
                Objects.equals(entranceNumber, address.entranceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityName, streetName, houseNumber, stageNumber, flatNumber, entranceNumber);
    }
}
