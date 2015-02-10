/*
 * Copyright (c) 2005-2012 Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.complexible.common.io.block;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import com.complexible.common.io.block.BlockSpec;
import com.complexible.common.io.block.BlockWriter;
import com.complexible.common.io.block.Bracket;
import com.complexible.common.io.block.TextBlockWriter;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Evren Sirin
 */
public class BlockWriterTests {
	private String SPARQL = 
		"SELECT ?x ?y ?z {\n" + 
		"   ?s ?p ?o ;\n" + 
		"      ?q ?v .\n" + 
		"   OPTIONAL {\n" + 
		"      ?p a ?x .\n" + 
		"      FILTER (isURI(?x))\n" + 
		"   }\n" + 
		"   ?o a ?t\n" + 
		"}\n";
	
	StringWriter sw;
	BlockWriter out;
	
	@Before
	public void beforeTest() {
		sw = new StringWriter();
		out = new TextBlockWriter(sw);
	}
	
	@Test
	public void textSPARQL() {		
		out.append("SELECT").append(" ?x ?y ?z ").println("{");
		out.printTab().beginBlock();
		out.print("?s ");
		out.beginBlock();
		out.println("?p ?o ;");
		out.println("?q ?v .");
		out.endBlock();
		out.println("OPTIONAL {");
		out.printTab().beginBlock();
		out.println("?p a ?x .");
		out.println("FILTER (isURI(?x))");
		out.endBlock().println("}");
		out.println("?o a ?t");
		out.endBlock().println("}");
		
		assertEquals(SPARQL, sw.toString());
	}
	
	@Test
	public void textSPARQLBlockSpec() {
		BlockSpec sparqlBlock = new BlockSpec().marker(Bracket.CURLY).newLineAfterBegin(true).newLineAfterEnd(true);
		BlockSpec optionalBlock = sparqlBlock.copy().title("OPTIONAL");
		BlockSpec filterBlock = sparqlBlock.copy().title("FILTER").marker(Bracket.PARENTHESIS).indentSize(0).newLineAfterBegin(false).newLineBeforeEnd(false);
		
		out.append("SELECT").append(" ?x ?y ?z ");
		out.beginBlock(sparqlBlock);
		out.print("?s ");
		out.beginBlock();
		out.println("?p ?o ;");
		out.println("?q ?v .");
		out.endBlock();
		out.beginBlock(optionalBlock);
		out.println("?p a ?x .");
		out.beginBlock(filterBlock);
		out.print("isURI(?x)");
		out.endBlock();
		out.endBlock();
		out.println("?o a ?t");
		out.endBlock();
		
		assertEquals(SPARQL, sw.toString());
	}
	
	@Test
	public void textIndent() {
		indent(3);
		String expected =
			"Level3\n" +
			"   Level2\n" +
			"      Level1\n";
		assertEquals(expected, sw.toString());
	}
	
	private void indent(int level) {
		out.println("Level" + level);
		if (level > 1) {
			out.beginBlock(BlockSpec.INDENTED);
			indent(level - 1);
			out.endBlock();
		}
	}
}
