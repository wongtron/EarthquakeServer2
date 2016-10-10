package com.leetron;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
 * Servlet implementation class GetFromDB
 */
@WebServlet("/GetFromDB")
public class GetFromDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<Earthquake> earthquakes;
	
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
     * @see HttpServlet#HttpServlet()
     */
    public GetFromDB() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			earthquakes = (ArrayList<Earthquake>) earthquakeDataUtil.getEarthquakes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < earthquakes.size(); i++) {
			
			outEQData(out, earthquakes.get(i));
			// append the separator between the records 
			if (i != (earthquakes.size()-1)) {
				out.append("<br />");
			}
			
		}
		
	}
	
	private void outEQData (PrintWriter out, Earthquake eq) {
		final String separatorMark = ",";
		out.append(eq.get_id()+separatorMark);
		out.append(eq.getEvent_date()+separatorMark);
		out.append(eq.getEvent_time()+separatorMark);
		out.append(eq.getLatitude()+separatorMark);
		out.append(eq.getLongitude()+separatorMark);
		out.append(eq.getDepth()+separatorMark);
		out.append(eq.getMag()+separatorMark);
		out.append(eq.getMagtype()+separatorMark);
		out.append(eq.getNst()+separatorMark);
		out.append(eq.getGap()+separatorMark);
		out.append(eq.getDmin()+separatorMark);
		out.append(eq.getRms()+separatorMark);
		out.append(eq.getNet()+separatorMark);
		out.append(eq.getId()+separatorMark);
		out.append(eq.getUpdated_date()+separatorMark);
		out.append(eq.getUpdated_time()+separatorMark);
		out.append(eq.getPlace()+separatorMark);
		out.append(eq.getType()+separatorMark);
		out.append(eq.getHorizontalerror()+separatorMark);
		out.append(eq.getDeptherror()+separatorMark);
		out.append(eq.getMagerror()+separatorMark);
		out.append(eq.getMagnst()+separatorMark);
		out.append(eq.getStatus()+separatorMark);
		out.append(eq.getLocationsource()+separatorMark);
		out.append(eq.getMagsource()+separatorMark);
		
	}
	private List<Earthquake> listEarthquakes(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {

			// get Earthquakes from db util
			List<Earthquake> earthquakes = earthquakeDataUtil.getEarthquakes();
			
			return earthquakes;
					
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
