/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.utils;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

public class CommandLineTest {

    @Test
    public void testRunLocalCommand() throws CommandLineException {
        String result = CommandLine.run("date");
        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_MONTH);
        assertTrue(result.contains(Integer.toString(today)));
    }

    @Test(expected = CommandLineException.class)
    public void shouldThrowExceptionBecauseExistStatus() throws CommandLineException {
        CommandLine.run("this command does not exit");
    }

    @Test(expected = CommandLineException.class)
    public void shouldThrowExceptionBecauseExistStatus2() throws CommandLineException {
        CommandLine.run("cat arquivo_que_nao_existe");
    }

}
