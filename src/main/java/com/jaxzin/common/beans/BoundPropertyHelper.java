/**
 * Copyright (C) 2006-2011 Brian R. Jackson <brian@jaxzin.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jaxzin.common.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

/**
 * Responsible for collecting lists of PropertyChangeListeners that are to be bound to bean properties
 * and to notify them of events as they occur.  It is expected that other implementations of BoundPropertyBean
 * will delegate to an instance of this class.
 * Date: Feb 16, 2006
 * Time: 6:28:14 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
@SuppressWarnings({"OverloadedMethodsWithSameNumberOfParameters"})
public class BoundPropertyHelper implements BoundPropertyBean {

    @SuppressWarnings({"CollectionWithoutInitialCapacity"})
    private Map<String, List<PropertyChangeListener>> listeners =
            new LinkedHashMap<String, List<PropertyChangeListener>>();

    public BoundPropertyHelper() {
    }

    // ============== Event Model =====================================================================================

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        addPropertyChangeListener("", listener);
    }

    public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
        List<PropertyChangeListener> items = listeners.get(propertyName);
        if(items == null) {
            //noinspection CollectionWithoutInitialCapacity
            items = new ArrayList<PropertyChangeListener>();
            listeners.put(propertyName, items);
        }
        items.add(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        for(List<PropertyChangeListener> items : listeners.values()) {
            items.remove(listener);
        }
    }

    public List<PropertyChangeListener> getPropertyChangeListeners() {
        //noinspection CollectionWithoutInitialCapacity
        final List<PropertyChangeListener> l = new ArrayList<PropertyChangeListener>();
        for(List<PropertyChangeListener> items : listeners.values()) {
            l.addAll(items);
        }
        return l;
    }

    @SuppressWarnings({"PublicMethodNotExposedInInterface"})
    public List<PropertyChangeListener> getPropertyChangeListeners(final String propertyName) {
        List<PropertyChangeListener> items = listeners.get(propertyName);
        if(items == null) {
            //noinspection CollectionWithoutInitialCapacity
            items = new ArrayList<PropertyChangeListener>();
            listeners.put(propertyName, items);
        }
        return items;
    }

    @SuppressWarnings({"MethodWithMultipleLoops", "PublicMethodNotExposedInInterface"})
    public <T> void firePropertyChanged(final String propertyName, final T oldValue, final T newValue) {
//        System.out.println("Firing property change for '"+propertyName+"' from <"+oldValue+"> to <"+newValue+">");
        final PropertyChangeEvent e = new PropertyChangeEvent(this, propertyName, oldValue, newValue);

        // Notify listeners of the changed property...make a defensive copy for thread-safety
        final List<PropertyChangeListener> listenersOfGivenProperty =
                new ArrayList<PropertyChangeListener>(getPropertyChangeListeners(propertyName));
        for(PropertyChangeListener l : listenersOfGivenProperty) {
            l.propertyChange(e);
        }

        // Notify listeners of all properties...make a defensive copy for thread-safety
        final List<PropertyChangeListener> listenersOfAllProperties =
                new ArrayList<PropertyChangeListener>(getPropertyChangeListeners(""));
        for(PropertyChangeListener l : listenersOfAllProperties) {
            l.propertyChange(e);
        }
    }

    @SuppressWarnings({"PublicMethodNotExposedInInterface"})
    public void firePropertyChanged(final String propertyName, final boolean oldValue, final boolean newValue) {
        firePropertyChanged(propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
    }

    @SuppressWarnings({"PublicMethodNotExposedInInterface"})
    public void firePropertyChanged(final String propertyName, final char oldValue, final char newValue) {
        firePropertyChanged(propertyName, new Character(oldValue), new Character(newValue));
    }

    @SuppressWarnings({"PublicMethodNotExposedInInterface"})
    public void firePropertyChanged(final String propertyName, final byte oldValue, final byte newValue) {
        firePropertyChanged(propertyName, Byte.valueOf(oldValue), Byte.valueOf(newValue));
    }

    @SuppressWarnings({"PublicMethodNotExposedInInterface"})
    public void firePropertyChanged(final String propertyName, final short oldValue, final short newValue) {
        firePropertyChanged(propertyName, Short.valueOf(oldValue), Short.valueOf(newValue));
    }

    @SuppressWarnings({"PublicMethodNotExposedInInterface"})
    public void firePropertyChanged(final String propertyName, final int oldValue, final int newValue) {
        firePropertyChanged(propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
    }

    @SuppressWarnings({"PublicMethodNotExposedInInterface"})
    public void firePropertyChanged(final String propertyName, final long oldValue, final long newValue) {
        firePropertyChanged(propertyName, Long.valueOf(oldValue), Long.valueOf(newValue));
    }

    @SuppressWarnings({"PublicMethodNotExposedInInterface"})
    public void firePropertyChanged(final String propertyName, final float oldValue, final float newValue) {
        firePropertyChanged(propertyName, Float.valueOf(oldValue), Float.valueOf(newValue));
    }

    @SuppressWarnings({"PublicMethodNotExposedInInterface"})
    public void firePropertyChanged(final String propertyName, final double oldValue, final double newValue) {
        firePropertyChanged(propertyName, Double.valueOf(oldValue), Double.valueOf(newValue));
    }
    // ============== End Event Model =================================================================================

    @SuppressWarnings({"MagicCharacter"})
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BoundPropertyHelper");
        sb.append("{listeners=").append(listeners);
        sb.append('}');
        return sb.toString();
    }
}
