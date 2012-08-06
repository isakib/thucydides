package net.thucydides.core.requirements;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.regex.Pattern;

import static org.apache.commons.collections.IteratorUtils.toList;

public class RequirementsPath {

    private final static Pattern PATH_SEPARATORS = Pattern.compile("[\\\\/.]");
    private final static Pattern FILE_SYSTEM_PATH_SEPARATORS = Pattern.compile("[\\\\/]");

    public static List<String> stripRootFromPath(String root, List<String> storyPathElements) {
        List<String> rootElements = pathElements(root);
        if (storyPathElements.subList(0, rootElements.size()).equals(rootElements)) {
            return storyPathElements.subList(rootElements.size(), storyPathElements.size());
        } else {
            return storyPathElements;
        }
    }

    public static List<String> pathElements(String path) {
        return toList(Splitter.on(PATH_SEPARATORS).omitEmptyStrings().trimResults().split(path).iterator());
    }

    public static List<String> fileSystemPathElements(String path) {
        return toList(Splitter.on(FILE_SYSTEM_PATH_SEPARATORS).omitEmptyStrings().trimResults().split(path).iterator());
    }

}
