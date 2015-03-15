package models.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

	private static final String Field_NumberOfActivities = "numberOfActivities";
	private static final int NumberOfActivities = 10;
	private static final String Field_NumberOfAdmins = "numberOfActivities";
	private static final int NumberOfAdmins = 2;
	private static final String Field_NumberOfTutors = "numberOfTutors";
	private static final int NumberOfTutors = 10;
	private static final String Field_NumberOfPupils = "numberOfPupils";
	private static final int NumberOfPupils = 200;
	private static final String Field_MinimumAgeInYearsForStaff = "minimumAgeInYearsForStaff";
	private static final int MinimumAgeInYearsForStaff = 25;
	private static final String Field_MaximumAgeInYearsForStaff = "maximumAgeInYearsForStaff";
	private static final int MaximumAgeInYearsForStaff = 55;
	private static final String Field_MinimumAgeInYearsForPupils = "minimumAgeInYearsForPupils";
	private static final int MinimumAgeInYearsForPupils = 15;
	private static final String Field_MaximumAgeInYearsForPupils = "maximumAgeInYearsForPupils";
	private static final int MaximumAgeInYearsForPupils = 18;
	
	private static final int SecondsPerYear = 3600 * 24 * 365;
	private static final int MaxUsernameLength = 5;
	
	@SuppressWarnings("rawtypes")
	private Map parametersMap = new HashMap<String, Integer>();
	private Random randomGenerator = new Random();
	private int lastCombinationUsed = 0;
	
	public DBCreator() {
		createParametersMap();
	}
	
	@SuppressWarnings("unchecked")
	private void createParametersMap() {
		parametersMap.put(Field_NumberOfActivities, NumberOfActivities);
		parametersMap.put(Field_NumberOfAdmins, NumberOfAdmins);
		parametersMap.put(Field_NumberOfTutors, NumberOfTutors);
		parametersMap.put(Field_NumberOfPupils, NumberOfPupils);
		parametersMap.put(Field_MaximumAgeInYearsForStaff, MaximumAgeInYearsForStaff);
		parametersMap.put(Field_MinimumAgeInYearsForStaff, MinimumAgeInYearsForStaff);
		parametersMap.put(Field_MaximumAgeInYearsForPupils, MaximumAgeInYearsForPupils);
		parametersMap.put(Field_MinimumAgeInYearsForPupils, MinimumAgeInYearsForPupils);
	}
	
	@SuppressWarnings("unchecked")
	public void setParameter(String parameterName, int parameterValue) {
		try {
			validateParameters(parameterName, parameterValue);
			parametersMap.put(parameterName, parameterValue);
		}
		catch (Exception e) {
		}
	}
	
	private void validateParameters(String changedParameterName, int changedParameterValue) {
		
	}
	
	public void createUsers(int numberOfUsers, UserType userType) {
		int[] indices = createSequentialArray(lastCombinationUsed, numberOfUsers);
		shuffle(indices);
		for (int i = 0; i < indices.length; i++) {
			System.out.println(indices[i]);
		}
		lastCombinationUsed += numberOfUsers;
	}
	
	private static int[] createSequentialArray(int startIndex, int size) {
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
	
	public Date createBirthDate(int minimumAgeInYears, int maximumAgeInYears) {
		return createBirthDate(DateLib.getDateObject(), minimumAgeInYears, maximumAgeInYears);
	}
	
	public Date createBirthDate(Date currentDate, int minimumAgeInYears, int maximumAgeInYears) {
		int randomAgeInYears = minimumAgeInYears + randomGenerator.nextInt(maximumAgeInYears - minimumAgeInYears);
		Date birthDate = DateLib.copyDateObject(currentDate);
		DateLib.addYears(birthDate, -randomAgeInYears);
		DateLib.addSeconds(birthDate, randomGenerator.nextInt(DBCreator.SecondsPerYear));
		return birthDate;
	}
	
	public static void main(String[] args) {
		new DBCreator().createUsers(100, UserType.Student);
	}
}