package board;

import java.util.ArrayList;

import javax.persistence.Entity;

@Entity
public class Topics {

  private String sem;
  private String sub;
  private ArrayList<String> topics =new ArrayList();
  private String time;
public String getSem() {
	return sem;
}
public void setSem(String sem) {
	this.sem = sem;
}
public String getSub() {
	return sub;
}
public void setSub(String sub) {
	this.sub = sub;
}
public ArrayList<String> getTopics() {
	return topics;
}
public void setTopics(ArrayList<String> topics) {
	this.topics = topics;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
	
}
