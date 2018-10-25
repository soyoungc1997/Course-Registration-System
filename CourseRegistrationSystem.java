
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner; 

public class CourseRegistrationSystem {
	public static void main(String[] args) {
		ArrayList<Course> courseList = new ArrayList<Course>(); 
		if (new File("Courses.ser").exists()) {
			try {
				FileInputStream fis = new FileInputStream("Courses.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);
				courseList = (ArrayList<Course>)ois.readObject();
				ois.close();
				fis.close();
			}
			catch (IOException ioe) {
				ioe.printStackTrace(); 
			}
			catch(ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
		}
		else {
			String fileName = "MyUniversityCourses.csv";
			ArrayList<String[]> allCourses = new ArrayList<String[]>(); 
			
			//make an ArrayList here for students as well 
			ArrayList<Student> studentList = new ArrayList<Student>(); 
			try {
				//Reading the course file into arrays 
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line = null; 
				while ((line = bufferedReader.readLine()) != null) {
					String[] splitLine = line.split(",");
					allCourses.add(splitLine); 
				}
				//Serializing to file courses.ser
				FileOutputStream fos = new FileOutputStream("Courses.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos); 
				
				//Write each object into the oos (serializing the arraylist with objects)
				for (int i = 1; i < allCourses.size(); i++) {
					String courseName = allCourses.get(i)[0]; 
					String courseId = allCourses.get(i)[1];
					int maxNum = Integer.parseInt(allCourses.get(i)[2]);
					int currentNum = Integer.parseInt(allCourses.get(i)[3]);
					ArrayList<Student> studentNames= new ArrayList<Student>();
					String instructor = allCourses.get(i)[5]; 
					String section = allCourses.get(i)[6]; 
					String location = allCourses.get(i)[7];
					
					Course newCourse = new Course(courseName, courseId, maxNum, currentNum, studentNames, instructor, section, location); 
					courseList.add(newCourse); 
				}
				oos.writeObject(courseList);
				oos.close(); 
				fos.close(); 
				bufferedReader.close(); 
			}
			catch(FileNotFoundException ex) {
				System.out.println("Unable to open file '" + fileName + "'");
				ex.printStackTrace();
			}
			catch(IOException ex) {
				System.out.println("Error reading file '" + fileName + "'");
				ex.printStackTrace();
			}
		}		
		//User sign in: ask if admin or student
		Scanner input = new Scanner(System.in);
		while (true) {
			System.out.println("Welcome to the NYU Course Registration System.");
			System.out.println("Are you an admin or a student? ");
			String answer = input.next(); 
			boolean signedin = false;
			//if it is an admin, accept username and password - put in while loop 
			if (answer.equalsIgnoreCase("Admin")) {
				while (true) {
					//enter user name and password 
					System.out.println("Enter your username and password:"); 
					String usernameAdmin = input.next(); 
					String passwordAdmin = input.next(); 
					//if username and password = admin, break out of both while loops
					if (usernameAdmin.equals("Admin") && passwordAdmin.equals("Admin001") ) {
						signedin = true;
						Admin admin = new Admin(usernameAdmin, passwordAdmin, "So-Young", "Choi");
						System.out.println("Welcome, Admin. You are signed in.");
						// you now need to show them the menu 
						while (true) {
							System.out.println("Please pick an option (1 or 2): "); 
							System.out.println("1) Course Management"); 
							System.out.println("2) Reports");
							String option = input.next(); 
							
							//if they picked option 1, 
							if (option.equals("1")) {
								while (true) {
									System.out.println("Please enter the number of the option you want: ");
									System.out.println("1. Create a new course");
									System.out.println("2. Delete a course");
									System.out.println("3. Edit a course");
									System.out.println("4. Display information for a given course");
									System.out.println("5. Register a student");
									System.out.println("6. Exit"); 
									String cmoption = input.next(); 
									if (cmoption.equals("1")) {
										admin.create();
									}
									else if (cmoption.equals("2")) {
										admin.delete();
									}
									else if (cmoption.equals("3")) {
										admin.edit();
									}
									else if (cmoption.equals("4")) {
										admin.display();
									}
									else if (cmoption.equals("5")) {
										admin.register();
									}
									else if (cmoption.equals("6")) {
										System.out.println("Thank you for using NYU Course Registration System.");
										admin.exit(); 
									}
									else {
										System.out.println("That is an invalid option. Please enter another number.");
									}
									while (true) {
										System.out.println("Would you like to conduct more actions?");
										String action = input.next(); 
										if (action.equals("no")) {
											admin.exit();
										}
										else if (action.equals("yes")) {
											break;
										}
										else {
											System.out.println("Please enter a valid input (yes/no)");
											continue;
										}
									}
								}
							}
							//if they picked option 2, 
							else if (option.equals("2")) {
								while (true) {
									System.out.println("Please enter the number of the option you want: ");
									System.out.println("1. View all courses");
									System.out.println("2. View all courses that are full");
									System.out.println("3. Write to a file the list of courses that are full");
									System.out.println("4. View the names of the students that are registered in a specific course");
									System.out.println("5. View the list of courses that a specific student is registered in"); 
									System.out.println("6. Sort the courses based on the current number of students registered");
									System.out.println("7. Exit");
									String rOption = input.next(); 
									if (rOption.equals("1")) {
										admin.viewAll();
									}
									else if (rOption.equals("2")) {
										admin.viewFull(); 
									}
									else if (rOption.equals("3")) {
										admin.writeFull();
									}
									else if (rOption.equals("4")) {
										admin.viewStudent();
									}
									else if (rOption.equals("5")) {
										admin.viewStudentCourse();
									}
									else if (rOption.equals("6")) {
										ArrayList<Course> list = admin.sortCourse();
										System.out.println("Here is a sorted list of courses: ");
										for (Course x: list) {
											x.printCourse();
										}
									}
									else if (rOption.equals("7")) {
										System.out.println("Thank you for using NYU Course Registration System. ");
										admin.exit(); 
									}
									else {
										System.out.println("That is an invalid option. Please enter another number.");
									}
									boolean keeplooping = true; 
									while (true) {
										System.out.println("Would you like to conduct more actions?");
										String action = input.next(); 
										if (action.equals("no")) {
											keeplooping = false;
											break;
										}
										else if (action.equals("yes")) {
											break;
										}
										else {
											System.out.println("Please enter a valid input (yes/no)");
											continue;
										}
									}
									if (keeplooping == false) {
										break;
									}
								}
							}
							else {
								System.out.println("Please enter a valid input (1 or 2)");
								continue;
							}
						}
					}
					System.out.println("Would you like to try again? (y/n)");
					String yes = input.next();
					if (yes.equalsIgnoreCase("n")) {
						break;
					}
					//If signed in break out of while loop
					if (signedin == true) {
						break;
					}
				}
			}
	
			
			//if student 
			if (answer.equalsIgnoreCase("Student")) {
				ArrayList<Student> studentList = new ArrayList<Student>();
				
				System.out.println("Are you a returning student?");
				String reply = input.next(); 
				//Find out if he/she wants to register (new or old)
				//if reply is register (need to create a new student object) 
				if (reply.equalsIgnoreCase("No")) {
					
					String newUsername = null; 
					if (new File("Student.ser").exists()) {
						while (true) {
							System.out.println("Please pick a username: "); 
							newUsername = input.next(); 
							studentList = null;
							//Need to validate if the username exists
							try {
								
								FileInputStream fis = new FileInputStream("Student.ser");
								ObjectInputStream ois = new ObjectInputStream(fis);
								studentList = (ArrayList<Student>)ois.readObject();
								ois.close();
								fis.close();
							}
							catch (FileNotFoundException fnfe) {
								System.out.println("There are no students registered.");
							}
							catch (IOException ex) {
								ex.printStackTrace();
							}
							catch(ClassNotFoundException cnfe) {
								cnfe.printStackTrace();
							}
							boolean notExist = true; 
							for (Student x: studentList) {
								if (x.getUsername().equals(newUsername)) {
									notExist = false;
									System.out.println("This username already exists. Please pick a different username.");
								}
							}
							if (notExist) {
								break;
							}
						}
					}
					else {
						System.out.println("Please pick a username: ");
						newUsername = input.next();
					}
					System.out.println("Please pick a password: "); 
					String newPassword = input.next(); 
					System.out.println("Please enter your firstname: "); 
					String firstname = input.next(); 
					System.out.println("Please enter your lastname: "); 
					String lastname = input.next(); 
					ArrayList<Course> myCourse = new ArrayList<Course>(); 
					
					//Create student object 
					Student newStudent = new Student(newUsername, newPassword, firstname, lastname, myCourse); 
					studentList.add(newStudent); 
					
					//Make the arrayList serializable 
					try {
						FileOutputStream fosStudent = new FileOutputStream("Student.ser");
						ObjectOutputStream oosStudent = new ObjectOutputStream(fosStudent);
						oosStudent.writeObject(studentList);
						oosStudent.close();
						fosStudent.close();
					}
					catch(IOException ex){
						ex.printStackTrace();
					}
					System.out.println("You are registered. Welcome "+newStudent.getFirstname()+ ", "+ newStudent.getLastname());
					System.out.println("Please sign in again. ");
					continue;
				}
				//if it is an old student trying to sign in
				else if (reply.equalsIgnoreCase("yes")) {
					Student myStudent = null;
					boolean stop = false;
					while (true) {
						System.out.println("Please enter your user name: "); 
						//get input
						String studentUsername = input.next(); 
						//need to see if username exists in the student arraylist 
						//deserialize arraylist 
						studentList = null;
						if (new File("Student.ser").exists()) {
							try {
								FileInputStream fis = new FileInputStream("Student.ser");
								ObjectInputStream ois = new ObjectInputStream(fis);
								studentList = (ArrayList<Student>)ois.readObject(); 
								
								ois.close(); 
								fis.close(); 
							}
							catch (FileNotFoundException fnfe) {
								System.out.println("There are no students registered.");
							}
							catch (IOException ioe) {
								ioe.printStackTrace();
							}
							catch(ClassNotFoundException cnfe) {
								cnfe.printStackTrace();
							}
							//Check if username exists in studentList
							boolean yes = true; 
							
							for (Student x: studentList) {
								if (x.getUsername().equals(studentUsername)) {
									while (true) {
										System.out.println("Please enter your password: ");
										String studentPassword = input.next(); 
										if (x.getPassword().equals(studentPassword)) {
											System.out.println("Welcome "+x.getFirstname() + ", " +x.getLastname()+"."); 
											yes = false; 
											while (true) {
												System.out.println("Please enter the number of the option that you want: ");
												System.out.println("1. View all courses. ");
												System.out.println("2. View all courses that are not full.");
												System.out.println("3. Register in a course.");
												System.out.println("4. Withdraw from a course.");
												System.out.println("5. View all courses that the student is registered in.");
												System.out.println("6. Exit"); 
												String studentOption = input.next(); 
												if (studentOption.equals("1")) {
													x.viewAll();
												}
												else if (studentOption.equals("2")) {
													x.viewAvailable();
												}
												else if (studentOption.equals("3")) {
													x.register();
												}
												else if (studentOption.equals("4")) {
													x.withdraw();
												}
												else if (studentOption.equals("5")) {
													x.viewmyCourse();
												}
												else if (studentOption.equals("6")) {
													x.exit(); 
												}
												else {
													System.out.println("That is not a valid input. Please enter a different number.");
													continue; 
												}
												
												System.out.println("Would you like to conduct more actions? (yes/no)");
												String action = input.next(); 
												if (action.equalsIgnoreCase("no")) {
													stop = true;
													break;
												}
												else if (action.equalsIgnoreCase("yes")) {
													continue;
												}
											}
											try {
												FileOutputStream studentsfos= new FileOutputStream("Student.ser");
												ObjectOutputStream studentsoos = new ObjectOutputStream(studentsfos);
												studentsoos.writeObject(studentList);
												studentsfos.close();
												studentsoos.close();
											}
											catch (FileNotFoundException fnfe) {
												fnfe.printStackTrace();
											}
											catch(IOException ioe) {
												ioe.printStackTrace();
											}
										}
										
										else {
											System.out.println("That is an incorrect password. Would you like to try again?");
											String tryOnce = input.next(); 
											if (tryOnce.equalsIgnoreCase("no")) {
												break; 
											}
											continue;
										}
										break;
									}
									
								}
								if (yes) {
									System.out.println("Sorry, your username does not exist. Would you like to try again?");
									String tryAgain = input.next(); 
									if (tryAgain.equals("no")) {
										break;
									}
									if (tryAgain.equals("yes")) {
										continue;
									}
								}
								break;
							}
						}
						else {
							System.out.println("There are no students registered yet. Please register first.");
							break;
						}
						if (stop == true) {
							break;
						}
					}
					
		
				}
				//If it is neither sign in/register? 
				else {
					System.out.println("Please enter a valid input (yes/no)");
					continue;
				}
			}
			//if neither student nor admin? 
			else {
				System.out.println("Please enter a valid input (student/admin)"); 
				continue;
			}
		}

		//close the input in the end 
	}
}


