/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OSCommandTest {

    @Test
    public void testRunLocalCommand() throws CommandLineException {
        OSCommand com = new OSCommand("echo 42");
        String result = com.execute();
        assertEquals("42", result.replace("\n", ""));
    }

    @Test(expected = CommandLineException.class)
    public void shouldThrowExceptionBecauseExistStatus() throws CommandLineException {
        OSCommand com = new OSCommand("this command does not exit");
        com.execute();
    }

    @Test(expected = CommandLineException.class)
    public void shouldThrowExceptionBecauseExistStatus2() throws CommandLineException {
        OSCommand com = new OSCommand("cat arquivo_que_nao_existe");
        com.execute();
    }

}
