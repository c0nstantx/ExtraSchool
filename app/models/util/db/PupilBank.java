package models.util.db;

import java.util.Date;
import java.util.Random;

import models.util.DateLib;

/**
 * PupilBank class
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class PupilBank {
	
	private static final String[] PupilFirstNames = new String[] {"Andrew", "Billy", "Charles", "Dean", 
		 														  "Ellie", "Frasier", "Glen", "Hector",
		 														  "Iris", "Jackie", "Katherine", "Liam",
		 														  "Moses", "Nick", "Olaf", "Pixie",
		 														  "Quin", "Roger", "Selina", "Trish",
		 														  "Udo", "Vanessa", "William", "Xavi",
		 														  "Yasmin", "Zach"};
	private static final String[] PupilLastNames = new String[] {"Asworth", "Beckett", "Chipman", "Darwin",
																 "Elliott", "Fennman", "Graham", "Healy",
																 "Irwin", "Jackson", "Kellerman", "Laplace",
																 "Maas", "Noakes", "Obermann", "Peters",
																 "Quarta", "Reiman", "Stewart", "Thurman",
																 "Ullman", "Vickers", "Wickley", "Xu",
																 "Yakov", "Ziemermann"};

	private static final int NumberOfPupils = 100;
	private static final Date CurrentDate = DateLib.getDateObject(23, 3, 2015);
	private static final int MinimumAgeInYearsForPupils = 15;
	private static final int MaximumAgeInYearsForPupils = 18;

	private static final int SecondsPerYear = 3600 * 24 * 365;
	private static final int MaxUsernameLength = 5;
	
	private static final Random randomGenerator;
	
	public static final PupilInfo[] pupilData;
	
	static {
		pupilData = new PupilInfo[NumberOfPupils];
		randomGenerator = new Random();
		for (int i = 0; i < NumberOfPupils; i++) {
			int firstNameIndex = (6 * i) % PupilFirstNames.length;
			int lastNameIndex = (6 * i) / PupilLastNames.length;
			pupilData[i] = new PupilInfo(createPupilUsername(PupilFirstNames[firstNameIndex], PupilLastNames[lastNameIndex]),
					createPupilPassword(PupilFirstNames[firstNameIndex], PupilLastNames[lastNameIndex]),
					PupilFirstNames[firstNameIndex], PupilLastNames[lastNameIndex],
					createPupilBirthDate(CurrentDate, MinimumAgeInYearsForPupils, MaximumAgeInYearsForPupils));
		}
	}
	
	private static String createPupilUsername(String firstName, String lastName) {
		return firstName.charAt(0) + lastName.substring(0, Math.min(lastName.length(), MaxUsernameLength - 1));
	}
	
	private static String createPupilPassword(String firstName, String lastName) {
		return createPupilUsername(firstName, lastName) + "123";
	}
	
	private static Date createPupilBirthDate(Date currentDate, int minimumAgeInYears, int maximumAgeInYears) {
		int randomAgeInYears = minimumAgeInYears + randomGenerator.nextInt(maximumAgeInYears - minimumAgeInYears);
		Date birthDate = DateLib.copyDateObject(currentDate);
		DateLib.addYears(birthDate, -randomAgeInYears);
		DateLib.addSeconds(birthDate, randomGenerator.nextInt(SecondsPerYear));
		return birthDate;
	}
}
