package uz.pdp.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {
    @NotBlank(message = "Department name is required")
    private String name;
    @NotNull(message = "Company of department is required")
    private Integer companyId;
}
