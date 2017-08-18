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
package info.bioinfweb.tic;


import info.bioinfweb.tic.input.TICKeyAdapter;
import info.bioinfweb.tic.input.TICKeyEvent;
import info.bioinfweb.tic.input.TICMouseAdapter;
import info.bioinfweb.tic.input.TICMouseEvent;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.* ;

import static org.junit.Assert.* ;



public class TICComponentTest {
	private static TICComponent createComponent() {
		TICComponent result = new TICComponent() {
			@Override
			public void paint(TICPaintEvent e) {}
			
			@Override
			public Dimension getSize() {
				return new Dimension(10, 10);
			}
		};
		
		result.addKeyListener(new TICKeyAdapter() {
			@Override
			public boolean keyPressed(TICKeyEvent event) {
				System.out.println("key pressed " + event);
				return true;
			}
		});
		
		result.addMouseListener(new TICMouseAdapter() {
			@Override
			public boolean mousePressed(TICMouseEvent event) {
				System.out.println("mousePressed " + event);
				return true;
			}
		});
		
		return result;
	}
	
	
	public static void main(String[] args) {
		TICComponent component = createComponent(); 		
		component.dispatchEvent(new TICKeyEvent(component, KeyEvent.KEY_PRESSED, 0, 0, 0, 0, 'A'));
		component.dispatchEvent(new TICKeyEvent(component, KeyEvent.KEY_RELEASED, 0, 0, 0, 0, 'A'));
		component.dispatchEvent(new TICMouseEvent(component, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 1, false, 5, 5));
		component.dispatchEvent(new TICMouseEvent(component, MouseEvent.MOUSE_MOVED, 0, 0, 0, 1, false, 5, 5));
	}
}
