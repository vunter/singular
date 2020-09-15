package com.mybusiness.myrequirementmodule.config;

import org.opensingular.requirement.studio.init.RequirementStudioAppInitializer;

public class MyRequirementModuleInitializer implements RequirementStudioAppInitializer {

    @Override
    public String moduleCod() {
        return "MYREQUIREMENT";
    }

    @Override
    public String[] springPackagesToScan() {
        return new String[]{"com.mybusiness"};
    }

}
