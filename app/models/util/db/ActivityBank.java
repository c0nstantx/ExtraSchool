package models.util.db;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import models.util.DateLib;
import models.util.DateSet;

/**
 * ActivityBank class
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class ActivityBank {
	private static final int NumberOfActivities = 11;
	
	private static final String ActivityNames[] = new String[] {"Athletics", "Basketball", "Drama Club", "Football",
																"Gymnastics", "History Club", "ICT Club", "Swimming",
																"Martial Arts", "Trampoline", "Volleyball"};
	
	private static final String AthleticsDescription = "Athletics is an exclusive collection of sporting events that involve competitive running, jumping, "
			+ "throwing, and walking. The most common types of athletics competitions are track and field, road running, cross country running, "
			+ "and race walking.";
	
	private static final String BasketballDescription = "Basketball is a sport played by two teams of five players on a rectangular court. The objective is "
			+ "to shoot a ball through a hoop 18 inches (46 cm) in diameter and 10 feet (3.048 m) high mounted to a backboard at each end.";
	
	private static final String DramaClubDescription = "Drama is the specific mode of fiction represented in performance.The term comes from a Greek word meaning action, "
			+ "which is derived from the verb meaning to do or to act.";
	
	private static final String FootballDescription = "Association football, more commonly known as football or soccer, is a sport played between two teams of eleven "
			+ "players with a spherical ball. It is played by 250 million players in over 200 countries, making it the world's most popular sport.";
	
	private static final String GymnasticsDescription = "Gymnastics is a sport involving the performance of exercises requiring physical strength, flexibility, "
			+ "power, agility, coordination, grace, balance and control. Internationally, all of the competitive gymnastics events are governed "
			+ "by the FIG.";
	
	private static final String HistoryClubDescription = "History is the study of the past, particularly how it relates to humans.It is an umbrella term that relates to "
			+ "past events as well as the memory, discovery, collection, organization, presentation, and interpretation of information about these "
			+ "events.";
	
	private static final String IctClubDescription = "Information and communications technology (ICT) is often used as an extended synonym for information technology (IT), "
			+ "but is a more specific term that stresses the role of unified communication and the integration of telecommunications and computers.";
	
	private static final String SwimmingDescription = "The recreational activity of swimming has been recorded since prehistoric times. The earliest recording of "
			+ "swimming dates back to Stone Age paintings from around 10000 years ago. Written references date from 2000 BC.";
	
	private static final String MartialArtsDescription = "Martial arts are codified systems and traditions of combat practices, which are practiced for a variety "
			+ "of reasons: self-defense, competition, physical health and fitness, entertainment, as well as mental, physical, and spiritual development.";
	
	private static final String TrampolineDescription = "A trampoline is a device consisting of a piece of taut, strong fabric stretched over a steel frame using many coiled "
			+ "springs. People bounce on trampolines for recreational and competitive purposes.";
	
	private static final String VolleyballDescription = "Volleyball is a team sport in which two teams of six players are separated by a net. Each team tries to "
			+ "score points by grounding a ball on the other team's court under organized rules.";
	
	private static final String ActivityDescriptions[] = new String[] {AthleticsDescription, BasketballDescription, DramaClubDescription, FootballDescription,
																	   GymnasticsDescription, HistoryClubDescription, IctClubDescription, SwimmingDescription,
																	   MartialArtsDescription, TrampolineDescription, VolleyballDescription};
	
	private static final DateSet[] AthleticsDateSet = new DateSet[] {new DateSet(DateLib.getDateObject(13, 1, 2015, 16, 0, 0),
																				 DateLib.getDateObject(26, 5, 2015, 16, 0, 0),
																				 new boolean[] {false, false, true, false, false, false, false}),
																	 new DateSet(DateLib.getDateObject(17, 1, 2015, 10, 0, 0),
																				 DateLib.getDateObject(30, 5, 2015, 10, 0, 0),
																				 new boolean[] {false, false, false, false, false, false, true})};
	
	private static final DateSet[] BasketballDateSet = new DateSet[] {new DateSet(DateLib.getDateObject(12, 1, 2015, 18, 0, 0),
																			 DateLib.getDateObject(27, 5, 2015, 18, 0, 0),
																			 new boolean[] {false, true, false, true, false, false, false}),
																	  new DateSet(DateLib.getDateObject(16, 1, 2015, 17, 0, 0),
																			 DateLib.getDateObject(29, 5, 2015, 17, 0, 0),
																			 new boolean[] {false, false, false, false, false, true, false})};
	
	private static final DateSet[] DramaClubDateSet = new DateSet[] {new DateSet(DateLib.getDateObject(12, 1, 2015, 17, 0, 0),
																			 DateLib.getDateObject(27, 5, 2015, 17, 0, 0),
																			 new boolean[] {false, true, false, true, false, false, false})};
	
	private static final DateSet[] FootballDateSet = new DateSet[] {new DateSet(DateLib.getDateObject(12, 1, 2015, 16, 0, 0),
																				DateLib.getDateObject(27, 5, 2015, 16, 0, 0),
																				new boolean[] {false, true, false, true, false, false, false}),
																	new DateSet(DateLib.getDateObject(17, 1, 2015, 10, 0, 0),
																				DateLib.getDateObject(30, 5, 2015, 10, 0, 0),
																				new boolean[] {false, false, false, false, false, false, true})};
	
	private static final DateSet[] GymnasticsDateSet = new DateSet[] {new DateSet(DateLib.getDateObject(13, 1, 2015, 17, 0, 0),
																				  DateLib.getDateObject(28, 5, 2015, 17, 0, 0),
																				  new boolean[] {false, false, true, false, true, false, false}),
																	  new DateSet(DateLib.getDateObject(17, 1, 2015, 10, 0, 0),
																			  	  DateLib.getDateObject(30, 5, 2015, 10, 0, 0),
																			  	  new boolean[] {false, false, false, false, false, false, true})};
	
	private static final DateSet[] HistoryClubDateSet = new DateSet[] {new DateSet(DateLib.getDateObject(15, 1, 2015, 16, 0, 0),
																			   DateLib.getDateObject(29, 5, 2015, 16, 0, 0),
																			   new boolean[] {false, false, false, false, true, true, false})};
	
	private static final DateSet[] IctClubDateSet = new DateSet[] {new DateSet(DateLib.getDateObject(12, 1, 2015, 16, 0, 0),
																		   DateLib.getDateObject(26, 5, 2015, 16, 0, 0),
																		   new boolean[] {false, true, true, false, false, false, false})};
	
	private static final DateSet[] SwimmingDateSet = new DateSet[] {new DateSet(DateLib.getDateObject(12, 1, 2015, 16, 0, 0),
																				DateLib.getDateObject(29, 5, 2015, 16, 0, 0),
																				new boolean[] {false, true, false, true, false, true, false})};
	
	private static final DateSet[] MartialArtsDateSet = new DateSet[] {new DateSet(DateLib.getDateObject(17, 1, 2015, 11, 0, 0),
																				   DateLib.getDateObject(30, 5, 2015, 11, 0, 0),
																				   new boolean[] {false, false, false, false, false, false, true})};
	
	private static final DateSet[] TrampolineDateSet = new DateSet[] {new DateSet(DateLib.getDateObject(17, 1, 2015, 12, 0, 0),
																				  DateLib.getDateObject(30, 5, 2015, 12, 0, 0),
																				  new boolean[] {false, false, false, false, false, false, true})};
	
	private static final DateSet[] VolleyballDateSet = new DateSet[] {new DateSet(DateLib.getDateObject(12, 1, 2015, 17, 0, 0),
																				  DateLib.getDateObject(27, 5, 2015, 17, 0, 0),
																				  new boolean[] {false, true, false, true, false, false, false})};
	
	private static final DateSet[][] ActivityDateSets = new DateSet[][] {AthleticsDateSet, BasketballDateSet, DramaClubDateSet, FootballDateSet,
																		 GymnasticsDateSet, HistoryClubDateSet, IctClubDateSet, SwimmingDateSet,
																		 MartialArtsDateSet, TrampolineDateSet, VolleyballDateSet};
	
	private static final String[] ActivityVenues = new String[] {"Outside fields", "Basketball court", "Room A5", "Football picth",
																 "Inside hall", "Room B1", "ICT lab 1", "Swimming pool",
																 "Inside hall", "Inside hall", "Basketball court"};
	
	private static final String[] TutorFullNames = new String[] {"Kate Voisey", "Chris Latanis", "Julie Gunn", "Nick Moustakas",
																 "Giota Ziara", "Elizabeth Stewart", "Tim Roberts", "Laura Pearson",
																 "Richard Watts", "Philip Kolyvas", "Test Tutor"};
	
	private static final Date[] TutorBirthDates = new Date[] {DateLib.getDateObject(11, 8, 1985), DateLib.getDateObject(21, 1, 1987),
															  DateLib.getDateObject(3, 12, 1968), DateLib.getDateObject(13, 11, 1980),
															  DateLib.getDateObject(19, 4, 1985), DateLib.getDateObject(30, 4, 1957),
															  DateLib.getDateObject(12, 1, 1966), DateLib.getDateObject(27, 6, 1991),
															  DateLib.getDateObject(2, 2, 1981), DateLib.getDateObject(2, 10, 1967),
															  DateLib.getDateObject(30, 20, 1987)};
	
	private static final int[] AthleticsPupils = new int[] {0, 6, 17, 24, 40, 56, 60, 62, 74, 80, 88, 94};
	private static final int[] BasketballPupils = new int[] {6, 10, 11, 14, 26, 27, 28, 40, 41, 42, 56, 63, 65, 66, 79, 80, 81, 83, 84, 90, 91, 96, 99};
	private static final int[] DramaClubPupils = new int[] {2, 5, 13, 44, 45, 47, 68, 69};
	private static final int[] FootballPupils = new int[] {1, 4, 8, 9, 18, 19, 20, 31, 32, 33, 34, 35, 46, 49, 51, 52, 57, 59, 63, 70, 71, 72, 79, 81, 85, 86, 87, 89, 92, 93, 98};
	private static final int[] GymnasticsPupils = new int[] {3, 7, 12, 37, 54, 76};
	private static final int[] HistoryPupils = new int[] {22, 25, 30, 39, 77};
	private static final int[] IctClubPupils = new int[] {11, 12, 26, 30, 42, 54, 65, 66, 69, 76, 84, 90, 96};
	private static final int[] SwimmingPupils = new int[] {6, 10, 14, 27, 37, 44, 45, 68, 74, 80, 82, 83, 91, 94};
	private static final int[] MartialArtsPupils = new int[] {6, 12, 18, 59, 60, 88, 91};
	private static final int[] TrampolinePupils = new int[] {28, 35, 41, 47, 49, 62, 82, 85, 96};
	private static final int[] VolleyballPupils = new int[] {4, 14, 17, 18, 26, 28, 31, 33, 46, 51, 52, 54, 56, 66, 71, 80, 88, 94, 98};
	
	private static final int[][] ActivityPupilIds = {AthleticsPupils, BasketballPupils, DramaClubPupils, FootballPupils,
													 GymnasticsPupils, HistoryPupils, IctClubPupils, SwimmingPupils,
													 MartialArtsPupils, TrampolinePupils, VolleyballPupils};
	
	public static Map<String, ActivityInfo> ActivityInfoMap;
	
	static {
		ActivityInfoMap = new HashMap<String, ActivityInfo>();
		for (int i = 0; i < NumberOfActivities; i++) {
			ActivityInfo activityInfo = new ActivityInfo(ActivityNames[i], ActivityDescriptions[i], ActivityDateSets[i],
														 ActivityVenues[i], createTutorUsername(TutorFullNames[i]),
														 createTutorPassword(TutorFullNames[i]), getTutorFirstName(TutorFullNames[i]),
														getTutorLastName(TutorFullNames[i]), TutorBirthDates[i], ActivityPupilIds[i]);
			ActivityInfoMap.put(ActivityNames[i], activityInfo);
		}
	}
	
	private static String createTutorUsername(String tutorFullName) {
		return tutorFullName.equals("Test Tutor") ? "tutor" : tutorFullName.toLowerCase().charAt(0) + getTutorLastName(tutorFullName).toLowerCase(); 
	}
	
	private static String createTutorPassword(String tutorFullName) {
		return createTutorUsername(tutorFullName).equals("tutor") ? "tutor" : createTutorUsername(tutorFullName) + "123";
	}
	
	private static String getTutorFirstName(String tutorFullName) {
		return tutorFullName.split(" ")[0];
	}
	
	private static String getTutorLastName(String tutorFullName) {
		return tutorFullName.split(" ")[1];
	}
}
