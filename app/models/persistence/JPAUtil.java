package models.persistence;

import javax.persistence.EntityManager;

import play.db.jpa.JPA;

public class JPAUtil {

	static EntityManager em = null;
    
    public static EntityManager getCurrentEntityManager() { 
    	if (em == null || !em.isOpen()){
    		em = createEntityManager();
    	}
    	return em;  	
    }
    
    public static EntityManager getCurrentEntityManager(Boolean defaultEm) { 
    	if (em == null || !em.isOpen()){
    		em = createEntityManager(defaultEm);
    	}
    	return em;  	
    }
    
    public static void setCurrentEntityManager(EntityManager e){
    	em = e;
    }
    
    public static EntityManager createEntityManager() {
    	
    	return JPA.em();
    }
    
    public static EntityManager createEntityManager(Boolean defaultEm) {

    	if (defaultEm) {
    		return JPA.em("default");
    	}
    	return JPA.em();
    }
    
}
