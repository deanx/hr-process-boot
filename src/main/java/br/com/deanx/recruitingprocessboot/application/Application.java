package br.com.deanx.recruitingprocessboot.application;

import br.com.deanx.recruitingprocessboot.offers.Offer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name="applications",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"offer_id", "email"})
        }
)
public class Application {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="offer_id")
    private Offer offer;

    @Email
    private String email;

    private String resume;

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public enum applicationStatus {APPLIED, INVITED, REJECTED, HIRED}

    @Enumerated(EnumType.STRING)
    private applicationStatus applicationStatus;

    public Application.applicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Application.applicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

}
