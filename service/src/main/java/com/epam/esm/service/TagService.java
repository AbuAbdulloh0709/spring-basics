package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService extends BaseService<Tag> {
    /**
     * Method for retrieving most popular tags of the user with highest cost of all orders.
     *
     * @param user_id ID of user
     * @return List of Tags
     */
    List<Tag> getMostPopularTagsOfUserWithHighestCostOfAllOrders(long user_id);

}
