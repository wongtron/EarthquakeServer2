package com.leetron;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class EarthquakeServlet
 */
@WebServlet("/EqServlet")
public class EarthquakeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private EarthquakeDataUtil earthquakeDataUtil;
	
	@Resource(name="jdbc/earthquake")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create our student db util ... and pass in the conn pool / datasource
		try {
			earthquakeDataUtil = new EarthquakeDataUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
    /**
     * Default constructor. 
     */
    public EarthquakeServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			listEarthquakes(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void listEarthquakes(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {

			// get Earthquakes from db util
			List<Earthquake> earthquakes = earthquakeDataUtil.getEarthquakes();
			
			// add Earthquakes to the request
			request.setAttribute("EARTHQUAKE_LIST", earthquakes);
					
			// send to JSP page (view)
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-earthquakes.jsp");
			dispatcher.forward(request, response);
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
