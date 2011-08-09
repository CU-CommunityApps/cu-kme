package edu.iu.m.news.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import edu.iu.m.news.entity.MaintRss;

@Repository
public class MaintRssDaoImpl implements MaintRssDao {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<MaintRss> findAllRss() {
        Query query = entityManager.createQuery("select r from MaintRss r order by r.order");
        try { 
        	return query.getResultList();
        } catch (Exception e) {        	
        	return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<MaintRss> findAllRssOrderBy(String order) {
        Query query = entityManager.createQuery("select r from MaintRss r order by " + order);
        try { 
        	return query.getResultList();
        } catch (Exception e) {        	
        	return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<MaintRss> rssDaofindAllRss(String clause) {
        Query query = entityManager.createQuery("select r from MaintRss r " + clause);
        try { 
        	return query.getResultList();
        } catch (Exception e) {        	
        	return null;
        }
    }
    
    public MaintRss findRssById(Long id) {
        Query query = entityManager.createQuery("select r from MaintRss r where r.rssId = :id");
        query.setParameter("id", id);
        try { 
        	return (MaintRss) query.getSingleResult();
        } catch (Exception e) {        	
        	return null;
        }
    }

    public MaintRss findRssByUrl(String url) {
        Query query = entityManager.createQuery("select r from MaintRss r where r.rssId = :url");
        query.setParameter("url", url);
        try { 
        	return (MaintRss) query.getSingleResult();
        } catch (Exception e) {        	
        	return null;
        }
    }
    
    public MaintRss findRssByShortCode(String code) {
        Query query = entityManager.createQuery("select r from MaintRss r where r.shortCode = :code");
        query.setParameter("code", code);
        try { 
        	return (MaintRss) query.getSingleResult();
        } catch (Exception e) {        	
        	return null;
        }
    }
    
    @SuppressWarnings("unchecked")
	public List<MaintRss> findAllNewsRssByCampusCode(String campusCode) {
        Query query = entityManager.createQuery("select r from MaintRss r where r.type like 'NEWS' and (r.campus like :campusCode or r.campus like 'UA') order by r.order");
        query.setParameter("campusCode", campusCode);
        try { 
        	return query.getResultList();
        } catch (Exception e) {        	
        	return null;
        }
    }

    public void saveRss(MaintRss rss) {
        if (rss == null) {
            return;
        }
        if (rss.getRssId() == null) {
            entityManager.persist(rss);
            rss.setOrder(rss.getRssId().intValue());
            entityManager.merge(rss);
        } else {
            entityManager.merge(rss);
        }
    }

    public void deleteRssById(Long id) {
        Query query = entityManager.createQuery("delete from MaintRss r where r.rssId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public String findUrlByShortCode(String code) {
        Query query = entityManager.createQuery("select r from MaintRss r where r.shortCode = :code");
        query.setParameter("code", code);
        try { 
        	MaintRss rss = (MaintRss) query.getSingleResult();
            return rss.getUrl();
        } catch (Exception e) {        	
        	return null;
        }
    }
    
    public void reoder(Long id, boolean up) {
        List<MaintRss> list = findAllRss();
        MaintRss last = null;
        boolean flag = false;
        int index = -1;
        int count = list.get(0).getOrder();
        for (MaintRss rss : list) {
            index++;
            if (rss.getRssId().equals(id)) {            
                if (up && last != null) {
                    swap(last, rss);
                    count = last.getOrder() + 1;
                    continue;
                } else if (!up) {
                    MaintRss next = list.get(index + 1);
                    swap(rss, next);
                    count = next.getOrder() + 1;
                    continue;                    
                }
                flag = true;
            }
            if (flag) {
                rss.setOrder(count);  
                entityManager.merge(rss);
            }
            count++;
            last = rss;
        }
    }

    private void swap(MaintRss one, MaintRss two) {
        int temp = one.getOrder();
        one.setOrder(two.getOrder());
        two.setOrder(temp);
        entityManager.merge(one);
        entityManager.merge(two);
    }
    
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
