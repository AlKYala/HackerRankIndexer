package de.yalama.hackerrankindexer.Security.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * A class to help wrap collections via JSON requests
 * @param <T> The data type of the objects in the collection
 */

@Getter
@Setter
public class CollectionWrapper<T> {
    private Collection<T> collection;
}
