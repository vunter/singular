package com.mybusiness.myrequirementmodule.flow;

import com.mybusiness.myrequirementmodule.MyRequirementModule;
import org.opensingular.flow.core.*;
import org.opensingular.flow.core.defaults.PermissiveTaskAccessStrategy;
import org.opensingular.requirement.commons.flow.builder.RequirementFlowBuilder;
import org.opensingular.requirement.commons.flow.builder.RequirementFlowDefinition;
import org.opensingular.requirement.commons.wicket.view.form.FormPage;

import javax.annotation.Nonnull;

import static com.mybusiness.myrequirementmodule.flow.MyRequirementFlow.MyRequirementTasks.*;

@DefinitionInfo("myrequirement")
public class MyRequirementFlow extends RequirementFlowDefinition<FlowInstance> {

    public MyRequirementFlow() {
        super(FlowInstance.class);
        this.setName(MyRequirementModule.MYREQUIREMENT_CONST, "MyRequirement");

    }

    @Override
    protected void buildFlow(@Nonnull RequirementFlowBuilder flow) {

        flow.addHumanTask(ANALISAR)
                .uiAccess(new PermissiveTaskAccessStrategy()).withExecutionPage(FormPage.class);

        flow.addHumanTask(SOLICITACAO_COM_PENDENCIAS).uiAccess(new PermissiveTaskAccessStrategy())
                .withExecutionPage(FormPage.class);

        flow.addHumanTask(SOLICITACAO_ADMIN).uiAccess(new PermissiveTaskAccessStrategy()).withExecutionPage(FormPage.class);
        flow.addEndTask(REPROVADO);
        flow.addEndTask(APROVADO);

        flow.setStartTask(ANALISAR);

        flow.from(ANALISAR).go("Solicitar ajustes", SOLICITACAO_COM_PENDENCIAS);
        flow.from(ANALISAR).go("Aprovar", APROVADO);
        flow.from(ANALISAR).go("Reprovar", REPROVADO);

        flow.from(SOLICITACAO_COM_PENDENCIAS).go("Concluir Pendência", ANALISAR);
        flow.from(SOLICITACAO_ADMIN).go("Concluir Admin", ANALISAR);

    }

    public enum MyRequirementTasks implements ITaskDefinition {

        ANALISAR("Analisar"),
        APROVADO("Aprovado"),
        REPROVADO("Reprovado"),
        SOLICITACAO_COM_PENDENCIAS("Solicitação com pendências"),
        SOLICITACAO_ADMIN("Solicitação com Administrador");

        private final String taskName;

        MyRequirementTasks(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public String getName() {
            return taskName;
        }
    }


}