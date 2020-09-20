package com.mybusiness.myrequirementmodule.spring.service.cep;

import com.mybusiness.myrequirementmodule.model.dto.CEPDto;

public interface CEPService {

    CEPDto getEndereco(String cep);

}
