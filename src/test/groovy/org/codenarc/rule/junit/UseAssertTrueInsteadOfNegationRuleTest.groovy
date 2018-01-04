/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.rule.junit

import org.codenarc.rule.GenericAbstractRuleTestCase
import org.junit.Test

/**
 * Tests for UseAssertTrueInsteadOfNegationRule
 *
 * @author 'Hamlet D'Arcy'
  */
class UseAssertTrueInsteadOfNegationRuleTest extends GenericAbstractRuleTestCase<UseAssertTrueInsteadOfNegationRule> {

    @Test
    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == 'UseAssertTrueInsteadOfNegation'
    }

    @Test
    void testSuccessScenario() {
        final SOURCE = '''
            class MyTest extends GroovyTestCase {
                public void testMethod() {
                    assertFalse(condition)
                    assertTrue(condition)
                    assertTrue(!condition, condition)
                    assertFalse(!condition, condition)
                }
            }
        '''
        assertNoViolations(SOURCE)
    }

    @Test
    void testUsingThisReference() {
        final SOURCE = '''
            class MyTest extends GroovyTestCase {
                public void testMethod() {
                    assertFalse(!condition)
                }
            }
        '''
        assertSingleViolation(SOURCE, 4, 'assertFalse(!condition)', 'assertFalse(!condition) can be simplified to assertTrue(condition)')
    }

    @Test
    void testUsingStaticReference() {
        final SOURCE = '''
            class MyTest extends GroovyTestCase {
                public void testMethod() {
                    Assert.assertFalse(!condition)
                }
            }
        '''
        assertSingleViolation(SOURCE, 4, 'Assert.assertFalse(!condition)', 'Assert.assertFalse(!condition) can be simplified to Assert.assertTrue(condition)')
    }

    @Override
    protected UseAssertTrueInsteadOfNegationRule createRule() {
        new UseAssertTrueInsteadOfNegationRule()
    }
}
