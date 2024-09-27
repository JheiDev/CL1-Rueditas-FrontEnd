package pe.edu.cibertec.CL1_Rueditas_FrontEnd.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.CL1_Rueditas_FrontEnd.DTO.RueditasRequestDTO;
import pe.edu.cibertec.CL1_Rueditas_FrontEnd.DTO.RueditasResponseDTO;
import pe.edu.cibertec.CL1_Rueditas_FrontEnd.ViewModel.RueditasModel;

@Controller
@RequestMapping("/vehiculos")
public class RueditasController {

    @Autowired
     RestTemplate restTemplate;

    @GetMapping("/buscar")
    public String buscar(Model model){
        RueditasModel rueditasModel = new RueditasModel("00", "", "",
                "", "", "", "");
        model.addAttribute("rueditasModel", rueditasModel);
        return "Inicio";
    }

    @PostMapping("/validation")
    public String validation(@RequestParam("placa") String placa, Model model){
        if (placa == null || placa.trim().length() == 0){
            RueditasModel rueditasModel = new RueditasModel("01", "Error: Ingrese una placa valida", "",
                    "", "", "", "");
            return "Inicio";
        }

    String endpoint = "http://localhost:8081/vehiculo/inicio";
    RueditasRequestDTO rueditasRequestDTO = new RueditasRequestDTO(placa);
        try {
            RueditasResponseDTO responseDTO = restTemplate.postForObject(endpoint, rueditasRequestDTO, RueditasResponseDTO.class);

            if (responseDTO != null && "00".equals(responseDTO.codigo())) {
                RueditasModel rueditasModel = new RueditasModel(
                        "00", "", responseDTO.marca(), responseDTO.modelo(), responseDTO.nroAsientos(),
                        responseDTO.precio(), responseDTO.color());
                model.addAttribute("rueditasModel", responseDTO);
                return "Detalles";

            } else if (responseDTO != null && "01".equals(responseDTO.codigo())) {
                model.addAttribute("rueditasModel", new RueditasModel("01", "Placa no encontrada", "", "", "", "", ""));
                return "Inicio";

            } else {
                model.addAttribute("rueditasModel", new RueditasModel("01", "Ingrese una placa válida", "", "", "", "", ""));
                return "Inicio";
            }

        } catch (RestClientException e) {
            model.addAttribute("rueditasModel", new RueditasModel("99", "Error al buscar el vehículo", "", "", "", "", ""));
            return "Inicio";
        }

    }

}
