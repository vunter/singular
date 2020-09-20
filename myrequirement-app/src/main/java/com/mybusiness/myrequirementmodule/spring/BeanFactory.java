package com.mybusiness.myrequirementmodule.spring;

import com.mybusiness.myrequirementmodule.spring.service.cep.CEPService;
import com.mybusiness.myrequirementmodule.spring.service.cep.IBGECEPService;
import com.mybusiness.myrequirementmodule.spring.service.cep.ViaCEPService;
import org.opensingular.lib.commons.base.SingularProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanFactory {

    @Bean
    public CEPService cepService() {
        String cepType = SingularProperties.get("singular.cep.type");
        if ("ViaCEPService".equals(cepType)) {
            return new ViaCEPService();
        } else {
            return new IBGECEPService();
        }
    }

}
