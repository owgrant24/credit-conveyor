package com.example.deal.db.entity;

import com.example.deal.db.enums.CreditStatus;
import com.example.deal.dto.response.PaymentScheduleElementDTO;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "credits")
public class Credit {
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @OneToOne(mappedBy = "credit")
    private Application application;
    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    @Type(type = "jsonb")
    private List<PaymentScheduleElementDTO> paymentSchedule;
    @Column(name = "insurance_enable")
    private Boolean isInsuranceEnabled;
    @Column(name = "salary_client")
    private Boolean isSalaryClient;
    @Enumerated(EnumType.STRING)
    private CreditStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Credit credit = (Credit) o;
        return id != null && Objects.equals(id, credit.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
