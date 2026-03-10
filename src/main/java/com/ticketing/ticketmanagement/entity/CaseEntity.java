package com.ticketing.ticketmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cases")
public class CaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caseTitle;

    @Enumerated(EnumType.STRING)
    private CaseSeverity severity;

    @Enumerated(EnumType.STRING)
    private CaseStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public CaseSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(CaseSeverity severity) {
        this.severity = severity;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }
}
