package forum_v1;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

import board.Post;
@SuppressWarnings("serial")
public class Forum_v1Servlet extends HttpServlet {
	private String text;
	public static String prev_topic;
	public int a=0;
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("text/html");
		com.google.appengine.api.datastore.DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

    if((req.getParameter("btn").equals("ADD POST")))	
    {	
    	
    	Query q = new Query("Post").addSort("time", SortDirection.DESCENDING);
		q.setFilter(
				new Query.FilterPredicate(
				"str",
				Query.FilterOperator.EQUAL,req.getParameter("post")));
				PreparedQuery pq = ds.prepare(q);
       PrintWriter out=resp.getWriter();
       Entity t=pq.asSingleEntity();
	   if(t==null){
		setText(req.getParameter("post"));
		Entity post = new Entity("Post");
//POST		
        post.setProperty("sub",req.getParameter("sub"));
        post.setProperty("sem",req.getParameter("sem"));
        post.setProperty("topic",req.getParameter("topic"));
		post.setProperty("str",getText());
		ArrayList<String> temp=new ArrayList();
		temp.add("");
		post.setProperty("comment",temp);
		post.setProperty("comment_count",a);
//TIME		
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		post.setProperty("time",sdf.format(cal.getTime()));
//TAGS		
		ArrayList<String> tag=new ArrayList();
		for (String retval:req.getParameter("tag").split(",")){
			tag.add(retval);
	      }
	    post.setProperty("tag",tag);		
	    ds.put(post);
	  }
	  RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/index2.jsp");
	  jsp.forward(req,resp);
	  
    }
    else if((req.getParameter("btn").equals("ADD NEW TOPIC")))
    {
    	 String temp;
		 temp=req.getParameter("topic");
    	 if(!(temp.equals("")) && !(temp.equals(prev_topic)))
    	 {
    		    prev_topic=temp;
    	        Filter semFilter = 
    		      new FilterPredicate("sem",
    		                      FilterOperator.EQUAL,
    		                      req.getParameter("sem"));
    		    Filter subFilter =
    		      new FilterPredicate("sub",
    		                      FilterOperator.EQUAL,
    		                      req.getParameter("sub"));                  
    		    Filter validFilter = CompositeFilterOperator.and(semFilter,subFilter);
    		    Query q = new Query("Topics").setFilter(validFilter).addSort("time", SortDirection.DESCENDING);
    		    PreparedQuery pq = ds.prepare(q);
    		    Entity topic=pq.asSingleEntity();
    		    ArrayList<String>str=new ArrayList();
    		   
    		  if(topic!=null)
    		  {  
    		    str=(ArrayList<String>)topic.getProperty("topics");
                str.add(temp);
                topic.setProperty("topics",str);
                ds.put(topic);
    		  }
    		  else
    		  {
    			  Calendar cal = Calendar.getInstance();
    			  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    			  Entity xyz = new Entity("Topics");
    			  str.add(temp);
    			  xyz.setProperty("topics",str);
    			  xyz.setProperty("sub",req.getParameter("sub"));
    			  xyz.setProperty("sem",req.getParameter("sem"));
    			  xyz.setProperty("time",sdf.format(cal.getTime()));
    			  ds.put(xyz);
    		  }
    	 }		  
        	  RequestDispatcher jsp = req.getRequestDispatcher("/topics.jsp");
        	  jsp.forward(req,resp);
        	  
    }
    else
    {
  	  RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/index2.jsp");
	  jsp.forward(req,resp);

    }
	    /*	Query q = new Query("Post").addSort("height", SortDirection.DESCENDING);
	 
		q.setFilter(
		new Query.FilterPredicate(
		"no",
		Query.FilterOperator.LESS_THAN_OR_EQUAL,
		10));
		PreparedQuery pq = ds.prepare(q);
		ArrayList<String> postList=new ArrayList();
		for (Entity result : pq.asIterable()) {
			
			((ArrayList) postList).add(result.getProperty("str"));
		}
			
		req.setAttribute("kk",postList);
		*/

		}

/*		
		PrintWriter out = resp.getWriter();
		  String title = "Using GET Method to Read Form Data";
	      String docType =
	      "<!doctype html public \"-//w3c//dtd html 4.0 " +
	      "transitional//en\">\n";
	      out.println(docType +
	                "<html>\n" +
	                "<head><title>" + title + "</title></head>\n" +
	                "<body bgcolor=\"#f0f0f0\">\n" +
	                "<h1 align=\"center\">" + title + "</h1>\n" +
	                "<ul>\n" +
	                "  <li><b>text</b>: "
	                + req.getParameter("kk") + "\n" +
	                "</ul>\n" +
	                "</body></html>");
*/	                

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
