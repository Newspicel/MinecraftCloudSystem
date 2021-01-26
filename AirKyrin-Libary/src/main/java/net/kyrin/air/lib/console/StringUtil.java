package net.kyrin.air.lib.console;

import net.kyrin.air.lib.AirKyrinLibary;

public class StringUtil {

    public static final String JAVA = "java",
            JAVA_JAR = "-jar",
            EMPTY = "",
            SLASH = "/",
            BACK_SLASH = "\\",
            OS_NAME = System.getProperty("os.name"),
            OS_ARCH = System.getProperty("os.arch"),
            OS_VERSION = System.getProperty("os.version"),
            USER_NAME = System.getProperty("user.name"),
            JAVA_VERSION = System.getProperty("java.version"),
            AIRKYRIN_SPECIFICATION = AirKyrinLibary.class.getPackage().getSpecificationVersion(),
            AIRKYRIN_VERSION = AirKyrinLibary.class.getPackage().getImplementationVersion();

}
