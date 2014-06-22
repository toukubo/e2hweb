package com.toukubo.e2h;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class Conversion {
	public Conversion(String query){
		Collection<com.toukubo.e2h.Note> notes = new Notes(query).getOurNotes();
		for (com.toukubo.e2h.Note note : notes) {
			System.err.println(note.getContent());;
			System.err.println(note.getTitle());;
		}
	}

	public static void main(String[] args) {
		Conversion conversion = new Conversion("tag:@how");
	}
}
