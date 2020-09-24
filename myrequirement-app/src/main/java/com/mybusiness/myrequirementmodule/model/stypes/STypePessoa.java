package com.mybusiness.myrequirementmodule.model.stypes;

import com.mybusiness.myrequirementmodule.model.instances.SIPessoa;
import com.sun.istack.internal.NotNull;
import org.opensingular.form.*;
import org.opensingular.form.type.core.*;
import org.opensingular.form.type.country.brazil.SPackageCountryBrazil;
import org.opensingular.form.type.country.brazil.STypeCPF;
import org.opensingular.form.type.country.brazil.STypeTelefoneNacional;
import org.opensingular.form.validation.InstanceValidatable;
import org.opensingular.lib.commons.util.Loggable;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@SInfoType(
        name = "DadosPessoa",
        spackage = SPackageCountryBrazil.class
)
public class STypePessoa extends STypeComposite<SIPessoa> implements Loggable {

    public STypeString nome;
    public STypeString sobrenome;
    public STypeTelefoneNacional telefone;
    public STypeCPF cpf;
    public STypeInteger idade;
    public STypeDate dataNascimento;


    @Override
    protected void onLoadType(@NotNull TypeBuilder tb) {
        this.nome = this.addFieldString("nome");
        this.nome.asAtrBootstrap().colPreference(4);
        this.nome.asAtr().label("Nome:").required();
        this.sobrenome = this.addFieldString("sobrenome");
        this.sobrenome.asAtr().label("Sobrenome:").required();
        this.sobrenome.asAtrBootstrap().colPreference(8);
        this.telefone = this.addField("telefone", STypeTelefoneNacional.class);
        this.telefone.asAtr().label("Telefone:").required().asAtrBootstrap().colPreference(4);
        this.cpf = this.addField("cpf", STypeCPF.class);
        this.cpf.asAtr().label("CPF").required().asAtrBootstrap().colPreference(4);
        this.dataNascimento = this.addFieldDate("dataNascimento");
        this.dataNascimento.asAtr().label("Data de Nascimento").required().asAtrBootstrap().colPreference(2);
        //this.dataNascimento..maxDate(Date.from(Instant.now()));
        this.idade = this.addFieldInteger("idade");
        this.idade.asAtr().label("Idade").asAtrBootstrap().colPreference(2);
        //this.idade.withUpdateListener(this::handleIdade).asAtr().dependsOn(dataNascimento);

        this.addInstanceValidator(this::verificaNomeESobrenomeMesmoCampo);

    }

    private void handleIdade(SIInteger instance) {
        final Optional<SIDate> dateField = instance.findNearest(dataNascimento);
        dateField.ifPresent(d -> {

            instance.findNearest(STypePessoa.class).ifPresent(s -> s.preencheIdade(dataNascimento.cast()));
        });
    }
    private void verificaNomeESobrenomeMesmoCampo(InstanceValidatable<SIPessoa> validator) {
        SIComposite form = validator.getInstance();

        String nomeString = form.findNearestValueOrException(nome).toString();
        String sobrenomeString = form.findNearestValueOrException(sobrenome).toString();

        if (nomeString.trim().contains(sobrenomeString.trim())) {
            validator.error("Favor preencher nome e sobrenome separadamente nos respectivos campos.");
        }

    }

}
