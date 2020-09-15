package com.mybusiness.myrequirementmodule.form;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nonnull;

import com.mybusiness.myrequirementmodule.spring.service.EnderecoCustomService;
import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.SInstance;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.SIString;
import org.opensingular.form.type.core.STypeBoolean;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.form.type.country.brazil.STypeAddress;
import org.opensingular.form.view.SViewByBlock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.opensingular.form.util.SingularPredicates.typeValueIsTrueAndNotNull;

@SInfoType(spackage = MyRequirementPackage.class, name = "MyRequirement")
public class MyRequirementForm extends STypeComposite<SIComposite> {

    private EnderecoCustomService enderecoCustomService = new EnderecoCustomService();

    public STypeBoolean radioBox;
    public STypeString descriptionTextArea;
    public STypeString typeSelection;
    public STypeAddress endereco;
    public STypeString dropDown;
    public STypeString radioButton;
    public STypeBoolean showRadioButton;
    public STypeString autocomplete;

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

        showRadioButton =  addFieldBoolean("showRadioButton");
        showRadioButton.withRadioView("Sim", "Não").asAtr().required().label("Mostrar Opções Radio Button");

        radioButton = addFieldString("radioButton");
        radioButton.withRadioView().selectionOf(createOptions(5)).asAtr().label("Radio Button exercicio 2");
        radioButton.asAtr().dependsOn(showRadioButton).exists(typeValueIsTrueAndNotNull(showRadioButton));

        autocomplete = addFieldString("autocomplete");
        autocomplete.autocompleteOf(createOptions(5)).asAtr().label("Autocomplete: ");

        endereco = addField("endereco", STypeAddress.class);
        endereco.withUpdateListener(this::pesquisarCEP).asAtr().label("endereço").dependsOn(endereco.cep);

        this.withView(new SViewByBlock(), block ->
            block.newBlock("Requerimento")
                .add(radioBox)
                .add(typeSelection)
                .add(descriptionTextArea)
                    .newBlock("Exercicios: ")
                    .add(dropDown)
                    .add(showRadioButton)
                    .add(radioButton)
                    .add(autocomplete)
                        .newBlock("Endereço:")
                        .add(endereco)
            );
    }

    private void pesquisarCEP(SIComposite instance) {
        final Optional<SIString> cepField = instance.findNearest(endereco.cep);
        cepField.ifPresent(c -> {

            endereco = enderecoCustomService.getByCep(c.getValue());;

        });
    }

    private Predicate<SInstance> isNewUser() {
        return typeValueIsTrueAndNotNull(radioBox);
    }

    public enum Profissoes {

        DESENVOLVEDOR("Desenvolvedor"),
        ANALISTA("Analista"),
        GERENTE("Gerente"),
        CEO("Diretor executivo");

        private String descricao;

        Profissoes(String descricao) {
            this.descricao = descricao;
        }

        @Nonnull
        public String getDescricao() {
            return descricao;
        }

        public static String[] valuesOfDescricao(){
            return Arrays.stream(values()).map(v -> v.descricao).toArray(String[]::new);
        }
    }

    private static String[] createOptions(int sizeOptions) {
        String[] options = new String[sizeOptions];
        for (int i = 1; i <= sizeOptions; i++) {
            options[i - 1] = "Opção " + i;
        }
        return options;
    }
}

