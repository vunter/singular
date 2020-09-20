package com.mybusiness.myrequirementmodule.form;

import com.mybusiness.myrequirementmodule.model.dto.CEPDto;
import com.mybusiness.myrequirementmodule.model.stypes.STypePessoa;
import com.mybusiness.myrequirementmodule.spring.service.EnderecoCustomService;
import org.opensingular.form.*;
import org.opensingular.form.type.core.SIString;
import org.opensingular.form.type.core.STypeBoolean;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.form.type.country.brazil.STypeAddress;
import org.opensingular.form.view.SViewByBlock;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import static org.opensingular.form.util.SingularPredicates.typeValueIsTrueAndNotNull;

@SInfoType(spackage = MyRequirementPackage.class, name = "MyRequirement")
public class MyRequirementForm extends STypeComposite<MyRequirementInstance> {

    public STypeBoolean radioBox;
    public STypeString descriptionTextArea;
    public STypeString typeSelection;
    public STypeAddress endereco;
    public STypeString dropDown;
    public STypeString radioButton;
    public STypeBoolean showRadioButton;
    public STypeString autocompleteField;
    public STypePessoa dadosPessoa;


    @Inject
    private EnderecoCustomService enderecoCustomService;

    public MyRequirementForm() {
        super(MyRequirementInstance.class);
    }

    private static String[] createOptions(int sizeOptions) {
        String[] options = new String[sizeOptions];
        for (int i = 1; i <= sizeOptions; i++) {
            options[i - 1] = "Opção " + i;
        }
        return options;
    }

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        asAtr().displayString("Nova Solicitação");

        radioBox = addFieldBoolean("radioBox");
        radioBox.withRadioView("Sim", "Não")
                .asAtr().required().label("Novo Usuário?").asAtrAnnotation().setAnnotated()
                .asAtrBootstrap().colPreference(2);

        typeSelection = addFieldString("typeSelection");
        typeSelection.selectionOf(Profissoes.valuesOfDescricao()).asAtr().label("Profissão").required(true);
        typeSelection.asAtr().dependsOn(radioBox).exists(isNewUser()).asAtrAnnotation().setAnnotated()
                .asAtrBootstrap().colPreference(3);

        descriptionTextArea = addFieldString("descriptionTextArea");
        descriptionTextArea
                .asAtr().required().label("Descrição")
                .asAtrBootstrap().colPreference(12);
        descriptionTextArea
                .withTextAreaView(sViewTextArea -> sViewTextArea.setLines(4))
                .asAtr().maxLength(4000).asAtrAnnotation().setAnnotated();

        dropDown = addFieldString("dropDown");
        dropDown.selectionOf(createOptions(5));
        dropDown.asAtr().label("Drop Down exercicio 1");

        showRadioButton = addFieldBoolean("showRadioButton");
        showRadioButton.withRadioView("Sim", "Não").asAtr().required().label("Mostrar Opções Radio Button");

        radioButton = addFieldString("radioButton");
        radioButton.withRadioView().selectionOf(createOptions(5)).asAtr().label("Radio Button exercicio 2");
        radioButton.asAtr().dependsOn(showRadioButton).visible(typeValueIsTrueAndNotNull(showRadioButton));

        autocompleteField = addFieldString("autocompleteField");
        autocompleteField.autocompleteOf(createOptions(5)).asAtr().label("Autocomplete: ");

        endereco = addField("endereco", STypeAddress.class);
        endereco.withUpdateListener(this::pesquisarCEP).asAtr().dependsOn(endereco.cep);
        dadosPessoa = addField("dadosPessoa", STypePessoa.class);
        this.withView(new SViewByBlock(), block ->
                block.newBlock("Requerimento")
                        .add(radioBox)
                        .add(typeSelection)
                        .add(descriptionTextArea)
                        .newBlock("Exercicios")
                        .add(dropDown)
                        .add(showRadioButton)
                        .add(radioButton)
                        .add(autocompleteField)
                        .newBlock("Endereço")
                        .add(endereco)
                        .newBlock("Dados Pessoais")
                        .add(dadosPessoa)
        );
    }

    private void pesquisarCEP(SIComposite instance) {
        final Optional<SIString> cepField = instance.findNearest(endereco.cep);
        cepField.ifPresent(c -> {

            CEPDto ender = enderecoCustomService.getByCep(c.getValue());
            instance.findNearest(MyRequirementForm.class).ifPresent(s -> s.fillInstance(ender));

        });
    }

    private Predicate<SInstance> isNewUser() {
        return typeValueIsTrueAndNotNull(radioBox);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MyRequirementForm that = (MyRequirementForm) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(radioBox, that.radioBox)
                .append(descriptionTextArea, that.descriptionTextArea)
                .append(typeSelection, that.typeSelection)
                .append(endereco, that.endereco)
                .append(dropDown, that.dropDown)
                .append(radioButton, that.radioButton)
                .append(showRadioButton, that.showRadioButton)
                .append(autocompleteField, that.autocompleteField)
                .append(enderecoCustomService, that.enderecoCustomService)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(radioBox)
                .append(descriptionTextArea)
                .append(typeSelection)
                .append(endereco)
                .append(dropDown)
                .append(radioButton)
                .append(showRadioButton)
                .append(autocompleteField)
                .append(enderecoCustomService)
                .toHashCode();
    }

    public enum Profissoes {

        DESENVOLVEDOR("Desenvolvedor"),
        ANALISTA("Analista"),
        GERENTE("Gerente"),
        CEO("Diretor executivo");

        private final String descricao;

        Profissoes(String descricao) {
            this.descricao = descricao;
        }

        public static String[] valuesOfDescricao() {
            return Arrays.stream(values()).map(v -> v.descricao).toArray(String[]::new);
        }

        @Nonnull
        public String getDescricao() {
            return descricao;
        }
    }

}

