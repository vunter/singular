package com.mybusiness.myrequirementmodule.spring.service;


import com.mybusiness.myrequirementmodule.model.dto.CEPDto;
import com.mybusiness.myrequirementmodule.spring.service.cep.CEPService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class EnderecoCustomService {

    @Inject
    private CEPService cepService;

    public CEPDto getByCep(String cep) {
        if (!cep.trim().isEmpty()) {
            String cepTratado = cep.replace(".", "").replace("-", "");

            return cepService.getEndereco(cepTratado);
        }
        return new CEPDto();
    }

}
