package com.mybusiness.myrequirementmodule.model.stypes;

import com.mybusiness.myrequirementmodule.form.MyRequirementPackage;
import com.mybusiness.myrequirementmodule.model.instances.SIDataNascimento;
import com.mybusiness.myrequirementmodule.model.instances.SIPessoa;
import org.apache.commons.lang3.StringUtils;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.SIInteger;
import org.opensingular.form.type.core.SIString;
import org.opensingular.form.type.core.STypeInteger;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.form.type.country.brazil.STypeCPF;
import org.opensingular.form.type.country.brazil.STypeTelefoneNacional;
import org.opensingular.form.validation.InstanceValidatable;
import org.opensingular.lib.commons.util.Loggable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SInfoType(
        name = "DadosPessoa",
        spackage = MyRequirementPackage.class
)
public class STypePessoa extends STypeComposite<SIPessoa> implements Loggable {

    public STypeString nome;
    public STypeString sobrenome;
    public STypeTelefoneNacional telefone;
    public STypeCPF cpf;
    public STypeInteger idade;
    public STypeDataNascimento dataNascimento;

    public STypePessoa() {
        super(SIPessoa.class);
    }

    @Override
    protected void onLoadType(TypeBuilder tb) {
        this.nome = this.addFieldString("nome");
        this.sobrenome = this.addFieldString("sobrenome");
        this.telefone = this.addField("telefone", STypeTelefoneNacional.class);
        this.cpf = this.addField("cpf", STypeCPF.class);
        this.dataNascimento = this.addField("dataNascimento", STypeDataNascimento.class);
        this.idade = this.addFieldInteger("idade");

        this.nome.asAtr()
                .label("Nome:")
                .required()
                .dependsOn(sobrenome)
                .asAtrBootstrap()
                .colPreference(4);
        this.nome.addInstanceValidator(this::verificaNomeESobrenomeMesmoCampo);

        this.sobrenome
                .asAtr()
                .label("Sobrenome:")
                .required()
                .dependsOn(nome)
                .asAtrBootstrap()
                .colPreference(8);
        this.sobrenome.addInstanceValidator(this::verificaNomeESobrenomeMesmoCampo);

        this.telefone.asAtr()
                .label("Telefone:")
                .required()
                .asAtrBootstrap()
                .colPreference(4);

        this.cpf.asAtr()
                .label("CPF")
                .required()
                .asAtrBootstrap()
                .colPreference(4);

        this.dataNascimento.asAtr()
                .required()
                .asAtrBootstrap()
                .colPreference(2);

        this.idade.withUpdateListener(this::preencherIdade)
                .asAtr()
                .dependsOn(dataNascimento.date)
                .label("Idade")
                .asAtrBootstrap()
                .colPreference(2);

    }

    private void preencherIdade(SIInteger instance) {
        SIDataNascimento siDateNascimento = instance.findNearestOrException(dataNascimento);
        if (siDateNascimento.isNotEmptyOfData()) {
            instance.setValue(siDateNascimento.getIdade());
        }
    }

    private void verificaNomeESobrenomeMesmoCampo(InstanceValidatable<SIString> validator) {
        SIString siNome = validator.getInstance().findNearestOrException(nome);
        SIString siSobrenome = siNome.findNearestOrException(sobrenome);

        if (siSobrenome.isNotEmptyOfData() && validarIgualdadeSobrenomeNome(siNome.getValue(), siSobrenome.getValue())) {
            validator.error("Favor preencher nome e sobrenome separadamente nos respectivos campos.");
        }

    }

    private boolean validarIgualdadeSobrenomeNome(String siNome, String siSobrenome) {

        List<String> textsFirstName = removeBlankTextsFromArray(splitTextOnBlankValue(siNome));

        List<String> textsLastName = removeBlankTextsFromArray(splitTextOnBlankValue(siSobrenome));


        return textsFirstName.stream().anyMatch(textsLastName::contains);
    }

    private List<String> removeBlankTextsFromArray(List<String> text) {
        return text.stream()
                .filter(t -> !StringUtils.isBlank(t))
                .collect(Collectors.toList());
    }

    private List<String> splitTextOnBlankValue(String text) {
        return Arrays.asList(text.split(" "));
    }

}
