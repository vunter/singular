package com.mybusiness.myrequirementmodule.spring.service;


import org.opensingular.form.type.country.brazil.STypeAddress;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoCustomService {



    public STypeAddress getByCep(String cep) {
        cep.replace(".", "").replace("-", "");
        String uri = "http://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, STypeAddress.class);
    }

}
