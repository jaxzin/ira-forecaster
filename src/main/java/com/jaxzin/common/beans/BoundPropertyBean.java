package com.jaxzin.common.beans;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Date: Feb 16, 2006
 * Time: 6:30:37 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public interface BoundPropertyBean {
    void addPropertyChangeListener(PropertyChangeListener listener);

    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    List<PropertyChangeListener> getPropertyChangeListeners();
}
