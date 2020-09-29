package com.mybusiness.myrequirementmodule.form.myrequirement;

import com.mybusiness.myrequirementmodule.form.MyRequirementPackage;
import org.opensingular.form.SInfoType;
import org.opensingular.form.SInstance;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.STypeBoolean;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.form.view.SViewByBlock;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.function.Predicate;

import static org.opensingular.form.util.SingularPredicates.typeValueIsTrueAndNotNull;

@SInfoType(spackage = MyRequirementPackage.class, name = "MyRequirement")
public class MyRequirementForm extends STypeComposite<MyRequirementInstance> {

    public STypeBoolean radioBox;
    public STypeString descriptionTextArea;
    public STypeString typeSelection;
    public STypeString dropDown;
    public STypeString radioButton;
    public STypeBoolean showRadioButton;
    public STypeString autocompleteField;

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
        typeSelection = addFieldString("typeSelection");
        descriptionTextArea = addFieldString("descriptionTextArea");
        dropDown = addFieldString("dropDown");
        showRadioButton = addFieldBoolean("showRadioButton");
        radioButton = addFieldString("radioButton");
        autocompleteField = addFieldString("autocompleteField");


        radioBox.withRadioView("Sim", "Não")
                .asAtr()
                .required()
                .label("Novo Usuário?")
                .asAtrAnnotation()
                .setAnnotated()
                .asAtrBootstrap()
                .colPreference(2);

        typeSelection.selectionOf(Profissoes.valuesOfDescricao())
                .asAtr().label("Profissão")
                .required()
                .dependsOn(radioBox)
                .exists(isNewUser())
                .asAtrAnnotation()
                .setAnnotated()
                .asAtrBootstrap()
                .colPreference(3);


        descriptionTextArea
                .withTextAreaView(sViewTextArea -> sViewTextArea.setLines(4))
                .asAtr()
                .maxLength(4000)
                .required()
                .label("Descrição")
                .asAtrBootstrap()
                .colPreference(12)
                .asAtrAnnotation().setAnnotated();

        dropDown.selectionOf(createOptions(5))
                .asAtr()
                .label("Grau Estudantil");

        showRadioButton.withRadioView("Sim", "Não")
                .asAtr()
                .required()
                .label("Tem experiência?");

        radioButton.withRadioView()
                .selectionOf(createOptions(5))
                .asAtr()
                .label("Anos de experiência")
                .dependsOn(showRadioButton)
                .visible(typeValueIsTrueAndNotNull(showRadioButton));

        autocompleteField.autocompleteOf(createOptions(15))
                .asAtr()
                .label("Tecnologias conhecidas: ");

        this.withView(new SViewByBlock(), block ->
                block.newBlock("Requerimento")
                        .add(radioBox)
                        .add(typeSelection)
                        .add(descriptionTextArea)
                        .newBlock("Infos Adicionais")
                        .add(dropDown)
                        .add(showRadioButton)
                        .add(radioButton)
                        .add(autocompleteField)
        );
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
                .append(dropDown, that.dropDown)
                .append(radioButton, that.radioButton)
                .append(showRadioButton, that.showRadioButton)
                .append(autocompleteField, that.autocompleteField)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(radioBox)
                .append(descriptionTextArea)
                .append(typeSelection)
                .append(dropDown)
                .append(radioButton)
                .append(showRadioButton)
                .append(autocompleteField)
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

