package com.toukubo.e2h;

import java.util.ArrayList;
import java.util.List;

import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;

public class Notebook {
	private String name = "";
	private String guid = "";
	EvernoteConfig config = EvernoteConfig.createConfig();;
	com.evernote.edam.type.Notebook notebook = null;
	public Notebook(String notebookGuid,String name) {
		this.name = name;
		this.guid = notebookGuid;
		
	}

	public static Notebook findNotebook(String guid){
		net.enclosing.list.List listUtil = new net.enclosing.list.List();
		listUtil.setDrivecsvPath("./list/");
		List<Notebook> storedNotebooks =listUtil.list(Notebook.class);
		for (Notebook notebook : storedNotebooks) {
			if(notebook.getGuid().equals(guid)){
				return notebook;
			}
		}
		return null;

	}
	public Notebook(String notebookGuid) {
		try {
			this.notebook = config.getNoteStore().getNotebook(notebookGuid);
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
		this.guid = guid;

		this.name = this.findName(guid);
		if(this.name==null){
			this.reloadNotebooksToCache();
			this.name = this.findName(guid);
		}
		System.err.println(this.getName());
		System.err.println(this.getGuid());
	}
	public Notebook() {
		// TODO Auto-generated constructor stub
	}
	public  void reloadNotebooksToCache(){
		List<com.evernote.edam.type.Notebook> notebooks;
		try {
			notebooks = config.getNoteStore().listNotebooks();

			List<Notebook> storedNotebooks = new ArrayList<Notebook>();
			for (com.evernote.edam.type.Notebook notebook : notebooks) {
				Notebook notebook2 = new Notebook(notebook.getGuid(), notebook.getName());
				storedNotebooks.add(notebook2);
			}
			

			net.enclosing.list.List listUtil = new net.enclosing.list.List();
			listUtil.setDrivecsvPath("./list");
			listUtil.writeList(storedNotebooks, Notebook.class);
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private List<Notebook> getNotebooks(){
		net.enclosing.list.List list = new net.enclosing.list.List();
		list.setDrivecsvPath("./list/");

		List<Notebook> cachedNotebooks = list.list(Notebook.class);
		return cachedNotebooks;
	}
	public String findName(String guid){
		List<Notebook> notebooks = getNotebooks();
		for (Notebook notebook : notebooks) {
			if(notebook.getGuid().equals(guid)){
				return notebook.getName();
			}
		}
		this.reloadNotebooksToCache();
		return this.findName(guid);
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}

	public static void main(String[] args) {
		Notebook notebook = new Notebook();
		notebook.reloadNotebooksToCache();
	}
}
