Command to Run the program: 
	1: Download this mysql-connector-j-8.0.32.jar from [https://dev.mysql.com/downloads/]
	2: Add this jar file to reference Lib
	 
-> Make sure you are in the directory where hw3.java file is at.
-> Make sure database already exists inside the Workbench and use that database in Command below:
-> Also included an image in src path where I showed my terminal command check:
-> Then Run the below command:
	
	Format:- java -cp /path/to/jarfile/mysql-connector-j-8.0.32.jar hw3.java localhost:3306/uniDB USERNAME PASSWORD\
	
	EXAMPLE my command was-: java -cp /Users/kushpatel/Downloads/mysql-connector-j-8.0.32/mysql-connector-j-8.0.32.jar hw3.java localhost:3306/uniDB root password




Randomly Generating Data Into Table: 
	
	-> Inside the MAIN Function taking an args given by the user from the terminal and using those 
		credentials to connect with mysql. Also, Calling the method IN_SQL
	
	1) IN_SQL method:

		>> Creating an arrayList of string for first_name, last_name, department_name, course_name, campus_names,  
			grade, and student_id. Also using the random module, to pick items randomly from the above arrayList. 
		
		>> Creating an database: CS336
		>> Creating following tables (field names): 
			

				
		>> Departments (name, campus)
			-> Field names with name and campus are not null
			-> Given primary key to name.
			
			* Selecting an random campus index from the arrayList of 'campus_names' using the index assigning the campus to 
				particular department. Like, let's it picked 'Livi' and randomly it will assign to any department Like 'Bio' 
		 
		 
		 
		 
		 
		 
		>> Students (first_name, last_name, id)
			-> Field names with first_name and last_names are not null
			-> id is an unsigned int not null and primary key (id)
			
			* Picking an randomly selected distinct value of 'id' range from smallest 9-digit to largest 9-digit. 
			* selecting random index from the arrayList of 'first_name' and 'last_name'. Using that index inserting the values of random
				names into the Student data base and assigning 'id' number to each students.
			* NOTE: After Picking randomly all the 'id' for each student, then storing all the ids into the arrayList of 'students_id'. 
			
			
			
			
			
			
			
		>> Classes (name, credits)
			-> Field names with class name (varchar) and its credits (int)
			-> Primary key given to name
		   
		   * putting all the classes into database from arrayList of 'course_names' and giving the random value of credits 3 or 4
		   		to each course. Like 'Calculus 2' has 4 credits. 
		   
		   
		   
		   
		   
		   
		>> Majors (sid, dname)    &&    Minors (sid, dname)
			-> sid is the student id which is referenced from table Students
			-> dname is the department name which is reference from table Departments
		
			*  ASSUMEING that department name is not repeated in major and minor and student is allowed to have max 2 major and minor and minimum is none. 
			* Like if a student is doing major in 'math' cannot have same department for 'another major' or 'any minor'. 
			* First of all, having an random count of major and minor. A maximum majors and minors a student can do 2 each or none. 
			* Depends on student major and minor count, assigning a department name based on above assumed constraint.
			* Using do-while loop to find the different department name for each case until get the different department. 
			
			
			 	
			 	
			 	
			 		
		>> IsTaking (sid, name)
			-> sid is the student id which is referenced from table Students
			-> name is the Class name which is referenced from table Classes.
			
			* First of all, having an array of [0,1,2,3] and selecting randomly and assign to [fr, so, jr, sr] respectively.
			* Using if-else statement inserting the classes into Istaking. 
			* Inside the loops, having an random number of picking 4 or 5, assuming all students took 4 or 5 classes each year.
			* Using int value 4 and 5, picking courses from arrayList of 'coure_names' and inserting into isTaking database.     
			
			
			
			
			
		>> HasTaken (sid, name, grade)
			-> sid is the student id which is referenced from table Students.
			-> name is the Class name which is referenced from table Classes
			-> Last coln is grades earn by the student in the class in the previously. 
			
			* First of all, having an array of [0,1,2,3] and selecting randomly and assign to [fr, so, jr, sr] respectively.
			* Using if-else statement inserting the classes into HasTaken.
			* Loops of HasTaken and IsTaking runs in the same loops
			* Using Collection and Shuffle module to have different course while picking for 'Istaking' and 'HasTaken'. So, 
				Case of student hasTaken courses names and IsTaking courses names are different while picking randomly. 

		
	2) menu method:
		-> It has all the option of that user wants to run the query

	3) getName method:
		-> It takes an int value of student ID. 
		-> Method runs an query into students table and find the student name based on ID. 
		-> Method itself prints the name.

	4) getMajor method:
		-> It takes an int value of student ID. 
		-> Method runs an query into Majors table and find the student major name based on ID. 
		-> Method itself prints the name.

	5) getMinor method:
		-> It takes an int value of student ID. 
		-> Method runs an query into Minor table and find the student minor name based on ID. 
		-> Method itself prints the name.
		
	6) getGPA method:
		-> It takes an int value of student ID. 
		-> This method run an query into HasTaken and also uses JOIN on Classes to obtain the students grades
		-> Method itself return float value GPA of an student
	
	7) getCredit method:
		-> It takes an int value of student ID. 
		-> This method run an query into HasTaken and also uses JOIN on Classes to obtain the students sum(credits) 
		-> Method itself prints the sum of credits of an student.

	8) myprint method:
		-> This prints the format of as per the Question Prompt req:
				Line 1: Student name
				Line 2: Student ID
				Line 3: Student Major
				Line 4: Student Minor
				Line 5: Student GPA
				Line 6: Student Credit
				








