package com.mybusiness.myrequirementmodule.model.instances;

import com.mybusiness.myrequirementmodule.model.stypes.STypePessoa;
import org.opensingular.form.SIComposite;

public class SIPessoa extends SIComposite {


    public SIPessoa() {
        //Empty Constructor
    }

    @Override
    public STypePessoa getType() {
        return (STypePessoa) super.getType();
    }

}
