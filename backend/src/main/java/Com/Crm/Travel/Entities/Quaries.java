package Com.Crm.Travel.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Com.Crm.Travel.common.enums.QuariesPriority;
import Com.Crm.Travel.common.enums.QuariesStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "quaries")
@Builder
public class Quaries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serialNumber;
    private String requesterName;
    private String contactNo;
    private String email;
    private ArrayList<String> Destination;
    private String fromLocation;
    private Date fromDate;
    private Date toDate;
    private Long quotedPrice;
    private ArrayList<String> requiredServices;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id")
    private AppUser appUser;

    @Enumerated(EnumType.STRING)
    private QuariesStatus quariesStatus;

    @Enumerated(EnumType.STRING)
    private QuariesPriority quariesPriority;

    @OneToMany(mappedBy = "quarie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuariesComment> comments = new ArrayList<>();

    protected void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

}
