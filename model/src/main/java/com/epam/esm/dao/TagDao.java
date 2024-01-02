package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends BaseDao<Tag> {
    /**
     * Method for getting an entity objects by name.
     *
     * @param name name of entity to get
     * @return optional Entity object
     */
    Optional<Tag> getByName(String name);

    /**
     * Method for retrieving most popular tags of the user with highest cost of all orders.
     *
     * @param user_id ID of user
     * @return List of Tags
     */
    List<Tag> getMostPopularTagsWithHighestCostOfAllOrders(long user_id);
}
