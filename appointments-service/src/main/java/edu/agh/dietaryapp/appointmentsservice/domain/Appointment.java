package edu.agh.dietaryapp.appointmentsservice.domain;

import java.time.OffsetDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    private Long clientId;
    private Long dietitianId;
    private String status;
    @Column(name = "start_date")
    private OffsetDateTime start;
    @Column(name = "end_date")
    private OffsetDateTime end;
    private String title;
    private boolean allDay;

    public Appointment(Long id, Long clientId, Long dietitianId, String status,
                       OffsetDateTime start, OffsetDateTime end, String title, boolean allDay) {
        this.id = id;
        this.clientId = clientId;
        this.dietitianId = dietitianId;
        this.status = status;
        this.start = start;
        this.end = end;
        this.title = title;
        this.allDay = allDay;
    }

    public Appointment() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long userId) {
        this.clientId = userId;
    }

    public Long getDietitianId() {
        return dietitianId;
    }

    public void setDietitianId(Long dietitianId) {
        this.dietitianId = dietitianId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OffsetDateTime getStart() {
        return start;
    }

    public void setStart(OffsetDateTime startDate) {
        this.start = startDate;
    }

    public OffsetDateTime getEnd() {
        return end;
    }

    public void setEnd(OffsetDateTime endDate) {
        this.end = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String info) {
        this.title = info;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }
}
