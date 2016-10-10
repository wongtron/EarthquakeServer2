package com.leetron;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class GetFromDB
 */
@WebServlet("/GetFromForm3")
public class GetFromForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<Earthquake> earthquakes;

	final String separatorMark = ",";
	private EarthquakeDataUtil earthquakeDataUtil;

	@Resource(name = "jdbc/earthquake")
	private DataSource dataSource;

	private String startYear;
	private String startMonth;
	private String startDate;
	private String endYear;
	private String endMonth;
	private String endDate;
	// String minLatitude;
	// String maxLatitude;
	// String minLongitude;
	// String maxLongitude;
	private String minMagnitude;
	private String maxMagnitude;
	private String searchLocLat;
	private String searchLocLong;
	private String radius;
	private String outputformat;

	int resultCount = 0;

	@Override
	public void init() throws ServletException {
		super.init();

		// create our earthquake db util ... and pass in the conn pool /
		// datasource
		try {
			earthquakeDataUtil = new EarthquakeDataUtil(dataSource);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetFromForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		startYear = request.getParameter("startYear");
		startMonth = request.getParameter("startMonth");
		startDate = request.getParameter("startDate");
		endYear = request.getParameter("endYear");
		endMonth = request.getParameter("endMonth");
		endDate = request.getParameter("endDate");
		// minLatitude = request.getParameter("minLatitude");
		// maxLatitude = request.getParameter("maxLatitude");
		// minLongitude = request.getParameter("minLongitude");
		// maxLongitude = request.getParameter("maxLongitude");
		minMagnitude = request.getParameter("minMagnitude");
		maxMagnitude = request.getParameter("maxMagnitude");
		searchLocLat = request.getParameter("searchLocLat");
		searchLocLong = request.getParameter("searchLocLong");
		radius = request.getParameter("radius");
		outputformat = request.getParameter("outputformat");

		resultCount = 0;

		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		checkParameters();

		try {
			earthquakes = (ArrayList<Earthquake>) earthquakeDataUtil.getEarthquakes(startYear, startMonth, startDate,
					endYear, endMonth, endDate,
					// minLatitude,
					// maxLatitude,
					// minLongitude,
					// maxLongitude,
					minMagnitude, maxMagnitude, searchLocLat, searchLocLong, radius);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// write the search parameters as the first record in the response
		// this is to allow the javascript program to show exactly what
		// parameters were used in the query

		if (earthquakes != null) {
			resultCount = earthquakes.size();
		}

		outHeaderData(out);

		if ((earthquakes != null) && (earthquakes.size() != 0)) {
			for (int i = 0; i < earthquakes.size(); i++) {
				outEQData(out, earthquakes.get(i));
				// append the separator between the records
				if (i != (earthquakes.size() - 1)) {
					out.append("<br />");
				}

			}
		} else {
			// there is no return data to the browser
			out.append("");
			resultCount = 0;
		}
	}

	private void outHeaderData(PrintWriter out) {
		// output the count of items in search result
		out.append(String.valueOf(resultCount) + separatorMark);
		out.append(startYear + separatorMark);
		out.append(startMonth + separatorMark);
		out.append(startDate + separatorMark);
		out.append(endYear + separatorMark);
		out.append(endMonth + separatorMark);
		out.append(endDate + separatorMark);
		out.append(minMagnitude + separatorMark);
		out.append(maxMagnitude + separatorMark);
		if (searchLocLat != null)
			out.append(searchLocLat + separatorMark);
		if (searchLocLong != null)
			out.append(searchLocLong + separatorMark);
		// if last item, do not add the separator Mark
		out.append(radius);
		out.append("<br />");
	}

	private void outEQData(PrintWriter out, Earthquake eq) {
		out.append(eq.get_id() + separatorMark);
		out.append(eq.getEvent_date() + separatorMark);
		out.append(eq.getEvent_time() + separatorMark);
		out.append(eq.getLatitude() + separatorMark);
		out.append(eq.getLongitude() + separatorMark);
		out.append(eq.getDepth() + separatorMark);
		out.append(eq.getMag() + separatorMark);
		out.append(eq.getMagtype() + separatorMark);
		out.append(eq.getNst() + separatorMark);
		out.append(eq.getGap() + separatorMark);
		out.append(eq.getDmin() + separatorMark);
		out.append(eq.getRms() + separatorMark);
		out.append(eq.getNet() + separatorMark);
		out.append(eq.getId() + separatorMark);
		out.append(eq.getUpdated_date() + separatorMark);
		out.append(eq.getUpdated_time() + separatorMark);
		out.append(eq.getPlace() + separatorMark);
		out.append(eq.getType() + separatorMark);
		out.append(eq.getHorizontalerror() + separatorMark);
		out.append(eq.getDeptherror() + separatorMark);
		out.append(eq.getMagerror() + separatorMark);
		out.append(eq.getMagnst() + separatorMark);
		out.append(eq.getStatus() + separatorMark);
		out.append(eq.getLocationsource() + separatorMark);
		out.append(eq.getMagsource() + separatorMark);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private static boolean isNum(String strNum) {
		boolean ret = true;
		try {

			Double.parseDouble(strNum);

		} catch (NumberFormatException e) {
			ret = false;
		}
		return ret;
	}

	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	private void checkParameters() {

		LocalDateTime now = LocalDateTime.now();
		int thisYear = now.getYear();
		int thisMonth = now.getMonthValue();
		int thisDay = now.getDayOfMonth();

		// if startYear is empty, null, not a legal value, then set it to
		// the current year
		if ((startYear == null) || startYear.equals("") || (!isNum(startYear))) {
			startYear = String.valueOf(thisYear);
		} else if (!isInteger(startYear, 10)) {
			startYear = String.valueOf(thisYear);
		} else {
			int year = Integer.parseInt(startYear);
			if (year < 1900 || year > thisYear)
				startYear = String.valueOf(thisYear);
		}
		
		// if startMonth is empty, null, not a legal value, then set it to
		// the current month. Understood this may not be a sensible solution
		// but there is no better way to handle ill cases
		if ((startMonth == null) || startMonth.equals("") || (!isNum(startMonth))) {
			startMonth = String.valueOf(thisMonth);
		} else if (!isInteger(startMonth, 10)) {
			startMonth = String.valueOf(thisMonth);
		} else {
			int month = Integer.parseInt(startMonth);
			if (month < 1 || month > 12)
				startMonth = String.valueOf(thisMonth);
		}
		
		// if startDate is empty, null, not a legal value, then set it to
		// the current date. Understood this may not be a sensible solution
		// but there is no better way to handle ill cases
		if ((startDate == null) || startDate.equals("") || (!isNum(startDate))) {
			startDate = String.valueOf(thisDay);
		} else if (!isInteger(startDate, 10)) {
			startDate = String.valueOf(thisDay);
		} else {
			int day = Integer.parseInt(startDate);
			if (day < 1 || day > 31)
				startDate = String.valueOf(thisDay);
		}
		
		// if endYear is empty, null, not a legal value, then set it to
		// the current year
		if ((endYear == null) || endYear.equals("") || (!isNum(endYear))) {
			endYear = String.valueOf(thisYear);
		} else if (!isInteger(endYear, 10)) {
			endYear = String.valueOf(thisYear);
		} else {
			int year = Integer.parseInt(endYear);
			if (year < 1900 || year > thisYear)
				endYear = String.valueOf(thisYear);
		}

		// if endMonth is empty, null, not a legal value, then set it to
		// the current month. Understood this may not be a sensible solution
		// but there is no better way to handle ill cases
		if ((endMonth == null) || endMonth.equals("") || (!isNum(endMonth))) {
			endMonth = String.valueOf(thisMonth);
		} else if (!isInteger(endMonth, 10)) {
			endMonth = String.valueOf(thisMonth);
		} else {
			int month = Integer.parseInt(endMonth);
			if (month < 1 || month > 12)
				endMonth = String.valueOf(thisMonth);
		}

		// if endDate is empty, null, not a legal value, then set it to
		// the current date. Understood this may not be a sensible solution
		// but there is no better way to handle ill cases
		if ((endDate == null) || endDate.equals("") || (!isNum(endDate))) {
			endDate = String.valueOf(thisDay);
		} else if (!isInteger(endDate, 10)) {
			endDate = String.valueOf(thisDay);
		} else {
			int day = Integer.parseInt(endDate);
			if (day < 1 || day > 31)
				endDate = String.valueOf(thisDay);
		}

		int startMon = Integer.parseInt(startMonth);

		// check if the date is reasonable for the month
		if (startMon == 4 || startMon == 6 || startMon == 9 || startMon == 11) {
			// cannot have day 31. If so, set it to 30
			if (Integer.parseInt(startDate) >= 31) {
				startDate = "30";
			}
		}

		if (startMon == 2) {
			// February cannot have day 30 and 31. If so, set it to 28
			// Do not handle the leap year for now
			if (Integer.parseInt(startDate) >= 30) {
				startDate = "28";
			}
		}

		int endMon = Integer.parseInt(endMonth);

		// check if the date is reasonable for the month
		if (endMon == 4 || endMon == 6 || endMon == 9 || endMon == 11) {
			// cannot have day 31. If so, set it to 30
			if (Integer.parseInt(endDate) >= 31) {
				endDate = "30";
			}
		}

		if (endMon == 2) {
			// February cannot have day 30 and 31. If so, set it to 28
			// Do not handle the leap year for now
			if (Integer.parseInt(endDate) >= 30) {
				endDate = "28";
			}
		}
		
		// if startYear is larger than the endYear, then swap the two
		if (Integer.parseInt(startYear) > Integer.parseInt(endYear)) {
			String s = startYear;
			startYear = endYear;
			endYear = s;
		}
		
		// if the years are the same, check if the startMonth is smaller than the endMonth
		if (startYear.equals(endYear)) {
			if (Integer.parseInt(startMonth) > Integer.parseInt(endMonth)) {
				// swap the month and date
				String s = endMonth;
				endMonth = startMonth;
				startMonth = s;
				
				s = endDate;
				endDate = startDate;
				startDate = s;
				
			}
			
			if (startMonth.equals(endMonth)) {
				if (Integer.parseInt(startDate) > Integer.parseInt(endDate)) {
					String s = endDate;
					endDate = startDate;
					startDate = s;
				}
				
			}
		}
		

		// if minMagnitude is empty, null, not a legal value, then set it to
		// the default value.
		if ((minMagnitude == null) || minMagnitude.equals("") || (!isNum(minMagnitude))) {
			// set default value
			minMagnitude = "5.0";
		} else if ((Double.parseDouble(minMagnitude) < -1.0) || (Double.parseDouble(minMagnitude) > 10.0)) {
			// set default value
			minMagnitude = "5.0";
		}

		// if maxMagnitude is empty, null, not a legal value, then set it to
		// the default value.
		if ((maxMagnitude == null) || maxMagnitude.equals("") || (!isNum(maxMagnitude))) {
			// set default value
			maxMagnitude = "7.0";
		} else if ((Double.parseDouble(maxMagnitude) < -1.0) || (Double.parseDouble(maxMagnitude) > 10.0)) {
			// set default value
			maxMagnitude = "7.0";
		}
		// if maxMagnitude < minMagnitude, exchange the two
		if (Double.parseDouble(maxMagnitude) < Double.parseDouble(minMagnitude)) {
			String s = maxMagnitude;
			maxMagnitude = minMagnitude;
			minMagnitude = s;
		}

		// if radius is empty, null, not a legal value, then set it to
		// the default value.
		if ((radius == null) || radius.equals("") || (!isNum(radius))) {
			// Assume a radius of 500km
			radius = "500";
		} else // the perimeter of earth is about 40000Km
		if ((Double.parseDouble(radius) < 0) || (Double.parseDouble(radius) > 20000)) {
			radius = "500";

		}
	}
}
