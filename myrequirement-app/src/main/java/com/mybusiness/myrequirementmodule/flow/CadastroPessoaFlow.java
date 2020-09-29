package com.mybusiness.myrequirementmodule.flow;

import com.mybusiness.myrequirementmodule.MyRequirementModule;
import org.opensingular.flow.core.DefinitionInfo;
import org.opensingular.flow.core.FlowInstance;
import org.opensingular.flow.core.ITaskDefinition;
import org.opensingular.flow.core.defaults.PermissiveTaskAccessStrategy;
import org.opensingular.requirement.commons.flow.builder.RequirementFlowBuilder;
import org.opensingular.requirement.commons.flow.builder.RequirementFlowDefinition;
import org.opensingular.requirement.commons.wicket.view.form.FormPage;

import javax.annotation.Nonnull;

import static com.mybusiness.myrequirementmodule.flow.CadastroPessoaFlow.CadastroPessoaTasks.*;

@DefinitionInfo("cadastroPessoa")
public class CadastroPessoaFlow extends RequirementFlowDefinition<FlowInstance> {


    public CadastroPessoaFlow() {
        super(FlowInstance.class);
        this.setName(MyRequirementModule.MYREQUIREMENT_CONST, "CadastroPessoa");

    }

    @Override
    protected void buildFlow(@Nonnull RequirementFlowBuilder requirementFlowBuilder) {
        requirementFlowBuilder.addHumanTask(ANALISAR)
                .uiAccess(new PermissiveTaskAccessStrategy())
                .withExecutionPage(FormPage.class);


        requirementFlowBuilder.addHumanTask(SOLICITACAO_ADMIN)
                .uiAccess(new PermissiveTaskAccessStrategy())
                .withExecutionPage(FormPage.class);

        requirementFlowBuilder.addEndTask(REPROVADO);
        requirementFlowBuilder.addEndTask(APROVADO);

        requirementFlowBuilder.setStartTask(ANALISAR);

        requirementFlowBuilder.from(ANALISAR).go("Aprovar", APROVADO);
        requirementFlowBuilder.from(ANALISAR).go("Reprovar", REPROVADO);
        requirementFlowBuilder.from(ANALISAR).go("Passar para admin", SOLICITACAO_ADMIN);

        requirementFlowBuilder.from(SOLICITACAO_ADMIN).go("Concluir Admin", ANALISAR);
    }


    public enum CadastroPessoaTasks implements ITaskDefinition {

        ANALISAR("Analisar"),
        APROVADO("Aprovado"),
        REPROVADO("Reprovado"),
        SOLICITACAO_ADMIN("Solicitação com Administrador");

        private final String taskName;

        CadastroPessoaTasks(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public String getName() {
            return taskName;
        }
    }

}
