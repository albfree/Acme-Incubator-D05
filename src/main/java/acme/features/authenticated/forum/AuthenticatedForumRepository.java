
package acme.features.authenticated.forum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.forums.Forum;
import acme.entities.messages.Message;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedForumRepository extends AbstractRepository {

	@Query("select f from Forum f where f.id = ?1")
	Forum findOneForumById(int id);

	@Query("select f from Forum f where f.investment.entrepreneur.userAccount.id = ?1")
	Collection<Forum> findForumsOfMyInvestmentRounds(int id);

	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findOneUserAccountById(int id);

	@Query("select ua from UserAccount ua")
	Collection<UserAccount> findManyUserAccount();

	@Query("select f from Forum as f where ?1 member of f.participants")
	Collection<Forum> findForumsIAmParticipant(UserAccount user);

	@Query("select f from Forum f where f.creator.id = ?1")
	Collection<Forum> findForumIAmCreator(int id);

	@Query("select m from Message m where m.forum.id = ?1")
	Collection<Message> findMessagesOfAForum(int id);

}
