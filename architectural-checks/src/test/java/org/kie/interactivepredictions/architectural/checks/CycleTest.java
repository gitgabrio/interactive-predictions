package org.kie.interactivepredictions.architectural.checks;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CycleTest {

    private JDepend jdepend;

    private final static List<String> MODULES = Arrays.asList("dialogue-engine",
                                                              "dialogue-service",
                                                              "explainability-collaboration",
                                                              "explainability-engine",
                                                              "explainability-service",
                                                              "interactive-predictions-api",
                                                              "prediction-engine",
                                                              "prediction-service",
                                                              "user-interface");

    private final static String TARGET_CLASS_DIR = File.separator + "target" + File.separator + "classes";

    private final static File USER_DIR = new File(System.getProperty("user.dir"));
    private final static String BASE_DIR = USER_DIR.getParentFile().getAbsolutePath() + File.separator;

    @BeforeEach
    void init() throws IOException {
        jdepend = new JDepend();
        jdepend.analyzeInnerClasses(true);
        for (String module : MODULES) {
            jdepend.addDirectory(BASE_DIR + module + TARGET_CLASS_DIR);
        }
    }

    @Test
    void testAllPackages() {
        Collection<JavaPackage> packages = jdepend.analyze();
        assertFalse(jdepend.containsCycles(), "Cycles exist");
        double ideal = 0.0;
        double tolerance = 0.5; // project-dependent
        Iterator<JavaPackage> iter = packages.iterator();
        while (iter.hasNext()) {
            JavaPackage p = iter.next();
            System.out.println(p.getName() + " -> " + p.distance());
//            assertEquals(ideal, p.distance(), tolerance, "Distance exceeded: " + p.getName());
        }
    }
}