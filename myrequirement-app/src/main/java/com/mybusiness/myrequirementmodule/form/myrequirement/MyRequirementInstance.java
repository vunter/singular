package com.mybusiness.myrequirementmodule.form.myrequirement;

import org.opensingular.form.SIComposite;

public class MyRequirementInstance extends SIComposite {


    public MyRequirementInstance() {
        //Empty constructor
    }

    @Override
    public MyRequirementForm getType() {
        return (MyRequirementForm) super.getType();
    }

}
