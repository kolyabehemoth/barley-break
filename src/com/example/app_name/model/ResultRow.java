package com.example.app_name.model;

public class ResultRow {
	private String name;
	private int time;
	public ResultRow(){
		
	}
	public ResultRow(String name,int time){
		this.name=name;
		this.time=time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
}
