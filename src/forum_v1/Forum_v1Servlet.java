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
import com.google.appengine.api.datastore.Query.SortDirection;

import board.Post;
@SuppressWarnings("serial")
public class Forum_v1Servlet extends HttpServlet {
	private String text;
	public int a=0;
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		setText(req.getParameter("kk"));
		resp.setContentType("text/html");
		com.google.appengine.api.datastore.DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Post").addSort("time", SortDirection.DESCENDING);
		q.setFilter(
				new Query.FilterPredicate(
				"str",
				Query.FilterOperator.EQUAL,req.getParameter("kk")));
				PreparedQuery pq = ds.prepare(q);
       PrintWriter out=resp.getWriter();
       out.println(pq);
       Entity t=pq.asSingleEntity();
	  if(t==null){
		out.println(pq);
		Entity post = new Entity("Post");
//POST		
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
		RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/index2.jsp");
			jsp.forward(req,resp);
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
