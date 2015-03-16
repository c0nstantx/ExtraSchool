package models.util;

import java.util.Date;
import java.util.Random;

import models.domain.UserType;

/**
 * DBCreator Class
 * Populates the database with data
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class DBCreator {
	
	private static final String[] FirstNames = new String[] {"Andrew", "Billy", "Charles", "Dean", 
															 "Ellie", "Frasier", "Glen", "Hector",
															 "Iris", "Jackie", "Katherine", "Liam",
															 "Moses", "Nick", "Olaf", "Pixie",
															 "Quin", "Roger", "Selina", "Trish",
															 "Udo", "Vanessa", "William", "Xavi",
															 "Yasmin", "Zach"};
	private static final String[] LastNames = new String[] {"Asworth", "Beckett", "Chipman", "Darwin",
															"Elliott", "Fennman", "Graham", "Healy",
															"Irwin", "Jackson", "Kellerman", "Laplace",
															"Maas", "Noakes", "Obermann", "Peters",
															"Quarta", "Reiman", "Stewart", "Thurman",
															"Ullman", "Vickers", "Wickley", "Xu",
															"Yakov", "Ziemermann"};
	
	private static final int MaximumUniqueNames = FirstNames.length * LastNames.length;

	private static final int NumberOfActivities = 30;
	private static final int NumberOfAdmins = 2;
	private static final int NumberOfTutors = 30;
	private static final int NumberOfPupils = 400;
	private static final int MinimumAgeInYearsForStaff = 25;
	private static final int MaximumAgeInYearsForStaff = 55;
	private static final int MinimumAgeInYearsForPupils = 15;
	private static final int MaximumAgeInYearsForPupils = 18;
	
	private static final int SecondsPerYear = 3600 * 24 * 365;
	private static final int MaxUsernameLength = 5;
	
	private Random randomGenerator = new Random();
	private int lastCombinationUsed = 0;
	
	public DBCreator() { }
	
	public void createStandardData() {
		
	}
	
	public void createRandomData() {
		
	}
	
	private void createStandardAdmins() {
		
	}
	
	private void createRandomAdmins() {
		
	}
	
	private void createStandardTutors() {
		
	}
	
	private void createRandomTutors() {
		
	}
	
	private void createStandardPupils() {
		
	}
	
	private void createRandomPupils() {
		
	}
	
	private void createStandardUsers(int numberOfUsers, UserType userType) {
		int[] indices = createSequentialArray(lastCombinationUsed, numberOfUsers);
		createUsers(indices, userType);
	}
	
	private void createRandomUsers(int numberOfUsers, UserType userType) {
		int[] indices = createSequentialArray(lastCombinationUsed, numberOfUsers);
		shuffle(indices);
		createUsers(indices, userType);
	}
	
	private void createUsers(int[] indices, UserType userType) {
		for (int i = 0; i < indices.length; i++) {
			System.out.println(indices[i]);
		}
		lastCombinationUsed += indices.length;
	}
	
	private int[] createSequentialArray(int startIndex, int size) {
		int[] seqArray = new int[size];
		for (int i = 0; i < size; i++) {
			seqArray[i] = startIndex + i;
		}
		return seqArray;
	}
	
	private void shuffle(int[] array) {
		for (int i = 0; i < array.length * 10; i++) {
			int posA = randomGenerator.nextInt(array.length);
			int posB = randomGenerator.nextInt(array.length);
			int temp = array[posA];
			array[posA] = array[posB];
			array[posB] = temp;
		}
	}
	
	private String createUsername(String firstName, String lastName) {
		return firstName.charAt(0) + lastName.substring(0, Math.min(lastName.length(), MaxUsernameLength - 1));
	}
	
	private String createPassword(String firstName, String lastName) {
		return createUsername(firstName, lastName) + "123";
	}
	
	private Date createBirthDate(int minimumAgeInYears, int maximumAgeInYears) {
		return createBirthDate(DateLib.getDateObject(), minimumAgeInYears, maximumAgeInYears);
	}
	
	private Date createBirthDate(Date currentDate, int minimumAgeInYears, int maximumAgeInYears) {
		int randomAgeInYears = minimumAgeInYears + randomGenerator.nextInt(maximumAgeInYears - minimumAgeInYears);
		Date birthDate = DateLib.copyDateObject(currentDate);
		DateLib.addYears(birthDate, -randomAgeInYears);
		DateLib.addSeconds(birthDate, randomGenerator.nextInt(DBCreator.SecondsPerYear));
		return birthDate;
	}
	
	public static void main(String[] args) {
		
	}
}