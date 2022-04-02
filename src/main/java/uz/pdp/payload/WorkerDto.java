package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.entity.Address;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {
    @NotBlank(message = "Worker name is required")
    private String name;
    @NotBlank(message = "Worker phoneNumber is required")
    private String phoneNumber;
    @NotNull(message = "Worker address is required")
    private Address address;
    @NotNull(message = "Department of worker is required")
    private Integer departmentId;
}
