package com.mybusiness.myrequirementmodule.spring.service.cep;

import com.mybusiness.myrequirementmodule.model.dto.CEPDto;
import org.springframework.web.client.RestTemplate;

public class ViaCEPService implements CEPService {

    @Override
    public CEPDto getEndereco(String cep) {
        String uri = "http://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, CEPDto.class);
    }
}
