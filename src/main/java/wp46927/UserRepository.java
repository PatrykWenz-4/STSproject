/*package wp46927;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByName(String name);
	
	@Transactional
	long deleteByName(String name);
}

*/

package wp46927;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByName(String name);
    long deleteByName(String userName);
    void deleteById(User user);
}
