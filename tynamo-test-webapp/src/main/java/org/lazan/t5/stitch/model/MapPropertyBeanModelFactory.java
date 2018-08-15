package org.lazan.t5.stitch.model;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.services.BeanModelSource;

import java.util.HashMap;
import java.util.Map;

public class MapPropertyBeanModelFactory {

    private static ImmutablePair<String, Class>[] config;

    public MapPropertyBeanModelFactory(ImmutablePair<String, Class>[] configuration){
        config = configuration;
    }

    public Map<String, Object> createRecord(Object... values) {
        final Map<String, Object> record = new HashMap<>();
        int i = 0;
        for(Pair pair : config) {
            record.put((String) pair.getKey(), values[i]);
            i++;
        }
        return record;
    }

    public BeanModel<Object> createMapPropertyConduit(BeanModelSource beanModelSource, Messages messages) {
        // Initially construct a BeanModel for object (no properties)
        final BeanModel<Object> beanModel = beanModelSource.createDisplayModel(Object.class, messages);

        // add MapPropertyConduits for each map entry
        for(Pair pair : config) {
            beanModel.add((String) pair.getKey(),
                    new MapPropertyConduit((String) pair.getKey(), (Class) pair.getValue()));
        }

        return beanModel;
    }

}