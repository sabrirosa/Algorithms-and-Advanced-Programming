
package addstaffrecord;
import java.io.File;
import java.util.*;

public class AddStaffRecord extends Exception {

	public static void main(String[] args) throws Exception{
		//parsing and reading the CSV file data into the film (object) array
		// provide the path here...
        File directory = new File("./");
  		String name = directory.getAbsolutePath() + "//Staff.csv";
		Scanner sc = new Scanner(new File(name));
		Staff[] staffs = new Staff[10000];

		// this will just print the header in CSV file
		sc.nextLine();

		int i = 0; String st = "";
                int counter=0;
		while (sc.hasNextLine())  //returns a boolean value
		{
                    counter++;
			st = sc.nextLine();
			String[] data = st.split(",");
			staffs[i++] = new Staff(Integer.parseInt(data[0]), data[1], data[2], data[3], Float.parseFloat(data[4]), Float.parseFloat(data[5]));
		}
		sc.close();  //closes the scanner

		// We can print film details due to overridden toString method in film class
		System.out.println(staffs[0]);
		System.out.println(staffs[1]);

		// we can compare films based on their ID due to overridden CompareTo method in film class
		System.out.println(staffs[0]==staffs[0]);
		System.out.println(staffs[0]==staffs[1]);
                
                
                //This part has been implemented by me
                //System.out.println(counter);
                
//part 2 Question 2                
                float wage=0;
                 float projectCompletionRate=0;
                Scanner input=new Scanner(System.in);
           
                System.out.println("Enter First_Name");
                String firstName=input.next();
                if(firstName.isEmpty())
                {
                     throw new InvalidInputException("FirstName cannot be empty! Correct This");
                }
                else if(firstName.matches("\\d+"))
                {
                     throw new InvalidInputException("FirstName cannot contain Digits Only! Correct this.");
                }
                
                System.out.println("Enter Second Name");
                 String secondName=input.next();
                  if(secondName.isEmpty())
                {
                     throw new InvalidInputException("SecondName cannot be empty! Correct This");
                }
                else if(secondName.matches("\\d+"))
                {
                     throw new InvalidInputException("SecondName cannot contain Digits Only! Correct this.");
                }
                  
                  System.out.println("Enter Department");
                 String department=input.next();
                  if(department.isEmpty())
                {
                     throw new InvalidInputException("Department cannot be empty! Correct This");
                }
                else if(department.matches("\\d+"))
                {
                     throw new InvalidInputException("Department cannot contain Digits Only! Correct this.");
                }
                  
                 System.out.println("Enter Wage");
                 String wageInput=input.next();
                 try {
                wage = Float.parseFloat(wageInput);
                if (wage <= 0) {
            throw new NumberFormatException();
                  }
               } catch (NumberFormatException e) {
            throw new InvalidInputException("The Wage must be a valid number greater than 0!Correct this.");
                                                  } 
                 
                 System.out.println("Enter ProjectCompletionRate");
                 String projectCompletionRateInput =input.next();
                 try {
                projectCompletionRate = Float.parseFloat(projectCompletionRateInput);
                if (wage <= 0) {
            throw new NumberFormatException();
                  }
               } catch (NumberFormatException e) {
            throw new InvalidInputException("The ProjectCompletionRate must be a valid number greater than 0!Correct this.");
                                                  } 
              
                 //Call method for part 2 Question 1
                 counter+=1;
               addNewRecord(staffs, counter,firstName,secondName,department,wage,projectCompletionRate);
               
               
               //Call method for part 3 Algorithm Question 
      System.out.println("---Let us search for members of staff using a budget of 100,000 and search for ones with a high completion Rate--- ");
                selectStaffWithinBudget( staffs, 100000.00f);
    
                         
	}
        
//Part 2 Question 1
    public static void addNewRecord(Staff[] staffs, int empNo, String fName, String sName, String department, float wage, float projectCompletionRate)
{
    // Create the new temporary array with enough space for the new staff object
    Staff[] newTempStaff = new Staff[empNo + 1];
    // Copy the elements of the original array to the new array
    System.arraycopy(staffs, 0, newTempStaff, 0, empNo-1);
    // Create the new staff object and add it to the end of the array
    Staff newStaff = new Staff(empNo, fName, sName, department, wage, projectCompletionRate);
    newTempStaff[empNo-1] = newStaff;
    //we now print our last element that has been added to the new record Array
    System.out.println(newTempStaff[empNo-1]);
}
    
//Part 3 : Algorithm Question
    public static ArrayList<Staff> selectStaffWithinBudget(Staff[] staffs, float budget) {
    // Sort the Staff array based on projectCompletionRate in descending order
    mergeSort_Algorithm(staffs, 0, staffs.length - 1);

    // Initialize variables for tracking total cost and selected staff members
    float totalCost = 0;
    ArrayList<Staff> selectedStaff = new ArrayList<>();

    // Iterate through the sorted Staff array and select staff members
    for (int i = 0; i < staffs.length; i++) {
        Staff staff = staffs[i];
        // If the projectCompletionRate is less than or equal to 70, break out of the loop
        if (staff.getProjectCompletionRate() <= 70) {
            break;
        }
        // If the total cost exceeds the budget, break out of the loop
        if (totalCost + staff.getWage() > budget) {
            break;
        }
        // Add the staff member to the selected staff list and update the total cost
        selectedStaff.add(staff);
        totalCost += staff.getWage();
    }
    for(Staff staff : selectedStaff)
    {
         System.out.println(staff);
    }
      
    return selectedStaff;
}

// Merge sort implementation for sorting the Staff array based on projectCompletionRate in descending order
public static void mergeSort_Algorithm(Staff[] staffs, int left, int right) {
    if (left < right) {
        int middle = (left + right) / 2;
        mergeSort_Algorithm(staffs, left, middle);
        mergeSort_Algorithm(staffs, middle + 1, right);
        merge(staffs, left, middle, right);
    }
}

public static void merge(Staff[] staffs, int left, int middle, int right) {
    Staff[] temp = new Staff[right - left + 1];
    int i = left;
    int j = middle + 1;
    int k = 0;
    while (i <= middle && j <= right) {
        if (staffs[i].getProjectCompletionRate() >= staffs[j].getProjectCompletionRate()) {
            temp[k] = staffs[i];
            i++;
        } else {
            temp[k] = staffs[j];
            j++;
        }
        k++;
    }
    while (i <= middle) {
        temp[k] = staffs[i];
        i++;
        k++;
    }
    while (j <= right) {
        temp[k] = staffs[j];
        j++;
        k++;
    }
    for (k = 0; k < temp.length; k++) {
        staffs[left + k] = temp[k];
    }
}


}

 // use a class to handle any Exceptions from user Inputs
class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}



class Staff implements Comparable<Object>{

	private int empNo;
	private String fName;
	private String sName;
	private String department;
	private float wage;
	private float projectCompletionRate;


	// constructor
	public Staff(int empNo, String fName, String sName, String department, float wage, float projectCompletionRate) {
		super();
		this.empNo = empNo;
		this.fName= fName;
		this.sName= sName;
		this.department= department;
		this.wage = wage;
		this.projectCompletionRate = projectCompletionRate;
	}

	// setters and getters
	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public String getFName() {
		return fName;
	}

	public void setFName(String fName) {
		this.fName = fName;
	}

	public String getSName() {
		return sName;
	}

	public void setSName(String sName) {
		this.sName = sName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public float getProjectCompletionRate() {
		return projectCompletionRate;
	}

	public void setProjectCompletionRate(float projectCompletionRate) {
		this.projectCompletionRate = projectCompletionRate;
	}

	public float getWage(){
		return wage;
	}

	public void setWage(float wage){
		this.wage = wage;
	}

		// so the film objects can be compared when sorting/searching
	// NOTE: this will only allow comparisons based on the title of the film
	@Override
	public int compareTo(Object obj) {

		/*
		Edit this section so it compares the appropriate
		column you wish to sort by
		*/

		Staff sff = (Staff)obj;
		return empNo - (sff.getEmpNo());
	}

	@Override
	public String toString() {
		return "Staff [EmpNo=" + empNo + ", first name=" + fName+ ", last Name=" + sName+ ", department="
				+ department+  ", wage=" + wage+ ", project completion rate="
				+ projectCompletionRate+ "]";
	}



}