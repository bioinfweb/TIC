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
package info.bioinfweb.tic.test.tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.reflect.ConstructorUtils;



public class OverloadingTest {
	public OverloadingTest(Object p1, Object p2) {
		super();
		System.out.println("Object, Object");
	}
	
	
	public OverloadingTest(List<Object> p1, Set<Object> p2) {
		super();
		System.out.println("List<Object>, Set<Object>");
	}
	
	
	public OverloadingTest(ArrayList<Object> p1, Set<Object> p2) {
		super();
		System.out.println("ArrayList<Object>, Set<Object>");
	}
	
	
	public OverloadingTest(List<Object> p1, HashSet<Object> p2) {
		super();
		System.out.println("List<Object>, HashSet<Object>");
	}
	
	
	public static void main(String[] args) {
		new OverloadingTest(new ArrayList<Object>(), new TreeSet<Object>());
		new OverloadingTest(new LinkedList<Object>(), new TreeSet<Object>());
		new OverloadingTest(new LinkedList<Object>(), new HashSet<Object>());
		// new OverloadingTest(new ArrayList<Object>(), new HashSet<Object>());  // Compile error. "Constructor(Object, Object) is ambiguous."
		System.out.println();
		
		try {
			//System.out.println(OverloadingTest.class.getConstructor(ArrayList.class, TreeSet.class));  // Throws NoSuchMethodException
			//System.out.println(OverloadingTest.class.getConstructor(LinkedList.class, TreeSet.class));  // Throws NoSuchMethodException
			//System.out.println(OverloadingTest.class.getConstructor(LinkedList.class, HashSet.class));  // Throws NoSuchMethodException
			System.out.println(OverloadingTest.class.getConstructor(List.class, HashSet.class));
			// => This method does not work with subclasses.
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
		System.out.println();
		
		System.out.println(ConstructorUtils.getMatchingAccessibleConstructor(OverloadingTest.class, ArrayList.class, TreeSet.class));
		System.out.println(ConstructorUtils.getMatchingAccessibleConstructor(OverloadingTest.class, LinkedList.class, TreeSet.class));
		System.out.println(ConstructorUtils.getMatchingAccessibleConstructor(OverloadingTest.class, LinkedList.class, HashSet.class));
		System.out.println(ConstructorUtils.getMatchingAccessibleConstructor(OverloadingTest.class, ArrayList.class, HashSet.class));
	}
}
