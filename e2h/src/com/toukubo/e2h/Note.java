package com.toukubo.e2h;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;

public class Note {
	public Note(){
		
	}
	public void update(){
		try {
			config.getFactory().createNoteStoreClient().updateNote(this.note);
		} catch (EDAMUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EDAMSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EDAMNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	com.evernote.edam.type.Note note = null;
	EvernoteConfig config = new EvernoteConfig();
	private String content;
	private String title;
	private String guid;
	
	public String getGuid() {
		return guid;
	}
	public Note(String guid){
		try {
			this.note = config.getFactory().createNoteStoreClient().getNote(guid, true,true, false, true);
		} catch (EDAMUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EDAMSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EDAMNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Collection<Tag> getTags(){
		Collection<Tag> tags = new ArrayList<Tag>();
				
		List<String> tags2;
		try {
			tags2 = config.getFactory().createNoteStoreClient().getNoteTagNames(note.getGuid());
			for (String string : tags2) {
				tags.add(new Tag(string));
			}
		} catch (EDAMUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EDAMSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EDAMNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tags;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setReminderDone(){
		this.note.getAttributes().setReminderDoneTime(System.currentTimeMillis());
		this.note.addToTagNames("@pomodoroed");
		this.update();
	}
	
	public static void main(String[] args) {
		Notes notes = new Notes("test entry");
		Note note = new Note(notes.getNotes().iterator().next().getGuid());
		System.err.print(note.note.getGuid());
		note.setReminderDone();
		
		
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
}
