package models.util.db;

import java.util.Random;

import models.domain.ActivitySession;
import models.domain.RegistrationStatus;
import models.domain.SessionRegister;
import models.domain.User;

/**
 * PupilBank class
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class SessionRegisterBank {
	
	private static int PupilPresentThreshold = 72;
	private static int PupilAbsentDueToIllnessThreshold = 79;
	private static int PupilAbsentDueToInjuryThreshold = 86;
	private static int PupilAbsentWithPermissionThreshold = 93;
	private static int UpperThreshold = 100;
	
	private static final RegistrationStatus[] RegistrationCodes = new RegistrationStatus[] {RegistrationStatus.Present,
																					  RegistrationStatus.AbsentDueToIllness,
																					  RegistrationStatus.AbsentDueToInjury,
																					  RegistrationStatus.AbsentWithoutPermission,
																					  RegistrationStatus.AbsentWithPermission};

	private static final String[] PresentCodeNotes = new String[] {"Very distracted. Better to have stayed home.",
															 "A poor performance. Mind travelling elsewhere.",
															 "Excellent performance!",
															 "Quite mediocre today. Hope there's more to come."};
	
	private static final String[] AbsentDueToIllnessNotes = new String[] {"IllnessA", "IllnessB", "IllnessC", "IllnessD"};
	
	private static final String[] AbsentDueToInjuryNotes = new String[] {"InjuryA", "InjuryB", "InjuryC", "InjuryD"};
	
	private static final String[] AbsentWithoutPermissionNotes = new String[] {"WithPermissionA", "WithPermissionB", "WithPermissionC", "WithPermissionD"};
	
	private static final String[] AbsentWithPermissionNotes = new String[] {"WithoutPermissionA", "WithoutPermissionB", "WithoutPermissionC", "WithoutPermissionD"};
	
	private static final String[][] RegistrationNotes = new String[][] {PresentCodeNotes, AbsentDueToIllnessNotes, AbsentDueToInjuryNotes,
																  AbsentWithoutPermissionNotes, AbsentWithPermissionNotes};
	
	private static Random randomGenerator = new Random();
	
	public static SessionRegister createSessionRegister(User pupil, ActivitySession session) {
		int statusIndex = selectStatusIndex();
		RegistrationStatus status = RegistrationCodes[statusIndex];
		int notesIndex = randomGenerator.nextInt(RegistrationNotes[statusIndex].length);
		String notes = RegistrationNotes[statusIndex][notesIndex];
		SessionRegister register = new SessionRegister(pupil, session, status, notes);
		pupil.addRegister(register);
		session.addRegister(register);
		return register;
	}
	
	private static int selectStatusIndex() {
		int statusIndex = randomGenerator.nextInt(UpperThreshold);
		if (statusIndex < PupilPresentThreshold) {
			return 0;
		}
		else if (statusIndex < PupilAbsentDueToIllnessThreshold) {
			return 1;
		}
		else if (statusIndex < PupilAbsentDueToInjuryThreshold) {
			return 2;
		}
		else if (statusIndex < PupilAbsentWithPermissionThreshold) {
			return 3;
		}
		else {
			return 4;
		}
	}
}
