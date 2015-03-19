import java.util.Properties;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import models.persistence.Initializer;
import models.util.DBCreator;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.reflections.Reflections;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		Logger.info("Application started...");
		Logger.info("DataLoader: Loading initial data");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				//Initializer init = new Initializer();
				//init.clearDB();
				//init.initDB();
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
