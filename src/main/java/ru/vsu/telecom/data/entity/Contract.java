package ru.vsu.telecom.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.telecom.data.dbloader.xml.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * @author Pavel_Burdyug
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlSeeAlso({MobileConnectContract.class, WiredInternetContract.class, DigitalTelevisionContract.class})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Contract {
    @XmlAttribute
    private Long id;
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate startDate;
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate endDate;
    private Long contractNumber;
    private Customer customer;

    public Contract(LocalDate startDate, LocalDate endDate, Long contractNumber, Customer customer) {
        this(null, startDate, endDate, contractNumber, customer);
    }
}
