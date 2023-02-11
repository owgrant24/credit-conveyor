package com.example.deal.db.entity;

import com.example.deal.db.enums.EmploymentStatus;
import com.example.deal.db.enums.Position;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "employments")
public class Employment {
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Enumerated(EnumType.STRING)
    private EmploymentStatus status;
    @Column(name = "employer_inn")
    private String employerINN;
    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    private Position position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Employment that = (Employment) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
