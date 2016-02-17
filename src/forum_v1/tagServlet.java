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
	ArrayList<String> postList=new ArrayList();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		    
		    com.google.appengine.api.datastore.DatastoreService ds = DatastoreServiceFactory.getDatastoreService();  
		    Query q = new Query("Post").addSort("time", SortDirection.DESCENDING);
				q.setFilter(
				new Query.FilterPredicate(
				"tag",
				Query.FilterOperator.EQUAL,req.getParameter("submit")));
				PreparedQuery pq = ds.prepare(q);
				PrintWriter out = resp.getWriter();
				for (Entity result : pq.asIterable())
			    {
						out.println("<html>\n"+"<body>\n"+"<p>"+result.getProperty("str")+"</p>"+"</body>"+"</html>");
			    }
		
	//	RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/index3.jsp");
	//	jsp.forward(req,resp);
     
	}
	
}
