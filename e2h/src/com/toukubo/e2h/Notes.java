package com.toukubo.e2h;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import com.evernote.edam.error.EDAMErrorCode;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.thrift.TException;
import com.evernote.thrift.transport.TTransportException;

public class Notes {
	//
//	  private static final String AUTH_TOKEN = "S=s1:U=5e207:E=14dc76cabc7:C=1466fbb7fca:P=1cd:A=en-devtoken:V=2:H=df4abf0a86fdeadfd0c1b5f675491fab"; // sandbox
		Collection<Note> notes = null;
		  private UserStoreClient userStore;
		  private NoteStoreClient noteStore;
		  private String newNoteGuid;
		  EvernoteConfig config = new EvernoteConfig();
	        ArrayList<com.toukubo.e2h.Note> ourNotes = new ArrayList<com.toukubo.e2h.Note>();

		  
		public ArrayList<com.toukubo.e2h.Note> getOurNotes() {
				return ourNotes;
			}

			public void setOurNotes(ArrayList<com.toukubo.e2h.Note> ourNotes) {
				this.ourNotes = ourNotes;
			}

		public Notes(String query){
		    try {
				noteStore = config.getFactory().createNoteStoreClient();
		
		        this.notes= searchNotes(query).getNotes();
				for (Note note : notes) {
					Note fullNote = noteStore.getNote(note.getGuid(), true, true, false, false);

//					System.err.println(noteStore.getNoteTagNames(note.getGuid()));
					System.err.println("----------was note ----------------");
//					System.err.println(fullNote.getContent());;
					System.err.println(fullNote.getTitle());;
					com.toukubo.e2h.Note ournote = new com.toukubo.e2h.Note();
					ournote.setContent(parseDom(fullNote.getContent()));
					ournote.setTitle(fullNote.getTitle());
					ournote.setGuid(note.getGuid());
					ournote.note = fullNote;
					ourNotes.add(ournote);

				}
		        

		      } catch (EDAMUserException e){ 
		      
		        // These are the most common error types that you'll need to
		        // handle
		        // EDAMUserException is thrown when an API call fails because a
		        // paramter was invalid.
		        if (e.getErrorCode() == EDAMErrorCode.AUTH_EXPIRED) {
		          System.err.println("Your authentication token is expired!");
		        } else if (e.getErrorCode() == EDAMErrorCode.INVALID_AUTH) {
		          System.err.println("Your authentication token is invalid!");
		        } else if (e.getErrorCode() == EDAMErrorCode.QUOTA_REACHED) {
		          System.err.println("Your authentication token is invalid!");
		        } else {
		          System.err.println("Error: " + e.getErrorCode().toString()
		              + " parameter: " + e.getParameter());
		        }
		      } catch (EDAMSystemException e) {
		        System.err.println("System error: " + e.getErrorCode().toString());
		      } catch (TTransportException t) {
		        System.err.println("Networking error: " + t.getMessage());
		      }	catch (TException e){
					e.printStackTrace();
		      	}catch (Exception e) {
					e.printStackTrace();
				}
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
				// TODO: handle exception
			}
			return null;

			
		}
		

		private NoteList searchNotes(String query) throws Exception {
			    // http://dev.evernote.com/documentation/cloud/chapters/Searching_notes.php
//			    query = "intitle:EDAMDemo";

			    // To search for notes with a specific tag, we could do something like
			    // this:
			    // String query = "tag:tagname";


			    NoteFilter filter = new NoteFilter();
			    
			    filter.setWords(query);
			    filter.setOrder(NoteSortOrder.UPDATED.getValue());
			    filter.setAscending(false);

			    // Find the first 50 notes matching the search
			    System.out.println("Searching for notes matching query: " + query);
			    NoteList notes = noteStore.findNotes(filter, 0, 50);
			    
			    return notes;
		  }

		public Collection<Note> getNotes() {
			return this.notes;
		}

		public static void main(String[] args) {
    		Collection<com.toukubo.e2h.Note> notes = new Notes("tag:@pomodoro").getOurNotes();
    		for (com.toukubo.e2h.Note note : notes) {
				note.note.getTagNames();
			}
		}

}
