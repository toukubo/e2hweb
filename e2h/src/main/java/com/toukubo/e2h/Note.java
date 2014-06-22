package com.toukubo.e2h;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.notestore.NoteMetadata;
import com.evernote.thrift.TException;

public class Note {
	private Notebook notebook = null;
	
	public Notebook getNotebook() {
		return notebook;
	}
	public void setNotebook(Notebook notebook) {
		this.notebook = notebook;
	}
	public Note(){
		
	}
	public void update(){
		try {
			config.getFactory().createNoteStoreClient().updateNote(this.note);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	com.evernote.edam.type.Note note = null;
	NoteMetadata metaNote = null;
	EvernoteConfig config = EvernoteConfig.createConfig();
	private String content;
	private String title;
	private String guid;
	

	public com.evernote.edam.type.Note getNote(){
		if(this.note==null){
			try {
				this.note= config.getNoteStore().getNote(this.guid, true, true, false, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return this.note;

		}
		return null;
	}
	public String getGuid() {
		return guid;
	}
	public Note(NoteMetadata metaNote){
		this.setGuid(metaNote.getGuid());
		this.setTitle(metaNote.getTitle());
		this.metaNote = metaNote;
		this.notebook = Notebook.findNotebook(metaNote.getNotebookGuid());
	}
	public Note(String guid){
		try {

//			this.setGuid(guid);
//			System.err.println(noteStore.getNoteTagNames(note.getGuid()));
//			System.err.println("----------was note ----------------");
//			System.err.println(fullNote.getContent());;
//			System.err.println(this.getNote().getTitle());;
//			this.setTitle(this.getNote().getTitle());
			
//			this.note = config.getNoteStore().getNote(guid, true,true, false, true);
//			this.notebook = new Notebook(this.note.getNotebookGuid());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void refreshTags(){
		net.enclosing.list.List list  = new net.enclosing.list.List();
		list.setDrivecsvPath("./list/");
		List<Tag> bufferedTags = list.list(Tag.class);

	}
	
	public Collection<Tag> getTags(){


		Collection<Tag> tags = new ArrayList<Tag>();
				
		List<String> tags2;
		try {
			Tag tag = new Tag();
			
			tags2 = this.metaNote.getTagGuids(); 
//			tags2 = config.getNoteStore().getNoteTagNames(note.getGuid());
			for (String guid : tags2) {
				tags.add(new Tag(guid,Tag.findTagFromGuid(guid)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tags;
	}
	private String parseDom(String content){
		try {
			int index = content.indexOf("<en-note");
			content = content.substring(index);
			InputStream in = new ByteArrayInputStream(content.getBytes());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(in);
			
			String returned = doc.getDocumentElement().getTextContent();
			return returned;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	public String getContent() {
		if(this.note==null)
			return "";
					
		this.setContent(parseDom(this.getNote().getContent()));

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
		
			List<String> tags = this.note.getTagGuids();
			tags.remove(Tag.pomodoroTagGuid);
			this.note.setTagGuids(tags);

		this.update();
	}
	
	public static void main(String[] args) {
		Notes notes = new Notes("tag:@micro");
		Note note = new Note(notes.getNotes().iterator().next().getGuid());
		System.err.print(note.note.getGuid());
		note.setReminderDone();
		
		
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
}
