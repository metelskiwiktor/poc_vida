package pl.vida.code.poc.domain.feed;

import java.util.Arrays;
import java.util.Locale;

public enum Extension {
    XML, CSV, UNKNOWN;

    public static Extension getExtension(String fileName) {
        String[] fileNameParts = fileName.split("\\.");
        String extension = fileNameParts[fileNameParts.length - 1];

        return Arrays.stream(Extension.values())
                .filter(extEnum -> extEnum
                        .toString()
                        .toLowerCase(Locale.ROOT)
                        .equals(extension.toLowerCase(Locale.ROOT)))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
