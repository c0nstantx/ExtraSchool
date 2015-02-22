package models.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import play.db.jpa.Transactional;

/**
 * Init DB for testing
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
public class Initializer
{
    @Transactional
    public void clearDB()
    {
        EntityManager em = JPAUtil.getCurrentEntityManager();
   
        Query query = em.createNativeQuery("DELETE FROM users");
        query.executeUpdate();
    }

    @Transactional
    public void initDB(boolean mockupData)
    {
        clearDB();
    	if (mockupData == true) {
    		populateMockupData();
    	}
    }

    @Transactional
    public void initDB()
    {
    	initDB(true);
    }
    
    private void populateMockupData()
    {
    	
    }
    
}
