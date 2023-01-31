package com.example.deal.db.entity;

import com.example.deal.db.enums.Gender;
import com.example.deal.db.enums.Marital;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @OneToOne(mappedBy = "client")
    private Application application;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthdate;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Marital maritalStatus;
    private Integer dependentAmount;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Passport passport;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Employment employment;
    private String account;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Client client = (Client) o;
        return id != null && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
