package br.com.deanx.recruitingprocessboot.offers;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Offer {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String jobTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate startDate;

    @Transient
    private int numberOfApplications;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(id, offer.id) || Objects.equals(jobTitle, offer.jobTitle);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getNumberOfApplications() {
        return numberOfApplications;
    }

    public void setNumberOfApplications(int numberOfApplications) {
        this.numberOfApplications = numberOfApplications;
    }

    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
