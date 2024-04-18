package com.social_network.server.entities;

import com.social_network.server.HibernateUtil;
import com.social_network.server.utils.Graph;
import com.social_network.server.utils.Status;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

@Entity
@jakarta.persistence.Table(name = "connects_to", schema = "konnect")
public class ConnectsTo {
    @EmbeddedId
    private ConnectsToPK id;

    @Basic
    @Column(name = "status")
    private String status;

    public ConnectsToPK getId() {
        return id;
    }

    public String getUserFromId() {
        return this.getId().getUserFromId();
    }

    public String getUserToId() {
        return this.getId().getUserToId();
    }

    public void setId(ConnectsToPK id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ConnectsTo(String userFromId, String userToId) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.id = new ConnectsToPK(userFromId, userToId);
        this.status = "pending";
        System.out.println(this.id);
    }

    public ConnectsTo() {}

    public static Map<String, List<List<String>>> checkGroups() {
        ArrayList<ConnectsTo> connections = ConnectsTo.list();
        Graph graph = new Graph(connections);
        List<List<List<String>>> listOfKns = new ArrayList<>();

        for (int n = 0; n <= 7; n++) {
            List<List<String>> allKns = graph.findKn(n);
            for (int m = n - 1; m >= 0; m--) {
                List<List<String>> pastKns = listOfKns.get(m);
                Iterator<List<String>> pastKnIterator = pastKns.iterator();
                while (pastKnIterator.hasNext()) {
                    List<String> pastKn = pastKnIterator.next();
                    for (List<String> currKn : allKns) {
                        if (isKnContained(currKn, pastKn)) {
                            // If Kn in current Kn contains past Kn, remove past Kn
                            pastKnIterator.remove();
                            break;
                        }
                    }
                }
            }
            if (n == 0 || n == 1 || n == 2) {
                listOfKns.add(new ArrayList<>());
            } else {
                listOfKns.add(removeDuplicates(allKns));
            }
        }
        for (int i = 0; i < listOfKns.size(); i++) {
            System.out.println("K" + i + ": " + listOfKns.get(i));
        }
        ArrayList<KnUser> allGroupUsers = KnUser.list();
        Map<String, List<String>> knParticipantsMap = new HashMap<>();

        // Iterate over all KnUser objects
        for (KnUser user : allGroupUsers) {
            String knId = user.getKnId();
            String userId = user.getUserId();

            // Get the list of participants for the current knId
            List<String> participants = knParticipantsMap.getOrDefault(knId, new ArrayList<>());

            // Add the userId to the list of participants
            participants.add(userId);

            // Update the map with the updated list of participants for the current knId
            knParticipantsMap.put(knId, participants);
        }

        List<List<String>> mustCreate = new ArrayList<>();
        List<String> toDelete = new ArrayList<>();

        // Iterate over each Kn in listOfKns
        for (List<List<String>> kns : listOfKns) {
            // Iterate over each Kn in the current list of Kns
            for (List<String> kn : kns) {
                boolean knFound = false;

                // Check if this Kn is already a list of participants in some group
                for (Map.Entry<String, List<String>> entry : knParticipantsMap.entrySet()) {
                    List<String> participants = entry.getValue();

                    // Check if the Kn is already a list of participants (order doesn't matter)
                    if (isSameListIgnoringOrder(kn, participants)) {
                        knFound = true;
                        break;
                    }
                }

                // If the Kn is not already a list of participants in any group, create a new group
                if (!knFound) {
                    mustCreate.add(kn);
                }
            }
        }

        for (Map.Entry<String, List<String>> entry : knParticipantsMap.entrySet()) {
            String knId = entry.getKey();
            List<String> participants = entry.getValue();
            int n = participants.size();

            // Iterate over Kn lists in listOfKns starting from index n+1 to 7
            for (int i = n + 1; i <= 7; i++) {
                List<List<String>> kns = listOfKns.get(i);
                // Iterate over each Kn in the current list of Kns
                for (List<String> kn : kns) {
                    // Check if the Kn contains the participants list
                    if (kn.containsAll(participants)) {
                        // If yes, remove the Kn list
                        toDelete.add(knId);
                        // As we've removed an element, we need to decrement the loop variable and iterator to avoid skipping elements
                        i--;
                        break; // No need to continue checking the current Kn list
                    }
                }
            }
        }

        List<List<String>> mustDelete = new ArrayList<>();
        mustDelete.add(toDelete);
        Map<String, List<List<String>>> result = new HashMap<>();
        result.put("mustCreate", mustCreate);
        result.put("mustDelete", mustDelete);
        return result;
    }

    private static boolean isKnContained(List<String> kn, List<String> pastKn) {
        for (String id : pastKn) {
            if (!kn.contains(id)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSameListIgnoringOrder(List<String> list1, List<String> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        List<String> tempList = new ArrayList<>(list2);
        for (String element : list1) {
            if (!tempList.remove(element)) {
                return false;
            }
        }
        return tempList.isEmpty();
    }

    private static List<List<String>> removeDuplicates(List<List<String>> list) {
        Set<List<String>> set = new HashSet<>();
        for (List<String> sublist : list) {
            sublist.sort(null);
            set.add(sublist);
        }
        return new ArrayList<>(set);
    }

    public static ConnectsTo get(String userFromId, String userToId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            transaction.begin();
            ConnectsToPK id = new ConnectsToPK(userFromId,userToId);
            String hql = "FROM ConnectsTo WHERE id.userFromId = :userFromId and id.userToId = :userToId";
            Query query = session.createQuery(hql, ConnectsTo.class);
            query.setParameter("userFromId", id.getUserFromId());
            query.setParameter("userToId", id.getUserToId());
            ConnectsTo connection = (ConnectsTo) query.getResultList().get(0);

            transaction.commit();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    public static ArrayList<ConnectsTo> notifications(String userId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        System.out.println(userId);
        try (session) {
            transaction.begin();
            String hql = "FROM ConnectsTo WHERE id.userToId = :userId AND status = :status";
            Query query = session.createQuery(hql, ConnectsTo.class);
            query.setParameter("userId", userId);
            query.setParameter("status", "pending");
            ArrayList<ConnectsTo> connection = (ArrayList<ConnectsTo>) query.getResultList();

            transaction.commit();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    public static List<Object[]> getUsers(String userId, String searchFilter) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            String hql = "SELECT u, c.status FROM ConnectsTo c " +
                    "LEFT JOIN User u ON c.id.userToId = u.id " +
                    "WHERE u.username ilike :searchFilter";
            Query query = session.createQuery(hql);
            query.setParameter("searchFilter", searchFilter);
            List<Object[]> results = query.getResultList();

            transaction.commit();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            return null;
        }
    }

    public static ArrayList<ConnectsTo> list() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            transaction.begin();
            String hql = "FROM ConnectsTo";
            Query query = session.createQuery(hql, ConnectsTo.class);
            ArrayList<ConnectsTo> connections = (ArrayList<ConnectsTo>) query.getResultList();

            transaction.commit();
            return connections;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    public static HashMap<String, String> listRelatedConnectionsStatus(String userId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();

        try (session) {
            transaction.begin();
            String hql = "FROM ConnectsTo WHERE (id.userToId = :userId OR id.userFromId = :userId)";
            Query query = session.createQuery(hql, ConnectsTo.class);
            query.setParameter("userId", userId);
            ArrayList<ConnectsTo> relatedConnections = (ArrayList<ConnectsTo>) query.getResultList();
            transaction.commit();
            HashMap<String, String> idToStatus = new HashMap<String, String>();
            for (ConnectsTo connection : relatedConnections) {
                if (connection.getUserFromId().equals(userId)) {
                    idToStatus.put(connection.getUserToId(), connection.getStatus());
                } else {
                    idToStatus.put(connection.getUserFromId(), connection.getStatus());
                }
            }
            return idToStatus;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConnectsTo that = (ConnectsTo) o;

        if (!id.getUserFromId().equals(that.id.getUserFromId())) return false;
        if (!id.getUserToId().equals(that.id.getUserToId())) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.getUserFromId().hashCode();
        result = 31 * result + id.getUserToId().hashCode();
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
