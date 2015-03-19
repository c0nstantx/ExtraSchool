package models.util;

import java.util.Date;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import controllers.service.ActivityService;
import controllers.service.ActivitySessionService;
import controllers.service.MembershipService;
import controllers.service.UserService;
import play.db.jpa.Transactional;
import models.domain.Activity;
import models.domain.Membership;
import models.domain.User;
import models.domain.UserType;
import models.persistence.JPAUtil;

/**
 * DBCreator Class
 * Populates the database with data
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class DBCreator {
	
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
	
	//private static final int MaximumUniqueNames = FirstNames.length * LastNames.length;

	private static final int NumberOfPupils = 100;
	private static final int MinimumAgeInYearsForPupils = 15;
	private static final int MaximumAgeInYearsForPupils = 18;
	
	private static final int SecondsPerYear = 3600 * 24 * 365;
	private static final int MaxUsernameLength = 5;
	
	private Random randomGenerator = new Random();
	private int lastCombinationUsed = 0;
	
	private UserService userService;
	private ActivityService activityService;
	private ActivitySessionService sessionService;
	private MembershipService membershipService;
	
	private EntityManager em;
	
	public DBCreator() {
		em = JPAUtil.getCurrentEntityManager();
    	//userService = new UserService();
    	//activityService = new ActivityService();
    	//sessionService = new ActivitySessionService();
    	//membershipService = new MembershipService();
	}
	
	@Transactional
    public void clearDB() {
        Query query = em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE users");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE activities");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE activity_sessions");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE session_registers");
        query.executeUpdate();
        query = em.createNativeQuery("TRUNCATE TABLE memberships");
        query.executeUpdate();
        query = em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE");
        query.executeUpdate();
    }
	
	public void preparePresentationDatabase() {
		createAdminsAndPupils();
		createActivitiesWithTutors();;
	}
	
	private void createAdminsAndPupils() {
		createAdmins();
		createPupils();
	}

/*
	private void createAdminsOld() {
		userService.createUser("kchristof", "123456", UserType.Admin, "Konstantinos", "Christofilos", DateLib.getDateObject(17, 3, 1981));
		userService.createUser("pgerard", "123456", UserType.Admin, "Pavlos", "Gerardos", DateLib.getDateObject(14, 3, 1979));
		userService.createUser("spanta", "123456", UserType.Admin, "Sokratis", "Pantazaras", DateLib.getDateObject(20, 10, 1978));
	}
*/
	
	private void createAdmins() {
		User admin1 = new User("kchristof", "123456", UserType.Admin, "Konstantinos", "Christofilos", DateLib.getDateObject(17, 3, 1981));
		em.persist(admin1);
		User admin2 = new User("pgerard", "123456", UserType.Admin, "Pavlos", "Gerardos", DateLib.getDateObject(14, 3, 1979));
		em.persist(admin2);
		User admin3 = new User("spanta", "123456", UserType.Admin, "Sokratis", "Pantazaras", DateLib.getDateObject(20, 10, 1978));
		em.persist(admin3);
	}
	
	private void createPupils() {
		
	}

/*
	private void createActivitiesWithTutorsOld() {
		// ATHLETICS
		String athleticsDescription = "Athletics is an exclusive collection of sporting events that involve competitive running, jumping, "
				+ "throwing, and walking. The most common types of athletics competitions are track and field, road running, cross country running, "
				+ "and race walking. The simplicity of the competitions, and the lack of a need for expensive equipment, makes athletics one of the "
				+ "most commonly competed sports in the world. Athletics is mostly an individual sport, with the exception of relay races and "
				+ "competitions which combine athletes' performances for a team score, such as cross country.";
		Activity athleticsActivity = activityService.createActivity("Athletics", athleticsDescription, "Outside fields");
		sessionService.createActivitySessions(athleticsActivity, DateLib.getDateObject(13, 1, 2015, 16, 0, 0), DateLib.getDateObject(26, 5, 2015, 16, 0, 0),
				new boolean[] {false, false, true, false, false, false, false});
		sessionService.createActivitySessions(athleticsActivity, DateLib.getDateObject(17, 1, 2015, 10, 0, 0), DateLib.getDateObject(30, 5, 2015, 10, 0, 0),
				new boolean[] {false, false, false, false, false, false, true});
		User athleticsTutor = userService.createUser("kvoisey", "123456", UserType.Tutor, "Kate", "Voisey", DateLib.getDateObject(11, 8, 1985));
		membershipService.createMembership(athleticsActivity, athleticsTutor);
		
		// BASKETBALL
		String basketballDescription = "Basketball is a sport played by two teams of five players on a rectangular court. The objective is "
				+ "to shoot a ball through a hoop 18 inches (46 cm) in diameter and 10 feet (3.048 m) high mounted to a backboard at each end. "
				+ "Basketball is one of the world's most popular and widely viewed sports.";
		Activity basketballActivity = activityService.createActivity("Basketball", basketballDescription, "Basketball court");
		sessionService.createActivitySessions(basketballActivity, DateLib.getDateObject(12, 1, 2015, 18, 0, 0), DateLib.getDateObject(27, 5, 2015, 18, 0, 0),
				new boolean[] {false, true, false, true, false, false, false});
		sessionService.createActivitySessions(basketballActivity, DateLib.getDateObject(16, 1, 2015, 17, 0, 0), DateLib.getDateObject(29, 5, 2015, 17, 0, 0),
				new boolean[] {false, false, false, false, false, true, false});
		User basketballTutor = userService.createUser("clatanis", "123456", UserType.Tutor, "Chris", "Latanis", DateLib.getDateObject(21, 1, 1987));
		membershipService.createMembership(basketballActivity, basketballTutor);
		
		// DRAMA CLUB
		String dramaDescription = "Drama is the specific mode of fiction represented in performance.The term comes from a Greek word meaning action, "
				+ "which is derived from the verb meaning to do or to act. The enactment of drama in theatre, performed by actors on a stage before"
				+ " an audience, presupposes collaborative modes of production and a collective form of reception. The structure of dramatic texts, "
				+ "unlike other forms of literature, is directly influenced by this collaborative production and collective reception. The early "
				+ "modern tragedy Hamlet (1601) by Shakespeare and the classical Athenian tragedy Oedipus the King (c. 429 BC) by Sophocles are "
				+ "among the masterpieces of the art of drama. A modern example is Long Day's Journey into Night by Eugene O’Neill (1956).";
		Activity dramaActivity = activityService.createActivity("Drama Club", dramaDescription, "Room A5");
		sessionService.createActivitySessions(dramaActivity, DateLib.getDateObject(12, 1, 2015, 17, 0, 0), DateLib.getDateObject(27, 5, 2015, 17, 0, 0),
				new boolean[] {false, true, false, true, false, false, false});
		User dramaTutor = userService.createUser("jgunn", "123456", UserType.Tutor, "Julie", "Gunn", DateLib.getDateObject(3, 12, 1968));
		membershipService.createMembership(dramaActivity, dramaTutor);
	}
*/

	private void createActivitiesWithTutors() {
		/* ATHLETICS */
		String athleticsDescription = "Athletics is an exclusive collection of sporting events that involve competitive running, jumping, "
				+ "throwing, and walking. The most common types of athletics competitions are track and field, road running, cross country running, "
				+ "and race walking.";
		Activity athleticsActivity = new Activity("Athletics", athleticsDescription, "Outside fields");
		athleticsActivity.createSessions(DateLib.getDateObject(13, 1, 2015, 16, 0, 0), DateLib.getDateObject(26, 5, 2015, 16, 0, 0),
				new boolean[] {false, false, true, false, false, false, false});
		athleticsActivity.createSessions(DateLib.getDateObject(17, 1, 2015, 10, 0, 0), DateLib.getDateObject(30, 5, 2015, 10, 0, 0),
				new boolean[] {false, false, false, false, false, false, true});
		User athleticsTutor = new User("kvoisey", "123456", UserType.Tutor, "Kate", "Voisey", DateLib.getDateObject(11, 8, 1985));
		Membership athleticsMembership = new Membership(athleticsActivity, athleticsTutor, DateLib.getDateObject(1, 1, 2015));
		em.persist(athleticsActivity);
		em.persist(athleticsTutor);
		em.persist(athleticsMembership);
		
		/* BASKETBALL */
		String basketballDescription = "Basketball is a sport played by two teams of five players on a rectangular court. The objective is "
				+ "to shoot a ball through a hoop 18 inches (46 cm) in diameter and 10 feet (3.048 m) high mounted to a backboard at each end.";
		Activity basketballActivity = new Activity("Basketball", basketballDescription, "Basketball court");
		basketballActivity.createSessions(DateLib.getDateObject(12, 1, 2015, 18, 0, 0), DateLib.getDateObject(27, 5, 2015, 18, 0, 0),
				new boolean[] {false, true, false, true, false, false, false});
		basketballActivity.createSessions(DateLib.getDateObject(16, 1, 2015, 17, 0, 0), DateLib.getDateObject(29, 5, 2015, 17, 0, 0),
				new boolean[] {false, false, false, false, false, true, false});
		User basketballTutor = new User("clatanis", "123456", UserType.Tutor, "Chris", "Latanis", DateLib.getDateObject(21, 1, 1987));
		Membership basketballMembership = new Membership(basketballActivity, basketballTutor, DateLib.getDateObject(1, 1, 2015));
		em.persist(basketballActivity);
		em.persist(basketballTutor);
		em.persist(basketballMembership);
		
		/* DRAMA CLUB */
		String dramaDescription = "Drama is the specific mode of fiction represented in performance.The term comes from a Greek word meaning action, "
				+ "which is derived from the verb meaning to do or to act.";
		Activity dramaActivity = new Activity("Drama Club", dramaDescription, "Room A5");
		dramaActivity.createSessions(DateLib.getDateObject(12, 1, 2015, 17, 0, 0), DateLib.getDateObject(27, 5, 2015, 17, 0, 0),
				new boolean[] {false, true, false, true, false, false, false});
		User dramaTutor = new User("jgunn", "123456", UserType.Tutor, "Julie", "Gunn", DateLib.getDateObject(3, 12, 1968));
		Membership dramaMembership = new Membership(dramaActivity, dramaTutor, DateLib.getDateObject(1, 1, 2015));
		em.persist(dramaActivity);
		em.persist(dramaTutor);
		em.persist(dramaMembership);
		
		/* FOOTBALL */
		String footballDescription = "Association football, more commonly known as football or soccer, is a sport played between two teams of eleven "
				+ "players with a spherical ball. It is played by 250 million players in over 200 countries, making it the world's most popular sport.";
		Activity footballActivity = new Activity("Football", footballDescription, "Football pitch");
		footballActivity.createSessions(DateLib.getDateObject(12, 1, 2015, 16, 0, 0), DateLib.getDateObject(27, 5, 2015, 16, 0, 0),
				new boolean[] {false, true, false, true, false, false, false});
		footballActivity.createSessions(DateLib.getDateObject(17, 1, 2015, 10, 0, 0), DateLib.getDateObject(30, 5, 2015, 10, 0, 0),
				new boolean[] {false, false, false, false, false, false, true});
		User footballTutor = new User("nmoustakas", "123456", UserType.Tutor, "Nick", "Moustakas", DateLib.getDateObject(13, 11, 1980));
		Membership footballMembership = new Membership(footballActivity, footballTutor, DateLib.getDateObject(1, 1, 2015));
		em.persist(footballActivity);
		em.persist(footballTutor);
		em.persist(footballMembership);
		
		/* GYMNASTICS */
		String gymnasticsDescription = "Gymnastics is a sport involving the performance of exercises requiring physical strength, flexibility, "
				+ "power, agility, coordination, grace, balance and control. Internationally, all of the competitive gymnastics events are governed "
				+ "by the FIG.";
		Activity gymnasticsActivity = new Activity("Gymnastics", gymnasticsDescription, "Inside hall");
		gymnasticsActivity.createSessions(DateLib.getDateObject(13, 1, 2015, 17, 0, 0), DateLib.getDateObject(28, 5, 2015, 17, 0, 0),
				new boolean[] {false, false, true, false, true, false, false});
		gymnasticsActivity.createSessions(DateLib.getDateObject(17, 1, 2015, 10, 0, 0), DateLib.getDateObject(30, 5, 2015, 10, 0, 0),
				new boolean[] {false, false, false, false, false, false, true});
		User gymnasticsTutor = new User("gziara", "123456", UserType.Tutor, "Giota", "Ziara", DateLib.getDateObject(19, 4, 1985));
		Membership gymnasticsMembership = new Membership(gymnasticsActivity, gymnasticsTutor, DateLib.getDateObject(1, 1, 2015));
		em.persist(gymnasticsActivity);
		em.persist(gymnasticsTutor);
		em.persist(gymnasticsMembership);
		
		/* HISTORY CLUB */
		String historyDescription = "History is the study of the past, particularly how it relates to humans.It is an umbrella term that relates to "
				+ "past events as well as the memory, discovery, collection, organization, presentation, and interpretation of information about these "
				+ "events.";
		Activity historyActivity = new Activity("History Club", historyDescription, "Room B1");
		historyActivity.createSessions(DateLib.getDateObject(15, 1, 2015, 16, 0, 0), DateLib.getDateObject(29, 5, 2015, 16, 0, 0),
				new boolean[] {false, false, false, false, true, true, false});
		User historyTutor = new User("lstewart", "123456", UserType.Tutor, "Elizabeth", "Stewart", DateLib.getDateObject(30, 4, 1957));
		Membership historyMembership = new Membership(historyActivity, historyTutor, DateLib.getDateObject(1, 1, 2015));
		em.persist(historyActivity);
		em.persist(historyTutor);
		em.persist(historyMembership);
		
		/* ICT CLUB */
		String ictDescription = "Information and communications technology (ICT) is often used as an extended synonym for information technology (IT), "
				+ "but is a more specific term that stresses the role of unified communication and the integration of telecommunications and computers.";
		Activity ictActivity = new Activity("ICT Club", ictDescription, "ICT lab 1");
		ictActivity.createSessions(DateLib.getDateObject(12, 1, 2015, 16, 0, 0), DateLib.getDateObject(26, 5, 2015, 16, 0, 0),
				new boolean[] {false, true, true, false, false, false, false});
		User ictTutor = new User("troberts", "123456", UserType.Tutor, "Tim", "Roberts", DateLib.getDateObject(12, 1, 1966));
		Membership ictMembership = new Membership(ictActivity, ictTutor, DateLib.getDateObject(1, 1, 2015));
		em.persist(ictActivity);
		em.persist(ictTutor);
		em.persist(ictMembership);
		
		/* Swimming */
		String swimmingDescription = "The recreational activity of swimming has been recorded since prehistoric times. The earliest recording of "
				+ "swimming dates back to Stone Age paintings from around 10000 years ago. Written references date from 2000 BC.";
		Activity swimmingActivity = new Activity("Swimming", swimmingDescription, "Swimming pool");
		swimmingActivity.createSessions(DateLib.getDateObject(12, 1, 2015, 16, 0, 0), DateLib.getDateObject(29, 5, 2015, 16, 0, 0),
				new boolean[] {false, true, false, true, false, true, false});
		User swimmingTutor = new User("lpearson", "123456", UserType.Tutor, "Laura", "Pearson", DateLib.getDateObject(27, 6, 1991));
		Membership swimmingMembership = new Membership(swimmingActivity, swimmingTutor, DateLib.getDateObject(1, 1, 2015));
		em.persist(swimmingActivity);
		em.persist(swimmingTutor);
		em.persist(swimmingMembership);
		
		/* Martial Arts */
		String martialArtsDescription = "Martial arts are codified systems and traditions of combat practices, which are practiced for a variety "
				+ "of reasons: self-defense, competition, physical health and fitness, entertainment, as well as mental, physical, and spiritual development.";
		Activity martialArtsActivity = new Activity("Martial Arts", martialArtsDescription, "Inside hall");
		martialArtsActivity.createSessions(DateLib.getDateObject(17, 1, 2015, 11, 0, 0), DateLib.getDateObject(30, 5, 2015, 11, 0, 0),
				new boolean[] {false, false, false, false, false, false, true});
		User martialArtsTutor = new User("rwatts", "123456", UserType.Tutor, "Richard", "Watts", DateLib.getDateObject(2, 2, 1981));
		Membership martialArtsMembership = new Membership(martialArtsActivity, martialArtsTutor, DateLib.getDateObject(1, 1, 2015));
		em.persist(martialArtsActivity);
		em.persist(martialArtsTutor);
		em.persist(martialArtsMembership);
		
		/* Trampoline */
		String trampolineDescription = "A trampoline is a device consisting of a piece of taut, strong fabric stretched over a steel frame using many coiled "
				+ "springs. People bounce on trampolines for recreational and competitive purposes.";
		Activity trampolineActivity = new Activity("Trampoline", trampolineDescription, "Inside hall");
		trampolineActivity.createSessions(DateLib.getDateObject(17, 1, 2015, 12, 0, 0), DateLib.getDateObject(30, 5, 2015, 12, 0, 0),
				new boolean[] {false, false, false, false, false, false, true});
		User trampolineTutor = new User("pkolyvas", "123456", UserType.Tutor, "Philip", "Kolyvas", DateLib.getDateObject(2, 10, 1967));
		Membership trampolineMembership = new Membership(trampolineActivity, trampolineTutor, DateLib.getDateObject(1, 1, 2015));
		em.persist(trampolineActivity);
		em.persist(trampolineTutor);
		em.persist(trampolineMembership);
		
		/* Volleyball */
		String volleyballDescription = "Volleyball is a team sport in which two teams of six players are separated by a net. Each team tries to "
				+ "score points by grounding a ball on the other team's court under organized rules.";
		Activity volleyballActivity = new Activity("Volleyball", volleyballDescription, "Basketball court");
		volleyballActivity.createSessions(DateLib.getDateObject(12, 1, 2015, 17, 0, 0), DateLib.getDateObject(27, 5, 2015, 17, 0, 0),
				new boolean[] {false, true, false, true, false, false, false});
		User volleyballTutor = new User("dgrannon", "123456", UserType.Tutor, "Daniel", "Grannon", DateLib.getDateObject(30, 20, 1987));
		Membership volleyballMembership = new Membership(volleyballActivity, volleyballTutor, DateLib.getDateObject(1, 1, 2015));
		em.persist(volleyballActivity);
		em.persist(volleyballTutor);
		em.persist(volleyballMembership);
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
		DBCreator dbCreator = new DBCreator();
		dbCreator.preparePresentationDatabase();
	}
}