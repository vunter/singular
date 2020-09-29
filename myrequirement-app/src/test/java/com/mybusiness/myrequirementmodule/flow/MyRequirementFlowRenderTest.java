package com.mybusiness.myrequirementmodule.flow;

import org.junit.Test;
import org.opensingular.requirement.commons.test.flow.AbstractFlowRenderTest;

public class MyRequirementFlowRenderTest extends AbstractFlowRenderTest {

    public MyRequirementFlowRenderTest() {
        setOpenGeneratedFiles(false);
    }

    @Test
    public void render() {
        super.renderImage(new MyRequirementFlow());
    }
}
