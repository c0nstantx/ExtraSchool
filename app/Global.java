import javax.transaction.Transactional;

import models.util.db.DBCreator;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;

public class Global extends GlobalSettings {

	@Override
	@Transactional
	public void onStart(Application app) {
		Logger.info("Application started...");
		Logger.info("DataLoader: Loading initial data");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				DBCreator dbCreator = new DBCreator();
				dbCreator.clearDB();
				dbCreator.preparePresentationDatabase();
			}
		});
	}

	@Override
	public void onStop(Application app) {
		Logger.info("Application shutdown...");
	}
}
