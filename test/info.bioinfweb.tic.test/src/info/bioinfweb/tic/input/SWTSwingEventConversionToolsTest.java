/*
 * Toolkit independent components (TIC) - A Java library for creating GUI components for Swing and SWT
 * Copyright (C) 2014-2017  Ben Stöver, Sarah Wiechers
 * <http://bioinfweb.info/TIC>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.tic.input;


import org.junit.* ;


import static org.junit.Assert.* ;



public class SWTSwingEventConversionToolsTest {
	@Test
  public void test_convertEventTime() {
  	long swingTime = (long)Integer.MAX_VALUE + 5l;
  	assertEquals(swingTime, SWTSwingEventConversionTools.convertSWTEventTime((int)swingTime));

  	int swtTime = Integer.MIN_VALUE;  // SWT used unsigned ints
  	assertEquals(swtTime, (int)SWTSwingEventConversionTools.convertSWTEventTime(swtTime));
  }
}
