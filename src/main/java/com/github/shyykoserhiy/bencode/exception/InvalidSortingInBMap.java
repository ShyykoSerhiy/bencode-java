package com.github.shyykoserhiy.bencode.exception;

/**
 * @author shyyko
 */
public class InvalidSortingInBMap extends BParsingException {
    public InvalidSortingInBMap() {
        super("Invalid benccoded map. Keys inside map are NOT properly sorted. " +
                "Please, refer to https://wiki.theory.org/BitTorrentSpecification#Bencoding");
    }
}
