package libraryBackend;

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.HashMap;
	import java.util.Map;

	public class SessionManager {
		 private static String currentUsername;
		private static Map<String, Integer> sessionMap = new HashMap<>();
	    private static Map<String, String> sessionTypeMap = new HashMap<>();

	    public static void setLoggedInUser(String username, String userType, int userId) {
	    	
	    	currentUsername = username;
	        sessionMap.put(username, userId);
	        sessionTypeMap.put(username, userType);
	    }

	    public static boolean isLoggedIn(String username) {
	        return sessionMap.containsKey(username);
	    }

	    public static int getCurrentMemberId(String username) {
	        return sessionMap.getOrDefault(username, -1);
	    }

	    public static String getCurrentUserType(String username) {
	        return sessionTypeMap.getOrDefault(username, "");
	    }
	    public static String getCurrentUsername() {
	        return currentUsername;
	    }

	    public static void removeUserSession(String username) {
	        sessionMap.remove(username);
	        sessionTypeMap.remove(username);
	    }
		
		
	
	}



