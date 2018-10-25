# Course-Registration-System
This program is a course registration system for students and admin to register, withdraw, view, create, or delete courses. 
The program serializes a csv file with the data list of courses students can enroll in. 

Admin methods interface: 
  public void create(); 
  public void delete();
  public void edit(); 
  public void display();
  public void register();
  //report methods 
  public void viewAll(); 
  public void viewFull();
  public void writeFull();
  public void viewStudent();
  public void viewStudentCourse();
  public ArrayList<Course> sortCourse();
Student methods interface: 
  public void viewAll(); 
	public void viewAvailable(); 
	public void register(); 
	public void withdraw(); 
	public void viewmyCourse(); 
