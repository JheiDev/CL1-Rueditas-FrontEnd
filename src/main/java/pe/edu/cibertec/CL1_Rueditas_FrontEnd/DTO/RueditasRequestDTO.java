package pe.edu.cibertec.CL1_Rueditas_FrontEnd.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RueditasRequestDTO(String placa) {
}
