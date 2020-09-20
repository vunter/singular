package com.mybusiness.myrequirementmodule.model.instances;

import com.mybusiness.myrequirementmodule.model.stypes.STypePessoa;
import org.opensingular.form.SIComposite;
import org.opensingular.form.type.core.SIDate;
import org.opensingular.form.type.core.SIInteger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SIPessoa extends SIComposite {


    public SIPessoa() {
        //Empty Constructor
    }

    @Override
    public STypePessoa getType() {
        return (STypePessoa) super.getType();
    }

    public SIDate getDataNascimento() {
        return this.getDescendant(this.getType().dataNascimento);
    }

    public SIInteger getIdade() {
        return this.getDescendant(this.getType().idade);
    }

    public void preencheIdade(Date data) {
        if (data != null) {
            DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            int currentYear = Integer.parseInt(formatter.format(new Date()));
            int bithYear = Integer.parseInt(formatter.format(data));
            getIdade().setValue((currentYear - bithYear) / 10000);
        }
    }
}
