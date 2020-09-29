package com.mybusiness.myrequirementmodule.model.instances;

import com.mybusiness.myrequirementmodule.model.stypes.STypeDataNascimento;
import org.opensingular.form.SIComposite;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class SIDataNascimento extends SIComposite {

    public SIDataNascimento() {
        //Empty Constructor
    }

    @Override
    public STypeDataNascimento getType() {
        return (STypeDataNascimento) super.getType();
    }

    public Date getDate() {
        return this.getDescendant(this.getType().date).getValue();
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public int getIdade() {
        Date date = getDate();
        if (date != null) {
            LocalDate data = toLocalDate(date);
            if (data != null) return Period.between(data, LocalDate.now()).getYears();
        }
        return 0;
    }
}
