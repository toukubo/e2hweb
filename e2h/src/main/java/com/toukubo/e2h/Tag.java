package com.toukubo.e2h;

import java.util.ArrayList;
import java.util.List;

public class Tag {
	public static String pomodoroTagGuid = "963af6ee-e953-4aee-af14-ac1a012206b2";
	String guid = "";
	String name = "";
	EvernoteConfig config = EvernoteConfig.createConfig();

	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Tag(String guid,String name){
		this.guid = guid;
		this.name = name;
	}
	public Tag() {
		// TODO Auto-generated constructor stub
	}
	public static String findTagFromGuid(String guid){
		net.enclosing.list.List listUtil = new net.enclosing.list.List();
		listUtil.setDrivecsvPath("./list/");
		List<Tag> storedTags =listUtil.list(Tag.class);
		for (Tag tag : storedTags) {
			if(tag.getGuid().equals(guid)){
				return tag.getName();
			}
		}
		return null;
		
	}
	public static com.evernote.edam.type.Tag findTag(String name){
		
		try {
			EvernoteConfig config = EvernoteConfig.createConfig();

	        List<com.evernote.edam.type.Tag> tags = (List<com.evernote.edam.type.Tag>)config.getFactory().createNoteStoreClient().listTags();  
	        for (com.evernote.edam.type.Tag tag : tags) {  
	        	if(tag.getName().equals(name)){
	        		return tag;
	        	}
	        }
		} catch (Exception e) {
			// TODO: handle exception
		}
        return null;
	}
	public static void main(String[] args) {
		Tag tag = new Tag()		;
		tag.reloadNotebooksToCache();
		
		
//		com.evernote.edam.type.Tag tag2 = tag.findTag("@pomodoro");
//		System.err.println(tag2.getGuid());
	}
	public  void reloadNotebooksToCache(){
		List<com.evernote.edam.type.Tag> tags;
		try {
			tags = config.getNoteStore().listTags();

			List<Tag> storedTags = new ArrayList<Tag>();
			for (com.evernote.edam.type.Tag tag : tags) {
				Tag tag2 = new Tag(tag.getGuid(), tag.getName());
				storedTags.add(tag2);
			}
			

			net.enclosing.list.List listUtil = new net.enclosing.list.List();
			listUtil.setDrivecsvPath("./list/");
			listUtil.writeList(storedTags, Tag.class);
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
