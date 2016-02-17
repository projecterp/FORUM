package forum_v1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
public class COMMENTServlet extends HttpServlet{
	ArrayList<String> comment=new ArrayList<String>();
	int comment_count=0;
    String str;
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
	    
	    com.google.appengine.api.datastore.DatastoreService ds = DatastoreServiceFactory.getDatastoreService();  
	    Query q = new Query("Post").addSort("time", SortDirection.DESCENDING);
			q.setFilter(
			new Query.FilterPredicate(
			"time",
			Query.FilterOperator.EQUAL,req.getParameter("time")));
			
			PreparedQuery pq = ds.prepare(q);
			PrintWriter out = resp.getWriter();
			Entity result=pq.asSingleEntity();
		    str=req.getParameter("comment");	
		//	    @SuppressWarnings("unchecked")
				ArrayList<String> property =(ArrayList<String>) result.getProperty("comment");
				long comment_count=(long) result.getProperty("comment_count");
				if(!(str.equals(""))){
				  if(!(comment_count==0) && !((property.get(((int) comment_count)-1)).equals(str)))
				  {
						
			      }
			      else
			      {
					result.setProperty("comment",property);
					property.add(str);   
				    comment_count++;
				    result.setProperty("comment_count",comment_count);
			      }	
				}
			ds.put(result);		 
			RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/index2.jsp");
			jsp.forward(req,resp);
	}
}
