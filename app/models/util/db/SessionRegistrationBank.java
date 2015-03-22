package models.util.db;

import java.util.Random;

import models.domain.RegistrationStatus;

/**
 * PupilBank class
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class SessionRegistrationBank {
	
	private static RegistrationStatus[] RegistrationCodes = new RegistrationStatus[] {RegistrationStatus.Present,
																					  RegistrationStatus.AbsentDueToIllness,
																					  RegistrationStatus.AbsentDueToInjury,
																					  RegistrationStatus.AbsentWithoutPermission,
																					  RegistrationStatus.AbsentWithPermission};

	private static String[] PresentCodeNotes = new String[] {"Very distracted. Better to have stayed home.",
															 "A poor performance. Mind travelling elsewhere.",
															 "Excellent performance!",
															 "Quite mediocre today. Hope there's more to come."};
	
	private static String[] AbsentDueToIllnessNotes = new String[] {""};
	
	private static String[] AbsentDueToInjuryNotes = new String[] {""};
	
	private static String[] AbsentWithoutPermissionNotes = new String[] {""};
	
	private static String[] AbsentWithPermissionNotes = new String[] {""};
	
	private static String[][] RegistrationNotes = new String[][] {PresentCodeNotes, AbsentDueToIllnessNotes, AbsentDueToInjuryNotes,
																  AbsentWithoutPermissionNotes, AbsentWithPermissionNotes};
	
	private static final Random randomGenerator;
	
	static {
		randomGenerator = new Random();
	}
	
	/* biased selection: Present: 72%^, all the rest 7% each */
	private static RegistrationStatus selectRegistrationStatus() {
		return RegistrationStatus.Present;
	}
}
