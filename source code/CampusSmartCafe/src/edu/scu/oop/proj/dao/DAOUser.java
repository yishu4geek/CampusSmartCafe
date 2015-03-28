package edu.scu.oop.proj.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;

import edu.scu.oop.proj.entity.Gender;
import edu.scu.oop.proj.entity.Order;
import edu.scu.oop.proj.entity.User;
import edu.scu.oop.proj.entity.UserSession;


public class DAOUser implements DAO<User> {
	private User user; 

	//create user in the database 
	@Override
	public void create(User user) throws RuntimeException{
		try{
		MySQLAccess dao = MySQLAccess.getDBConn();
		PreparedStatement statement = null; 
		statement = (PreparedStatement) dao.getConn().prepareStatement(
				"INSERT INTO user"
				+ "VALUES (NULL, ?, ?, ?, ?, 0, 0");
		statement.setString(1, user.getUsername());
		statement.setString(2, user.getName());
		statement.setString(3, user.getPassword());
		statement.setString(4, user.getGender().toString()); 
		statement.executeUpdate(); 
		}catch (SQLException e) {
			throw new RuntimeException(e); 
		}	 
	}
	
	//delete user in the database 
	@Override
	public void delete(User user) throws RuntimeException {
		try {
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement(
					"DELETE FROM user"
					+ "WHERE id = ?");
			statement.setInt(1, user.getId());
			statement.executeUpdate(); 
		}catch  (SQLException e) {
			throw new RuntimeException(); 
		}
	}
	
	//get the user information (eg: gender) from database based on the userID 
	public User findElementById(int id) throws RuntimeException {
		ResultSet result; 
		try{
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement(
					"SELECT *"
					+ "FROM user "
					+ "WHERE id = ?");
			statement.setInt(1, id);
			result = statement.executeQuery();
			if (result.next()) {
				user = new User(); 
				int temp = result.getInt(1); 
				user.setId(result.getInt(1)); 
				user.setUsername(result.getString(2)); 
				user.setName(result.getString(3)); 
				user.setPassword(result.getString(4)); 
				if (result.getString(5).equals("female")) {
					user.setGender(Gender.FEMALE);
				}
				else {
					user.setGender(Gender.MALE);
				}
				user.setCalorieConsumed(result.getInt(6));
				user.setFundSpend(result.getInt(7));
			}
		}catch (SQLException e) {
			throw new RuntimeException(); 
		}
		return user; 
	} 
	
	//get fund setting value from db 
	public float findFundPreferenceById(int id) throws RuntimeException {
		float fundPreference = 0; 
		try{
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement(
					"SELECT setting_fund "
					+ "FROM preference_setting "
					+ "WHERE user_id = ?");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				fundPreference = result.getInt(1); 
			}
		}catch (SQLException e) {
			throw new RuntimeException(); 
		}
		return fundPreference; 
	}
	
	//get calories setting value from db 
	public int findCaloriesPreferenceById(int id) throws RuntimeException { 
		int caloriesPreference = 0; 
		try{
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			System.out.println("the id is " + id); 
			statement = (PreparedStatement) dao.getConn().prepareStatement(
					"SELECT * FROM preference_setting "
					+ "WHERE user_id = ?");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				caloriesPreference = result.getInt(2); 
				System.out.println(caloriesPreference); 
			}
		}catch (SQLException e) {
			throw new RuntimeException(); 
		}
		return caloriesPreference; 
	} 
	
	//set calorie preference in db 
	public void setCaloriesPreferenceById(int id, int calories) throws RuntimeException {
		try{
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			System.out.println("the id is " + id); 
			statement = (PreparedStatement) dao.getConn().prepareStatement("UPDATE preference_setting SET setting_calorie = ? WHERE user_id = ?");
			statement.setInt(1, calories);
			statement.setInt(2, id);
			statement.executeUpdate();
		}catch (SQLException e) {
			throw new RuntimeException(); 
		} 
	}
	
	//set fund preference in db 
	public void setFundPreferenceById(int id, float fund) throws RuntimeException {
		try{
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			System.out.println("the id is " + id); 
			statement = (PreparedStatement) dao.getConn().prepareStatement("UPDATE preference_setting SET setting_fund = ? WHERE user_id = ?");
			statement.setFloat(1, fund);
			statement.setInt(2, id);
			statement.executeUpdate();
		}catch (SQLException e) {
			throw new RuntimeException(); 
		} 
	}
	
	//get fund spent by the user for current month 
	public float getFundSpentById(int id) throws RuntimeException {
		float fund_spent = 0; 
		try {
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement("SELECT fund_spend FROM user WHERE id = ?");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery(); 
			if (result.next()) {
				fund_spent = result.getInt(1); 
			}
		}catch (SQLException e) {
			throw new RuntimeException(); 
		}
		return fund_spent; 
	}
	
	//get calories consumed by the user for current date 
	public int getCaloriesConsumedById(int id) throws RuntimeException {
		int calories_consumed = 0; 
		try {
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement("SELECT calorie_consumed FROM user WHERE id = ?");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery(); 
			if (result.next()) {
				calories_consumed = result.getInt(1); 
			}
		}catch (SQLException e) {
			throw new RuntimeException(); 
		}
		return calories_consumed; 
	} 
	
	//update calories 
	public void updateCaloriesConsumedById(int id, int calories) throws RuntimeException {
		int calories_consumed = this.getCaloriesConsumedById(id); 
		try {
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement("UPDATE user SET calorie_consumed = ? WHERE id = ?");
			statement.setInt(1, calories + calories_consumed);
			statement.setInt(2, id);
			statement.executeUpdate(); 
		}catch (SQLException e) {
			throw new RuntimeException(); 
		}
	}
	
	
	public void updateFundSpentById(int id, float fund) throws RuntimeException {
		float fund_spent = this.getFundSpentById(id); 
		try {
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement("UPDATE user SET fund_spend = ? WHERE id = ?");
			statement.setFloat(1, fund + fund_spent);
			statement.setInt(2, id);
			statement.executeUpdate(); 
		}catch (SQLException e) {
			throw new RuntimeException(); 
		}
	}  
	
	
	//check if the user exists in db based on id and password 
	public boolean checkExist(int id, String password) throws RuntimeException {
		try {
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement(
					"SELECT *"
					+ "FROM user "
					+ "WHERE id = ? AND password = ?");
			statement.setInt(1, id);
			statement.setString(2, password);
			ResultSet result = statement.executeQuery();
			//int count = result.getInt(1); 
			if (!result.next() ) {
			    return false; 
			} 
			user = new User(); 
			int temp = result.getInt(1); 
			user.setId(result.getInt(1)); 
			user.setUsername(result.getString(2)); 
			user.setName(result.getString(3)); 
			user.setPassword(result.getString(4)); 
			if (result.getString(5).equals("female")) {
				user.setGender(Gender.FEMALE);
			}
			else {
				user.setGender(Gender.MALE);
			}
			user.setCalorieConsumed(result.getInt(6));
			user.setFundSpend(result.getInt(7));
			UserSession.getInstance().setCurrentUser(user);
		}catch  (SQLException e) {
			throw new RuntimeException(); 
		}
		return true; 
	}
	
	
	//get the list of food today consumed and their calories 
	public ArrayList<ArrayList<Object>> getCaloriesHistory(int id) throws RuntimeException {
		
		ArrayList<ArrayList<Object> > items = new ArrayList<ArrayList<Object> >(); 
		try{
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement(
					"SELECT id, food_id, amount, calorie, time "
					+ "FROM user_order "
					+ "WHERE user_id = ? AND time >= curdate() AND time < curdate() + interval 1 day ");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery(); 
			int order_id = 0; 
			String food_name;
			int amount = 0; 
			int calories = 0;
			Date time;  
			while (result.next()) {
				ArrayList<Object> item = new ArrayList<Object>(); 
				order_id = result.getInt(1); 
				food_name = DAOFactory.getFoodDAO().getFoodName(result.getInt(2)); 
				amount = result.getInt(3); 
				calories = result.getInt(4);
				time = result.getDate(5); 
				item.add(order_id); 
				item.add(food_name);
				item.add(amount); 
				item.add(calories);
				item.add(time); 
				items.add(item); 
			}
		}catch (SQLException e) {
			throw new RuntimeException(); 
		}
		return items;
	}
	
	//get the list of food bought this month and their prices 
	public ArrayList<ArrayList<Object>> getFundHistory(int id) throws RuntimeException {
		ArrayList<ArrayList<Object> > items = new ArrayList<ArrayList<Object> >(); 
		try{
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement(
					"SELECT id, food_id, amount, price, time "
					+ "FROM user_order "
					+ "WHERE user_id = ? AND YEAR(time) = YEAR(CURDATE()) AND MONTH(time) = MONTH(CURDATE()) ");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery(); 
			int order_id = 0; 
			String food_name;
			int amount = 0; 
			float price = 0;
			Date time;  
			while (result.next()) {
				ArrayList<Object> item = new ArrayList<Object>(); 
				order_id = result.getInt(1); 
				food_name = DAOFactory.getFoodDAO().getFoodName(result.getInt(2)); 
				amount = result.getInt(3); 
				price = result.getFloat(4);
				time = result.getDate(5); 
				item.add(order_id); 
				item.add(food_name);
				item.add(amount); 
				item.add(price);
				item.add(time); 
				items.add(item); 
			}
		}catch (SQLException e) {
			throw new RuntimeException(); 
		}
		return items;
	}
	
	//get the pie data based on the user's consumption 
	public ArrayList<Integer> getPieData(User user) {
	    java.sql.PreparedStatement stmt = null;
	    int user_id = user.getId();
	    Date date = new Date();
	    System.out.println("Today's date: " + date);
	    System.out.println("the user is " + user);
	    ArrayList<Integer> pieData = new ArrayList<Integer>();
	    String query = "SELECT SUM(f.carbs)AS totalCarbs,SUM(f.fat)AS totalFat,SUM(f.protein)AS totalProtein "
	            + "from campus_smart_cafe.`user_order` o,campus_smart_cafe.`food` f "
	            + "where o.user_id = ? AND o.food_id = f.id  AND o.time >= curdate() AND o.time < curdate()+ interval 1 day ";
	    try {
	        stmt = (java.sql.PreparedStatement) MySQLAccess.getDBConn().getConn().prepareStatement(query);
	        stmt.setInt(1, user_id);
	        ResultSet res = stmt.executeQuery();	
	        while (res.next()) {
	            int carbs = res.getInt("totalCarbs");
	            int fat = res.getInt("totalFat");
	            int protein = res.getInt("totalProtein");
	
	            pieData.add(new Integer(carbs));
	            pieData.add(new Integer(fat));
	            pieData.add(new Integer(protein));
	            for(int i =0;i<pieData.size();i++){
	                System.out.println(pieData.get(i));
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Get Order has exception: " + e.getMessage());
	    }
	    return pieData;
	}
	
	//get the fund spent by the user this month 
	public float getFundSpend(User user){
        java.sql.PreparedStatement statement = null;
        int user_id = user.getId();
        Date date = new Date();
        float fund_spend = 0;
        String query = "SELECT SUM(f.price)AS fundSpend "
                + "from campus_smart_cafe.`user_order` o,campus_smart_cafe.`food` f "
                + "where o.user_id = ? AND o.food_id = f.id AND YEAR(o.time) = YEAR(CURRENT_DATE()) AND MONTH(o.time) = MONTH(CURRENT_DATE()) ";
        try{
            statement = (java.sql.PreparedStatement) MySQLAccess.getDBConn().getConn().prepareStatement(query);
            statement.setInt(1, user_id);
            ResultSet res = statement.executeQuery();
            while(res.next()) {
                fund_spend = res.getInt("fundSpend");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return fund_spend;
    }
	
	//get the calories consumed today 
	public int getCalorieConsumed(User user){
        java.sql.PreparedStatement statement = null;
        int user_id = user.getId();
        Date date = new Date();
        int calorie_consume = 0;
        String query = "SELECT SUM(f.calorie)AS calorieConsume "
                + "from campus_smart_cafe.`user_order` o,campus_smart_cafe.`food` f "
                + "where o.user_id = ? AND o.food_id = f.id AND time >= curdate() AND time < curdate() + interval 1 day";
        try{
            statement = (java.sql.PreparedStatement) MySQLAccess.getDBConn().getConn().prepareStatement(query);
            statement.setInt(1, user_id);
            ResultSet res = statement.executeQuery();
            while(res.next()) {
                calorie_consume = res.getInt("calorieConsume");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calorie_consume;
    }
	
	//get the average nutrition (calories, fat, carbs, protein)  consumption on current date for the entire user base in campus 
	public ArrayList<Integer> getGrossAverage() throws RuntimeException {
		ArrayList<Integer> grossAvg = new ArrayList<Integer>(); 
		try{
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement(
					"SELECT AVG(a.calorie) AS calorie "
					+ "FROM (SELECT user_id, SUM(calorie) AS calorie "
					+ "FROM user_order "
					+ "WHERE time >= curdate() AND time < curdate() + interval 1 day "
					+ "GROUP BY user_id) AS a ");
			ResultSet result = statement.executeQuery(); 
			if (result.next()) {
				grossAvg.add(result.getInt(1)); 
			}
			statement = (PreparedStatement) dao.getConn().prepareStatement(
					"SELECT AVG(a.fat) AS fat, AVG(a.protein) AS protein, AVG(a.carbs) AS carbs "
					+ "FROM (SELECT b.user_id, SUM(c.fat) AS fat, SUM(c.protein) AS protein, SUM(c.carbs) AS carbs "
					+ "FROM user_order b, food c "
					+ "WHERE b.time >= curdate() AND time < curdate() + interval 1 day "
					+ "AND b.food_id = c.id "
					+ "GROUP BY user_id) as a ");
			result = statement.executeQuery();
			if (result.next()) {
				grossAvg.add(result.getInt(1)); 
				grossAvg.add(result.getInt(2)); 
				grossAvg.add(result.getInt(3)); 
			}	
		}catch(SQLException e) {
			throw new RuntimeException(); 
		}
		return grossAvg; 
	}
	
	//get the average nutrition (calories, fat, carbs, protein)  consumption on current date for the user group with same gender in campus 
	public ArrayList<Integer> getGrossAverageWithGender(Gender gender) throws RuntimeException {
		ArrayList<Integer> grossAvgInGender = new ArrayList<Integer>(); 
		try{
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement(
					"SELECT AVG(a.calorie) AS calorie "
					+ "FROM (SELECT b.user_id, SUM(b.calorie) AS calorie "
					+ "FROM user_order AS b, user AS c "
					+ "WHERE time >= curdate() AND time < curdate() + interval 1 day "
					+ "AND b.user_id = c.id "
					+ "AND c.gender = ? "
					+ "GROUP BY b.user_id) AS a ");
			statement.setString(1, gender.toString());
			ResultSet result = statement.executeQuery(); 
			if (result.next()) {
				grossAvgInGender.add(result.getInt(1)); 
			}
			statement = (PreparedStatement) dao.getConn().prepareStatement(
					"SELECT AVG(a.fat) AS fat, AVG(a.protein) AS protein, AVG(a.carbs) AS carbs "
					+ "FROM (SELECT b.user_id, SUM(c.fat) AS fat, SUM(c.protein) AS protein, SUM(c.carbs) AS carbs "
					+ "FROM user_order b, food c, user d "
					+ "WHERE b.time >= curdate() AND time < curdate() + interval 1 day "
					+ "AND b.food_id = c.id "
					+ "AND b.user_id = d.id "
					+ "AND d.gender = ? "
					+ "GROUP BY user_id) as a ");
			statement.setString(1, gender.toString());
			result = statement.executeQuery();
			if (result.next()) {
				grossAvgInGender.add(result.getInt(1)); 
				grossAvgInGender.add(result.getInt(2)); 
				grossAvgInGender.add(result.getInt(3)); 
			}	
		}catch(SQLException e) {
			throw new RuntimeException(); 
		}
		return grossAvgInGender; 
	}
	
	//get your own nutrition profile for today 
	public ArrayList<Integer> getOwnDietaryProfile(User user) throws RuntimeException {
		ArrayList<Integer> dietaryProfile = new ArrayList<Integer>(); 
		try{
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement(
					"SELECT SUM(a.calorie) AS calorie, SUM(b.fat) AS fat, SUM(b.protein) AS protein, SUM(b.carbs) AS carbs "
					+ "FROM user_order AS a, food AS b "
					+ "WHERE a.time >= curdate() AND a.time < curdate() + interval 1 day "
					+ "AND a.food_id = b.id "
					+ "AND a.user_id = ? ");
			statement.setInt(1, user.getId());
			ResultSet result = statement.executeQuery(); 
			if (result.next()) {
				dietaryProfile.add(result.getInt(1)); 
				dietaryProfile.add(result.getInt(2)); 
				dietaryProfile.add(result.getInt(3)); 
				dietaryProfile.add(result.getInt(4)); 
			}
		}catch(SQLException e) {
			throw new RuntimeException(); 
		}
		return dietaryProfile; 
	}
	
	//get the fund spent via Vending Machine this month  
    public float getFundSpendVM(User user){
        java.sql.PreparedStatement statement = null;
        int user_id = user.getId();
        Date date = new Date();
        float fund_spendVM = 0;
        String query = "SELECT SUM(f.price)AS fundSpendVM"
        +" from campus_smart_cafe.`user_order` o,campus_smart_cafe.`food` f"
        +" where o.user_id = ? AND o.food_id = f.id AND f.type = 'VM' AND YEAR(o.time) = YEAR(CURRENT_DATE()) AND MONTH(o.time) = MONTH(CURRENT_DATE());";

        try{
            statement = (java.sql.PreparedStatement) MySQLAccess.getDBConn().getConn().prepareStatement(query);
            statement.setInt(1, user_id);
            ResultSet res = statement.executeQuery();
            System.out.print(res);
            while(res.next()) {
                fund_spendVM = res.getInt("fundSpendVM");
                System.out.println("FundSpendVM:" + fund_spendVM);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fund_spendVM;
    }

    //get fund spent via cafe this month  
    public float getFundSpendCafe(User user){
        java.sql.PreparedStatement statement = null;
        int user_id = user.getId();
        Date date = new Date();
        float fund_spendCafe = 0;
        String query = "SELECT SUM(f.price*o.amount)AS fundSpendCafe"
        +" from campus_smart_cafe.`user_order` o,campus_smart_cafe.`food` f"
        +" where o.user_id = ? AND o.food_id = f.id AND f.type = 'Cafe' AND YEAR(o.time) = YEAR(CURRENT_DATE()) AND MONTH(o.time) = MONTH(CURRENT_DATE());";

        try{
            statement = (java.sql.PreparedStatement) MySQLAccess.getDBConn().getConn().prepareStatement(query);
            statement.setInt(1, user_id);
            ResultSet res = statement.executeQuery();
            System.out.print(res);
            while(res.next()) {
                fund_spendCafe = res.getInt("fundSpendCafe");
                System.out.println("FundSpendCafe:" + fund_spendCafe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fund_spendCafe;
    }

    //get calories consumed via vending machine today 
    public int getCalorieConsumed_vm(User user){
        java.sql.PreparedStatement statement = null;
        int user_id = user.getId();
        Date date = new Date();
        int calorie_consume = 0;
        String query = "SELECT SUM(f.calorie) As calorieSpendVM, o.amount, f.type"
        +" from campus_smart_cafe.`user_order` o,campus_smart_cafe.`food` f"
        +" where o.user_id = ? AND o.food_id = f.id AND f.type = 'VM' AND time >= curdate() AND time < curdate() + interval 1 day;";
        try{
            statement = (java.sql.PreparedStatement) MySQLAccess.getDBConn().getConn().prepareStatement(query);
            statement.setInt(1, user_id);
            ResultSet res = statement.executeQuery();
            System.out.print(res);
            while(res.next()) {
                calorie_consume = res.getInt("calorieSpendVM");
                System.out.println("CalorieConsumeVM:" + calorie_consume);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calorie_consume;
    }
    
    //get calories conusmed via cafe today 
    public int getCalorieConsumed_Cafe(User user){
        java.sql.PreparedStatement statement = null;
        int user_id = user.getId();
        Date date = new Date();
        int calorie_consume_cafe = 0;
        String query = "SELECT SUM(f.calorie*o.amount) AS calorieSpendCafe, o.amount, f.type"
        +" from campus_smart_cafe.`user_order` o,campus_smart_cafe.`food` f"
        +" where o.user_id = ? AND o.food_id = f.id AND f.type = 'Cafe' AND time >= curdate() AND time < curdate() + interval 1 day;";
        try{
            statement = (java.sql.PreparedStatement) MySQLAccess.getDBConn().getConn().prepareStatement(query);
            statement.setInt(1, user_id);
            ResultSet res = statement.executeQuery();
            System.out.print(res);
            while(res.next()) {
                calorie_consume_cafe = res.getInt("calorieSpendCafe");
                System.out.println("calorieSpendCafe:" + calorie_consume_cafe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calorie_consume_cafe;
    }

	//won't be used for User scenario 
	public ArrayList<User> findElements(int id) throws UnsupportedOperationException, RuntimeException {
		throw new UnsupportedOperationException(); 
	}
	
	@Override
	public void update(int id) throws UnsupportedOperationException, RuntimeException {
		throw new UnsupportedOperationException(); 
	}


	
}
