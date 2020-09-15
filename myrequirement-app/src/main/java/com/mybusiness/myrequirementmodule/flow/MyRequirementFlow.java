package com.mybusiness.myrequirementmodule.flow;

import com.mybusiness.myrequirementmodule.MyRequirementModule;
import org.opensingular.flow.core.DefinitionInfo;
import org.opensingular.flow.core.ITaskDefinition;
import org.opensingular.flow.core.FlowInstance;
import org.opensingular.flow.core.defaults.PermissiveTaskAccessStrategy;

import org.opensingular.requirement.commons.flow.builder.RequirementFlowBuilder;
import org.opensingular.requirement.commons.flow.builder.RequirementFlowDefinition;
import org.opensingular.requirement.commons.wicket.view.form.FormPage;


import javax.annotation.Nonnull;

import static com.mybusiness.myrequirementmodule.flow.MyRequirementFlow.MyRequirementTasks.ANALISAR;
import static com.mybusiness.myrequirementmodule.flow.MyRequirementFlow.MyRequirementTasks.APROVADO;
import static com.mybusiness.myrequirementmodule.flow.MyRequirementFlow.MyRequirementTasks.REPROVADO;
import static com.mybusiness.myrequirementmodule.flow.MyRequirementFlow.MyRequirementTasks.SOLICITACAO_COM_PENDENCIAS;

@DefinitionInfo("myrequirement")
public class MyRequirementFlow extends RequirementFlowDefinition<FlowInstance> {

    public enum MyRequirementTasks implements ITaskDefinition {

        ANALISAR("Analisar"),
        APROVADO("Aprovado"),
        REPROVADO("Reprovado"),
        SOLICITACAO_COM_PENDENCIAS("Solicitação com pendências");

        private String taskName;

        MyRequirementTasks(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public String getName() {
            return taskName;
        }
    }

    public MyRequirementFlow() {
        super(FlowInstance.class);
        this.setName(MyRequirementModule.MYREQUIREMENT, "MyRequirement");

    }

    @Override
    protected void buildFlow(@Nonnull RequirementFlowBuilder flow) {

        flow.addHumanTask(ANALISAR)
                .uiAccess(new PermissiveTaskAccessStrategy()).withExecutionPage(FormPage.class);

        flow.addHumanTask(SOLICITACAO_COM_PENDENCIAS).uiAccess(new PermissiveTaskAccessStrategy())
                .withExecutionPage(FormPage.class);

        flow.addEndTask(REPROVADO);
        flow.addEndTask(APROVADO);

        flow.setStartTask(ANALISAR);

        flow.from(ANALISAR).go("Solicitar ajustes", SOLICITACAO_COM_PENDENCIAS);
        flow.from(ANALISAR).go("Aprovar", APROVADO);
        flow.from(ANALISAR).go("Reprovar", REPROVADO);

        flow.from(SOLICITACAO_COM_PENDENCIAS).go("Concluir Pendência", ANALISAR);

    }


}