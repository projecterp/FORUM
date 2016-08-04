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

@SuppressWarnings("serial")
public class tagServlet extends HttpServlet{
	Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	ArrayList<String> postList=new ArrayList<String>();
	ArrayList<String> comment=new ArrayList<String>();
    String str,str2;		
    long comment_count;
	private String str1;
	static String flag;
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		    
		if(req.getParameter("btn").equals("PostComment"))
		{
		    com.google.appengine.api.datastore.DatastoreService ds = DatastoreServiceFactory.getDatastoreService();  
		    Query q = new Query("Post").addSort("time", SortDirection.DESCENDING);
				q.setFilter(
				new Query.FilterPredicate(
				"time",
				Query.FilterOperator.EQUAL,req.getParameter("time")));
				PreparedQuery pq = ds.prepare(q);
				Entity result=pq.asSingleEntity();
				PrintWriter out =resp.getWriter();
			    str=req.getParameter("comment");	
			//	    @SuppressWarnings("unchecked")
					ArrayList<String> property =(ArrayList<String>) result.getProperty("comment");
					comment_count=(long)result.getProperty("comment_count");
			    if(!str.equals("") && !(str.equals(flag)))
			    {
					result.setProperty("comment",property);
					property.add(str);   
					comment_count++;
					result.setProperty("comment_count",comment_count);
					ds.put(result);
					flag=str;
			    }     	
				RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/index2.jsp");
				jsp.forward(req,resp);
		}
		else{
		    com.google.appengine.api.datastore.DatastoreService ds = DatastoreServiceFactory.getDatastoreService();  
		    Query q = new Query("Post").addSort("time", SortDirection.DESCENDING);
				q.setFilter(
				new Query.FilterPredicate(
				"tag",
				Query.FilterOperator.EQUAL,req.getParameter("btn")));
				PreparedQuery pq = ds.prepare(q);
				PrintWriter out = resp.getWriter();
				for (Entity result : pq.asIterable())
			    {
						out.println("<html>\n"+"<body>\n"+"<p>"+result.getProperty("str")+"</p>"+"</body>"+"</html>");
			    }
		}
	//	RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/index3.jsp");
	//	jsp.forward(req,resp);
	}
	
}
