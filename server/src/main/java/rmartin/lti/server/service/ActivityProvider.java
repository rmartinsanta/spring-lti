package rmartin.lti.server.service;

import rmartin.lti.server.model.Activity;

/**
 * TODO if this interface is internal remove from API package
 */
public interface ActivityProvider {
    Activity getActivity(String id);
    boolean canLaunch(String id);
    boolean exists(String id);
}
