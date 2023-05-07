import java.sql.*;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class hw3 { 

	// Global Final variables
	private static int id = 0;
	private static String str = " ";
	private static String user = "";
	private static String pass = "";
	private static String link = "jdbc:mysql://";
	
	// Main Method Which takes an args and call the method IN_SQL()
	public static void main(String[] args) throws SQLException{

		link += args[0];
		user = args[1];
		pass = args[2];
		
		IN_SQL();
	}// Main

	
	
	// Method for Inserting into table and also UI in terminal
	public static void IN_SQL() throws SQLException {
		// TODO Auto-generated method stub
		
		// ArrayList for first_names
		ArrayList<String> first_names = new ArrayList<>(Arrays.asList ( 
			"Benjamin", "Leah", "Matthew", "Samantha", "Joshua", "Olivia", "Ethan",
			"Emily", "William", "Natalie", "Samuel", "Elizabeth", "Michael", "Rachel", "David", "Victoria",
			"Christopher", "Madison", "Andrew", "Ava", "Daniel", "Isabella", "Joseph", "Lauren", "Nicholas",
			"Dylan", "Lillian", "Christopher", "Samantha", "Isabella", "Aiden", "Emily", "Elena", "Nicholas",
			"Gabriella", "Leo", "Camila", "Jonathan", "Makayla", "David", "Arianna", "Kylee", "Aedan", "Harper",
			"Dana", "Kolby", "Paulina", "Kayleigh", "Kailyn" ));
	
		// ArrayList for last_names
		ArrayList<String> last_names = new ArrayList<>(Arrays.asList ( 
			"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller",
			"Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson",
			"Garcia", "Martinez", "Robinson", "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen",
			"Jenkins", "Perry", "Powell", "Long", "Patterson", "Hughes", "Flores", "Washington", "Butler",
			"Simmons", "Foster", "Gonzalez", "Bryant", "Alexander", "Russell", "Griffin", "Diaz", "Garrett",
			"Davila", "Gutierrez", "Ponce", "Reese" ));
		
		// ArrayList for department_names
		ArrayList<String> department_names = new ArrayList<>(Arrays.asList("Bio", "Chem", "CS", "Eng", "Math", "Phys"));
		
		// ArrayList for course_names
		ArrayList<String> course_names = new ArrayList<>(Arrays.asList(
			"Introduction to Genetics", "Cell Biology and Biochemistry", "Ecology and Evolution", "Human Anatomy and Physiology", "Biostatistics and Experimental Design", "Bioinformatics", 
			"Calculus I and II", "Linear Algebra", "Probability and Statistics", "Discrete Mathematics", "Differential Equations", "Number Theory", "Introduction to Programming in Java", "Data Structures and Algorithms", 
			"Operating Systems", "Computer Networks", "Artificial Intelligence", "Machine Learning", "Computer Graphics",
			"Mechanics and Waves", "Electricity and Magnetism", "Quantum Mechanics", "Thermodynamics and Statistical Mechanics", "Astrophysics and Cosmology", "Classical Mechanics", 
			"General Chemistry I and II", "Organic Chemistry I and II", "Analytical Chemistry", "Physical Chemistry", "Biochemistry", "Inorganic Chemistry", 
			"Introduction to Literature", "Creative Writing", "Shakespearean Drama", "American Literature", "British Literature" ));
		
		// ArrayList for campus_names
		ArrayList<String> campus_names = new ArrayList<>(Arrays.asList( "Busch", "Livi", "CAC", "CD" ));
		
		// ArrayList for grade_names
		// Reason for having multiple A's, B's, C's because in picking randomly D's and F's probability will be less
		ArrayList<String> grade = new ArrayList<>(Arrays.asList( "A", "A", "A", "B", "B", "B", "A", "C", "D", "F" )); 
		
		
		/*
		 * 
		 * Throughout the code student_id will be used, after inserting into table also storing into stdent_id
		 * arrayList so in the below other loops, can recall the student id.
		 * 
		 */
		ArrayList<Integer> student_id = new ArrayList<>();
		
		// Variable declaration
		Random r = new Random();
		Scanner kb = new Scanner(System.in);
		Connection conn = null; 	
		Statement stmt = null;
		ResultSet rs =null;
		PreparedStatement ps = null;
		int num = 0;
		int fname, lname, major1, major2, minor1, minor2,  camp, credit;
		int major_count = 0;
		int minor_count = 0;

		
		try { 		
			// Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection(link,user, pass);  
			stmt = conn.createStatement();
	
			// Check If the Table Exits previously
			stmt.executeUpdate("DROP TABLE IF EXISTS Majors");
			stmt.executeUpdate("DROP TABLE IF EXISTS Minors");
			stmt.executeUpdate("DROP TABLE IF EXISTS IsTaking");
			stmt.executeUpdate("DROP TABLE IF EXISTS HasTaken");
			stmt.executeUpdate("DROP TABLE IF EXISTS Classes");
			stmt.executeUpdate("DROP TABLE IF EXISTS Students");
			stmt.executeUpdate("DROP TABLE IF EXISTS Departments");
			
			// Create Tables
			stmt.executeUpdate(" CREATE TABLE Departments ( name VARCHAR(50) NOT NULL, campus VARCHAR(50) NOT NULL, PRIMARY KEY (name) )");
			stmt.executeUpdate(" CREATE TABLE Students ( first_name VARCHAR(100) NOT NULL, last_name VARCHAR(100) NOT NULL, id INT UNSIGNED NOT NULL, PRIMARY KEY (ID) ) ");
			stmt.executeUpdate(" CREATE TABLE Classes ( name VARCHAR(50) NOT NULL, credits INT UNSIGNED NOT NULL, PRIMARY KEY (name) )");
			stmt.executeUpdate(" CREATE TABLE Majors (sid INT UNSIGNED NOT NULL, dname VARCHAR(50) NOT NULL, FOREIGN KEY (sid) REFERENCES Students(id), FOREIGN KEY (dname) REFERENCES Departments(name) ) ");
			stmt.executeUpdate(" CREATE TABLE Minors (sid INT UNSIGNED NOT NULL, dname VARCHAR(50) NOT NULL, FOREIGN KEY (sid) REFERENCES Students(id), FOREIGN KEY (dname) REFERENCES Departments(name) ) ");
			stmt.executeUpdate(" CREATE TABLE IsTaking ( sid INT UNSIGNED NOT NULL, name VARCHAR(250) NOT NULL, FOREIGN KEY (sid) REFERENCES Students(id), FOREIGN KEY (name) REFERENCES Classes(name) )");
			stmt.executeUpdate(" CREATE TABLE HasTaken (sid INT UNSIGNED NOT NULL, name VARCHAR(250) NOT NULL, grade VARCHAR(50) NOT NULL, FOREIGN KEY (sid) REFERENCES Students(id), FOREIGN KEY (name) REFERENCES Classes(name) ) ");
			
			// INSERT INTO STUDENTS
			for(int i=0; i< 100; i++) {
				num = r.nextInt(900000000) + 100000000;
				student_id.add(num);
				lname = r.nextInt(last_names.size());
				fname = r.nextInt(first_names.size());
				try {
					ps = conn.prepareStatement("INSERT INTO Students values (?,?,?)");
					ps.setString(1,first_names.get(fname));
					ps.setString(2,last_names.get(lname));
					ps.setInt(3,num);
					ps.executeUpdate();
				} catch (SQLException e) {
					System.out.println("Cannot Insert data into Students Table:");
				}
			}// For
			
			// INSERT INTO DEPARTMENT
			for(int i =0; i<= department_names.size()-1; i++) {
				camp = r.nextInt(campus_names.size());
				try {
					ps = conn.prepareStatement("INSERT INTO Departments values (?,?)");
					ps.setString(1,department_names.get(i));
					ps.setString(2,campus_names.get(camp));
					ps.executeUpdate();
					
				} catch(SQLException e) {
					System.out.println("Cannot Insert data into Department Table:");
				}
			} // For
			
			
			// INSERT INTO CLASSES
			for(int i=0; i<= course_names.size()-1; i++) {
				credit = r.nextInt(2)+3 ;
				try {
					ps = conn.prepareStatement("INSERT INTO Classes values (?,?)");
					ps.setString(1, course_names.get(i));
					ps.setInt(2, credit);
					ps.executeUpdate();
				} catch(SQLException e) {
					System.out.println("Cannot Insert data into Classes Table:");
				}
			}
			

			// INSERTING VALUE INTO MAJOR AND MINOR
			for (int i=0; i <= student_id.size()-1; i++) {
				
				major_count = r.nextInt(2);
				minor_count = r.nextInt(2);
				major1 = r.nextInt(department_names.size());
				do {
					major2 = r.nextInt(department_names.size());
				}while(major2 == major1);
				
				try {
					
					if(major_count == 1) {
						String major = "INSERT INTO Majors VALUES (?, ?)";
						ps = conn.prepareStatement(major);
						ps.setString(2, department_names.get(major1));
						ps.setInt(1, student_id.get(i));
						ps.executeUpdate();
					} else {
						
						String major = "INSERT INTO Majors VALUES (?, ?)";
						ps = conn.prepareStatement(major);
						ps.setString(2, department_names.get(major1));
						ps.setInt(1, student_id.get(i));
						ps.executeUpdate();
						ps = conn.prepareStatement(major);
						ps.setString(2, department_names.get(major2));
						ps.setInt(1, student_id.get(i));
						ps.executeUpdate();
					}
						
					// Getting Minor1 and Minor2 randomly and inserting into table
					do {
						minor1 = r.nextInt(department_names.size());
					}while(minor1 == major1 || minor1 == major2);
					
					do {
						minor2 = r.nextInt(department_names.size());
					}while(minor2 == major1 || minor2 == major2 || minor1 == minor2);
					
					if(minor_count == 1) {				
						String minor = "INSERT INTO Minors VALUES (?, ?)";
						ps = conn.prepareStatement(minor);
						ps.setString(2, department_names.get(minor1));
						ps.setInt(1, student_id.get(i));
						ps.executeUpdate();	
						
					}else {
						String minor = "INSERT INTO Minors VALUES (?, ?)";
						ps = conn.prepareStatement(minor);
						ps.setString(2, department_names.get(minor1));
						ps.setInt(1, student_id.get(i));
						ps.executeUpdate();
						ps = conn.prepareStatement(minor);
						ps.setString(2, department_names.get(minor2));
						ps.setInt(1, student_id.get(i));
						ps.executeUpdate();
					}
				
				} catch (SQLException e) {
					System.err.println(e);
					System.out.println("Error Inserting into Major and Minor: ");
				}
			}
			
			// INSERTING INTO HasTaken and IsTaking table
			int[] values = {0, 1, 2, 3}; 
			for(int i=0; i < student_id.size() ; i++) {
						
				int isTaking = r.nextInt(2)+4;
				Collections.shuffle(course_names);
				for (int k = 0; k < values.length; k++) {
				    int t = r.nextInt(values.length - k) + k;
				    int temp = values[k];
				    values[k] = values[t];
				    values[t] = temp;
				}
				int yr = values[i % 4];
				
				// Based on value array, random generator will pick and assigned to each yr. 
				// Freshmen Students
				if(yr == 0 ) {
					int taking_fr = r.nextInt(6)+1;
					for(int j=0; j< taking_fr; j++) {
						int mygrade = r.nextInt(grade.size());
						try {
							String taken = "INSERT INTO HasTaken VALUES (?, ?, ?)";
							ps = conn.prepareStatement(taken);
							ps.setInt(1, student_id.get(i));
							ps.setString(2, course_names.get(j));
							ps.setString(3, grade.get(mygrade));
							ps.executeUpdate();
						} catch (SQLException e) {
							System.err.println(e);
						}
					}
					
					for(int j= taking_fr; j< taking_fr + isTaking; j++) {
						try {
							//String courses = course_names.get(j);
							String taken = "INSERT INTO IsTaking VALUES (?, ?)";
							ps = conn.prepareStatement(taken);
							ps.setString(2, course_names.get(j));
							ps.setInt(1, student_id.get(i));
							ps.executeUpdate();
							
						} catch (SQLException e) {
							System.err.println(e);
						}
					}
					
				}
				// Sophomore Students
				if(yr == 1 ){
					int taking_so = r.nextInt(7)+7;
					for(int j=0; j< taking_so; j++) {
						int mygrade = r.nextInt(grade.size());
						try {
							String taken = "INSERT INTO HasTaken VALUES (?, ?, ?)";
							ps = conn.prepareStatement(taken);
							ps.setInt(1, student_id.get(i));
							ps.setString(2, course_names.get(j));
							ps.setString(3, grade.get(mygrade));
							ps.executeUpdate();
						} catch (SQLException e) {
							System.err.println(e);
						}
					}
					
					for(int j= taking_so; j< taking_so+ isTaking; j++) {
						try {
							//String courses = course_names.get(j);
							String taken = "INSERT INTO IsTaking VALUES (?, ?)";
							ps = conn.prepareStatement(taken);
							ps.setString(2, course_names.get(j));
							ps.setInt(1, student_id.get(i));
							ps.executeUpdate();
							
						} catch (SQLException e) {
							System.err.println(e);
						}
					}
				
				}
				// Junior Students
				if(yr == 2 ){
					int taking_jr = r.nextInt(7)+14;
					for(int j=0; j< taking_jr; j++) {
						int mygrade = r.nextInt(grade.size());
						try {
							String taken = "INSERT INTO HasTaken VALUES (?, ?, ?)";
							ps = conn.prepareStatement(taken);
							ps.setInt(1, student_id.get(i));
							ps.setString(2, course_names.get(j));
							ps.setString(3, grade.get(mygrade));
							ps.executeUpdate();
						} catch (SQLException e) {
							System.err.println(e);
						}
					}
					
					for(int j= taking_jr; j< taking_jr+ isTaking; j++) {
						try {
							//String courses = course_names.get(j);
							String taken = "INSERT INTO IsTaking VALUES (?, ?)";
							ps = conn.prepareStatement(taken);
							ps.setString(2, course_names.get(j));
							ps.setInt(1, student_id.get(i));
							ps.executeUpdate();
							
						} catch (SQLException e) {
							System.err.println(e);
						}
					}
				
				}
				// Senior Students
				if(yr == 3 ){
					int taking_sr = r.nextInt(7)+21;
					for(int j=0; j< taking_sr; j++) {
						int mygrade = r.nextInt(grade.size());
						try {
							String taken = "INSERT INTO HasTaken VALUES (?, ?, ?)";
							ps = conn.prepareStatement(taken);
							ps.setInt(1, student_id.get(i));
							ps.setString(2, course_names.get(j));
							ps.setString(3, grade.get(mygrade));
							ps.executeUpdate();
						} catch (SQLException e) {
							System.err.println(e);
						}
					}
					
					for(int j= taking_sr; j< taking_sr+ isTaking; j++) {
						try {
							//String courses = course_names.get(j);
							String taken = "INSERT INTO IsTaking VALUES (?, ?)";
							ps = conn.prepareStatement(taken);
							ps.setString(2, course_names.get(j));
							ps.setInt(1, student_id.get(i));
							ps.executeUpdate();
							
						} catch (SQLException e) {
							System.err.println(e);
						}
					}
				}
			}
			
			
			// Running UserInterface
			int choice = 0;
			menu();
			do {
				System.out.println("Which query would you like to run (1-8)?");
				try {
					choice = kb.nextInt();	
				}
				catch(InputMismatchException e) {
					System.out.println(e);
				}
				kb.nextLine();
				
				
				switch(choice) {
				
				case 1:
					String name = " ";
					System.out.println("Please enter the name.");
					try {
						name = kb.nextLine();
					} catch(InputMismatchException e) {
						System.out.println(e);
					}
					int count = 0;
					
					ArrayList<Integer> case1_id = new ArrayList<>();
					
					try {
						str = " SELECT count(*) as std, id FROM STUDENTS WHERE first_name like ? or last_name like ? group by id";
						ps = conn.prepareStatement(str);
						ps.setString(1, "%" + name + "%");
						ps.setString(2, "%" + name + "%");
						
						rs = ps.executeQuery();
						while(rs.next()) {
							id = rs.getInt(2);
							case1_id.add(rs.getInt(2));
							count++;
						}
						System.out.printf("%-3d Students Found \n", count);
						for(int i =0; i < count ; i++) {
							myprint(case1_id.get(i));
						}
					} catch(SQLException e) {
						System.out.println(e);
					}
					

					break;

					
				case 2:
					String year = " ";
					System.out.println("Please enter the year.");
					try { 
						year = kb.nextLine().toLowerCase();
					} catch(InputMismatchException e) {
						System.out.println(e);
					}
					ArrayList<Integer> case2_id = new ArrayList<>();
					int c2 =0 ;
					try {
						str = "SELECT H.sid, SUM(C.credits) AS total_credits FROM HasTaken H JOIN Classes C ON H.name = C.name GROUP BY H.sid";
						rs = stmt.executeQuery(str);
						while(rs.next()) {
							id = rs.getInt(1);
							case2_id.add(id);
							int cr = rs.getInt(2);
							
							if( cr <= 29 && year.equals("fr")) {
								myprint(case2_id.get(c2));
							}
							else if( cr >= 30 && cr <= 59 && year.equals("so") ) {
								myprint(case2_id.get(c2));
							}
							else if( cr >= 60 && cr <= 89 && year.equals("ju")  ) {
								myprint(case2_id.get(c2));
							}
							else if (cr >= 90 && year.equals("sr") ){
								myprint(case2_id.get(c2));
							}	
							else {
								System.out.println("Please check input- (Fr, So, Ju, Sr)");
							}
							c2++;
						}
					} catch(SQLException e) {
							System.out.println(e);
						}
				break;
				
					
				case 3:
					float ask3 = 0;
					ArrayList<Integer> case3_id = new ArrayList<>();
					System.out.println("Please enter the thresold.");
					try {
						ask3 = kb.nextFloat(); 
					} catch(InputMismatchException e) {
						System.out.println(e);
					}
					
					int count3 = 0;
					int count4 = 0;
					int sum = 0;
					float avg = 0;
					try {			
						str = "    SELECT H.sid, \n"
								+ "  SUM(\n"
								+ "    CASE \n"
								+ "      WHEN H.grade ='A' and C.credits = 3 THEN 4\n"
								+ "      WHEN H.grade ='B' and C.credits = 3 THEN 3\n"
								+ "      WHEN H.grade ='C' and C.credits = 3 THEN 2\n"
								+ "      WHEN H.grade ='D' and C.credits = 3 THEN 1\n"
								+ "      WHEN H.grade ='F' and C.credits = 3 THEN 0\n"
								+ "      ELSE NULL\n"
								+ "    END\n"
								+ "  ) * 3 AS 3credit, SUM(\n"
								+ "    CASE \n"
								+ "      WHEN H.grade ='A' and C.credits = 4 THEN 4\n"
								+ "      WHEN H.grade ='B' and C.credits = 4 THEN 3\n"
								+ "      WHEN H.grade ='C' and C.credits = 4 THEN 2\n"
								+ "      WHEN H.grade ='D' and C.credits = 4 THEN 1\n"
								+ "      WHEN H.grade ='F' and C.credits = 4 THEN 0\n"
								+ "      ELSE NULL\n"
								+ "    END\n"
								+ "  ) * 4 AS 4credit,\n"
								+ "		sum(C.credits)\n"
								+ "FROM HasTaken H\n"
								+ "JOIN Classes C ON H.name = C.name \n"
								+ "GROUP BY H.sid;\n";

						rs = stmt.executeQuery(str);
						int c3 =0;
						while(rs.next()) {
							case3_id.add(rs.getInt(1));
							count3 = rs.getInt(2);
							count4 = rs.getInt(3);
							sum = rs.getInt(4);
							avg = (float) (count3 + count4) / sum ;
							if(avg >= ask3) {
								myprint(case3_id.get(c3));	
							}				
							c3++;
						}

					} catch(SQLException e) {
						System.out.println(e);
					}
					break;
					
					
				case 4:
					float ask4 = 0;
					ArrayList<Integer> case4_id = new ArrayList<>();
					System.out.println("Please enter the thresold.");
					try {
						ask4 = kb.nextFloat(); 
					} catch(InputMismatchException e) {
						System.out.println(e);
					}
					try {
						
						int cout3 = 0;
						int cout4 = 0;
						int sum4 = 0;
						float avg4 = 0;
						str = "    SELECT H.sid, \n"
								+ "  SUM(\n"
								+ "    CASE \n"
								+ "      WHEN H.grade ='A' and C.credits = 3 THEN 4\n"
								+ "      WHEN H.grade ='B' and C.credits = 3 THEN 3\n"
								+ "      WHEN H.grade ='C' and C.credits = 3 THEN 2\n"
								+ "      WHEN H.grade ='D' and C.credits = 3 THEN 1\n"
								+ "      WHEN H.grade ='F' and C.credits = 3 THEN 0\n"
								+ "      ELSE NULL\n"
								+ "    END\n"
								+ "  ) * 3 AS 3credit, SUM(\n"
								+ "    CASE \n"
								+ "      WHEN H.grade ='A' and C.credits = 4 THEN 4\n"
								+ "      WHEN H.grade ='B' and C.credits = 4 THEN 3\n"
								+ "      WHEN H.grade ='C' and C.credits = 4 THEN 2\n"
								+ "      WHEN H.grade ='D' and C.credits = 4 THEN 1\n"
								+ "      WHEN H.grade ='F' and C.credits = 4 THEN 0\n"
								+ "      ELSE NULL\n"
								+ "    END\n"
								+ "  ) * 4 AS 4credit,\n"
								+ "		sum(C.credits)\n"
								+ "FROM HasTaken H\n"
								+ "JOIN Classes C ON H.name = C.name \n"
								+ "GROUP BY H.sid;\n";

						rs = stmt.executeQuery(str);
						int c4 = 0;
						while(rs.next()) {
							case4_id.add(rs.getInt(1));
							cout3 = rs.getInt(2);
							cout4 = rs.getInt(3);
							sum4 = rs.getInt(4);
							avg4 = (float) (cout3 + cout4) / sum4 ;
							if(avg4 <= ask4) {
								myprint(case4_id.get(c4));
							}				
							c4++;
						}	
					} catch (SQLException e) {
						System.err.println(e);
					}					
				break;
					
					
				case 5:
					String ask5 = "";
					ArrayList<Integer> case5_id = new ArrayList<>();
					System.out.println("Enter the department name. ");
					try {
						ask5 = kb.nextLine();
					} catch(InputMismatchException e) {
						System.out.println(e);
					}
					 
					int std5 , count5 , sum5 = 0;
					float gpaSum = 0 , avg5 =0;
					try {
						
						str = "SELECT Majors.sid, count(Majors.dname) FROM Majors where dname = ? group by Majors.sid "
								+ "union "
								+ "SELECT Minors.sid, count(Minors.dname) FROM Minors where dname = ? group by Minors.sid" ;
						
						ps = conn.prepareStatement(str);
						ps.setString(1, ask5);
						ps.setString(2, ask5);
						rs = ps.executeQuery();
						int c5 = 0;
						while(rs.next()) {
							std5 = rs.getInt(1);
							count5 = rs.getInt(2);
							sum5 = sum5 + count5;
							
							case5_id.add(std5);
							gpaSum = gpaSum + getGPA(case5_id.get(c5));
							avg5 = gpaSum / sum5;
							
							c5++;
						}
						System.out.printf("Num Students: %-5d \n", sum5);
						System.out.printf("Average GPA: %5.2f \n", avg5);
						
					}catch (Exception e) {
						System.err.println(e);
					}
					break;
					
					
				case 6:
					String ask6 = " ";
					ArrayList<Integer> case6_id = new ArrayList<>();
					System.out.println("Enter the Class name. ");
					try {
						ask6 = kb.nextLine();
					}catch(InputMismatchException e) {
						System.out.println(e);
					} 
					int countClass = 0;
					int countG = 0, c6 =0;
					char[] grades = {'A', 'B', 'C', 'D', 'F'};

					try {
						
						str = "SELECT count(*) FROM HasTaken where name = ? group by name;";
						
						ps = conn.prepareStatement(str);
						ps.setString(1, ask6);
						rs = ps.executeQuery();
						while(rs.next()) {
							countClass = rs.getInt(1);
							System.out.printf("%-3d students currently enrolled \n",countClass);
						}
						
						str = "SELECT grade, count(*) FROM HasTaken where name = ? group by grade ORDER BY FIELD(grade, 'A', 'B', 'C', 'D', 'F')"; 
						
						ps = conn.prepareStatement(str);
						ps.setString(1, ask6);
						rs = ps.executeQuery();
						System.out.println("Grades of previous enrollees:");
						while(rs.next()) {
							//letterG = rs.getInt(1);
							countG = rs.getInt(2);
							System.out.printf("%-2s: %-4d \n", grades[c6], countG );
							c6++;
						}

					} catch (SQLException e) {
						System.err.println(e);
					}
					
					break;

					
				case 7:
					String ask7 = "";
					System.out.println("Please enter the query. ");
					try {
						ask7 = kb.nextLine();
					}catch(InputMismatchException e) {
						System.out.println(e);
					} 
					try {
						// Execute the SQL query
			            stmt = conn.createStatement();
			            rs = stmt.executeQuery(ask7);

			            // Print the results using ResultSetMetaData
			            ResultSetMetaData rsmd = rs.getMetaData();
			            int numColumns = rsmd.getColumnCount();

			            // Print column headers
			            for (int i = 1; i <= numColumns; i++) {
			                System.out.printf("%-40s ",rsmd.getColumnName(i));
			            }
			            System.out.println();

			            // Print each row of the result set
			            while (rs.next()) {
			                for (int i = 1; i <= numColumns; i++) {
			                    System.out.printf("%-40s ", rs.getString(i));
			                }
			                System.out.println();
			            }
					} catch (SQLException e) {
						System.err.println(e);
					}
					break;

					
				case 8:
					System.out.println("GoodBye.");
					conn.close();
					ps.close();
					stmt.close();
					kb.close();
					break;

				}
				
			}while (choice != 8);

		  } // try
		catch (SQLException e){ 
		  System.err.println(e);
		  System.out.println("SQLException: " + e.getMessage());
		  System.out.println("SQLState: " + e.getSQLState());
		  System.out.println("VendorError: " + e.getErrorCode());
			
		  } // catch
		finally {
			if(ps != null) {
				ps.close();
			}
			if(stmt!= null) {
				stmt.close();
			}
			if(conn!= null) {
				conn.close();
			}
			if(kb!= null) {
				kb.close();
			}
		}
	
		
	} // IN_SQL
	
	// menu method
	public static void menu() {
		System.out.println("Welcome to the University database. Queries available");
		System.out.println("1) Search Student by Name");
		System.out.println("2) Search Student by Year");
		System.out.println("3) Search for student with a GPA >= Threshold");
		System.out.println("4) Search for student with a GPA <= Threshold");
		System.out.println("5) Get Department Statistics");
		System.out.println("6) Get class statistics");
		System.out.println("7) Execute an abitary SQL query");
		System.out.println("8) Exit Application");
	}
	
	// Method getName()
	public static void getName(int id) {
		
		Connection conn = null; 	
		ResultSet rs =null;
		PreparedStatement ps = null;
		
		try { 		
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(link,user,pass);  
			
			String str  = "SELECT * FROM Students WHERE id = ?";
			ps = conn.prepareStatement(str);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				System.out.printf("%s, %-10s\n", rs.getString(1), rs.getString(2)); // Print first name, last name
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	// Method getMajor()
	public static void getMajor(int id) throws SQLException {
		
		Connection conn = null; 	
		ResultSet rs =null;
		PreparedStatement ps = null;
		
		try { 		
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection(link,user,pass);  
			int count = 0 ;
			String str  = "SELECT * FROM Majors WHERE sid = ?";
			ps = conn.prepareStatement(str);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(count == 0) {
				System.out.print("Major: ");
				count++;
			}
			while(rs.next()) {
				System.out.printf("%s ", rs.getString(2)); // Print first name, last name
				
			}
			System.out.println();
			
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	// Method getMinor()
	public static void getMinor(int id) throws SQLException{
		
		Connection conn = null; 	
		ResultSet rs =null;
		PreparedStatement ps = null;
		
		try { 		
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection(link,user,pass);  
			int count = 0 ;
			String str  = "SELECT * FROM Minors WHERE sid = ?";
			ps = conn.prepareStatement(str);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(count == 0) {
				System.out.print("Minor: ");
				count++;
			}
			while(rs.next()) {
				System.out.printf("%s ", rs.getString(2)); // Print first name, last name
			}
			System.out.println();
			
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	// Method getGPA()
	public static float getGPA(int id) throws SQLException{
		
		Connection conn = null; 	
		ResultSet rs =null;
		PreparedStatement ps = null;
		int count3 = 0;
		int count4 = 0;
		int sum = 0;
		float avg = 0;	
		
		conn = DriverManager.getConnection(link,user,pass);  

			
		str = "    SELECT H.sid, \n"
				+ "  SUM(\n"
				+ "    CASE \n"
				+ "      WHEN H.grade ='A' and C.credits = 3 THEN 4\n"
				+ "      WHEN H.grade ='B' and C.credits = 3 THEN 3\n"
				+ "      WHEN H.grade ='C' and C.credits = 3 THEN 2\n"
				+ "      WHEN H.grade ='D' and C.credits = 3 THEN 1\n"
				+ "      WHEN H.grade ='F' and C.credits = 3 THEN 0\n"
				+ "      ELSE NULL\n"
				+ "    END\n"
				+ "  ) * 3 AS 3credit, SUM(\n"
				+ "    CASE \n"
				+ "      WHEN H.grade ='A' and C.credits = 4 THEN 4\n"
				+ "      WHEN H.grade ='B' and C.credits = 4 THEN 3\n"
				+ "      WHEN H.grade ='C' and C.credits = 4 THEN 2\n"
				+ "      WHEN H.grade ='D' and C.credits = 4 THEN 1\n"
				+ "      WHEN H.grade ='F' and C.credits = 4 THEN 0\n"
				+ "      ELSE NULL\n"
				+ "    END\n"
				+ "  ) * 4 AS 4credit,\n"
				+ "		sum(C.credits)\n"
				+ "FROM HasTaken H\n"
				+ "JOIN Classes C ON H.name = C.name where H.sid = ? \n"
				+ "GROUP BY H.sid;\n";
		
		ps = conn.prepareStatement(str);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		while(rs.next()) {
			count3 = rs.getInt(2);
			count4 = rs.getInt(3);
			sum = rs.getInt(4);
			avg = (float) (count3 + count4) / sum ;
		}
		return avg;
	}
	
	// Method getCredit()
	public static void getCredit(int id) throws SQLException{
		
		Connection conn = null; 	
		ResultSet rs =null;
		PreparedStatement ps = null;
		
		try { 		
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection(link,user,pass);  
			
			String str  =  " SELECT H.sid, SUM(C.credits) FROM HasTaken H JOIN Classes C ON H.name = C.name where sid = ? GROUP BY H.sid ";
			ps = conn.prepareStatement(str);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				System.out.printf("Credits: %-3d\n", rs.getInt(2)); // Print first name, last name
			}
			
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	// Method myprint()
	public static void myprint(int id) throws SQLException {
		getName(id);
		System.out.println("ID: " + id);
		getMajor(id);
		getMinor(id);
		System.out.printf("GPA: %-4.2f \n", getGPA(id));
		getCredit(id);
		System.out.println();
	}
	
} // hw4

