/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

// Check for fx presence.
if (typeof javafx.application.Application != "function") {
    print("JavaFX is not available.");
    exit(1);
}

// Extend the javafx.application.Application class overriding init, start and stop.
com.sun.javafx.application.LauncherImpl.launchApplication((Java.extend(javafx.application.Application, {
    // Overridden javafx.application.Application.init();
    init: function() {
        // Java FX packages and classes must be defined here because
        // they may not be viable until launch time due to clinit ordering.
    },

    // Overridden javafx.application.Application.start(Stage stage);
    start: function(stage) {
        // Set up stage global.
        $STAGE = stage;

        // Load user FX scripts.
        for each (var script in $SCRIPTS) {
            load(script);
        }

        // Call the global init function if present.
        if ($GLOBAL.init) {
            init();
        }

        // Call the global start function if present.  Otherwise show the stage.
        if ($GLOBAL.start) {
            start(stage);
        } else {
            stage.show();
        }
    },

    // Overridden javafx.application.Application.stop();
    stop: function() {
        // Call the global stop function if present.
        if ($GLOBAL.stop) {
            stop();
        }
    }

    // No arguments passed to application (handled thru $ARG.)
})).class, new (Java.type("java.lang.String[]"))(0));
