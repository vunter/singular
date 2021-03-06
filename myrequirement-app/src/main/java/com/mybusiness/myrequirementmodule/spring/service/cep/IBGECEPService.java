package com.mybusiness.myrequirementmodule.spring.service.cep;

import com.mybusiness.myrequirementmodule.model.dto.CEPDto;
import org.springframework.web.client.RestTemplate;

public class IBGECEPService implements CEPService {

    @Override
    public CEPDto getEndereco(String cep) {
        String uri = "http://ibgecep.gov.br/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, CEPDto.class);
    }
}
