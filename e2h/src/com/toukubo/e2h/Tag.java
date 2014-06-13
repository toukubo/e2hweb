package com.toukubo.e2h;

import java.util.List;

public class Tag {
	String name = "";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Tag(String name){
		this.name = name;
	}
	public Tag() {
		// TODO Auto-generated constructor stub
	}
	public static com.evernote.edam.type.Tag findTag(String name){
		
		try {
			EvernoteConfig config = new EvernoteConfig();

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
		Tag tag = new Tag()
		;
		com.evernote.edam.type.Tag tag2 = tag.findTag("@pomodoro");
		System.err.println(tag2.getGuid());
	}
}
