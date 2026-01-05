package Com.Crm.Travel.Entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity

@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "quaries")
public class Quaries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serialNumber;
    private String requesterName;
    private String contactNo;
    private String gmail;
    private String Destination;
    private String fromLocation;
    private Date fromDate;
    private Date toDate;

}
