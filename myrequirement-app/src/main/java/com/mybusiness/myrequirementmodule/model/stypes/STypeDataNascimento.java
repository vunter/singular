package com.mybusiness.myrequirementmodule.model.stypes;

import com.mybusiness.myrequirementmodule.form.MyRequirementPackage;
import com.mybusiness.myrequirementmodule.model.instances.SIDataNascimento;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeDate;

import java.util.Date;

@SInfoType(
        name = "LocalDate",
        spackage = MyRequirementPackage.class
)
public class STypeDataNascimento extends STypeComposite<SIDataNascimento> {

    public STypeDate date;

    public STypeDataNascimento() {
        super(SIDataNascimento.class);
    }

    @Override
    protected void onLoadType(TypeBuilder tb) {
        this.date = addFieldDate("date");

        this.date.asAtr()
                .label("Data Nascimento")
                .asAtrBootstrap()
                .colPreference(12);
        this.date.maxDate(new Date());
    }

}