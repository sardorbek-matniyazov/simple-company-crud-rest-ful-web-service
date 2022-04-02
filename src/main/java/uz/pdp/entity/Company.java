package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "company name is required")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "director name is required")
    @Column(nullable = false)
    private String directorName;

    @NotNull(message = "address is required")
    @OneToOne(optional = false)
    private Address address;
}
