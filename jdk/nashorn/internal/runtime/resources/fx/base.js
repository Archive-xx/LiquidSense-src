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

var JFX_BASE_CLASSES     = [];
var JFX_GRAPHICS_CLASSES = [];
var JFX_CONTROLS_CLASSES = [];
var JFX_FXML_CLASSES     = [];
var JFX_WEB_CLASSES      = [];
var JFX_MEDIA_CLASSES    = [];
var JFX_SWING_CLASSES    = [];
var JFX_SWT_CLASSES      = [];

function LOAD_FX_CLASSES(clsList) {
    for each (var cls in clsList) {
        // Ex. Stage = Java.type("javafx.stage.Stage");
        this[cls[cls.length - 1]] = Java.type(cls.join("."));
    }
}

(function() {
    var System           = Java.type("java.lang.System");
    var ZipFile          = Java.type("java.util.zip.ZipFile");

    var SUFFIX_LENGTH    = ".class".length;

    try {
        var jfxrtJar = new ZipFile(System.getProperty("java.home") + "/lib/ext/jfxrt.jar");
    } catch (ex) {
        throw new Error("JavaFX runtime not found");
    }

    var entries = jfxrtJar.entries();

    while (entries.hasMoreElements()) {
        var entry = entries.nextElement();

        if (entry.isDirectory()) {
            continue;
        }

        var name = entry.name;

        if (!name.endsWith(".class")) {
            continue;
        }

        name = name.substring(0, name.length - SUFFIX_LENGTH);
        cls = name.split("/");

        if (cls[0] != "javafx") {
            continue;
        }

        var last = cls[cls.length - 1];
        var nested = last.lastIndexOf("$");

        // If class name ends with $nnn
        if (nested != -1 && !(last.substring(nested) - 0)) {
            continue;
        }

        switch (cls[1]) {
        case "stage":
            if (cls[2] == "Stage") {
                JFX_BASE_CLASSES.push(cls);
            } else {
                JFX_GRAPHICS_CLASSES.push(cls);
            }
            break;

        case "scene":
            switch (cls[2]) {
            case "Scene":
            case "Group":
                JFX_BASE_CLASSES.push(cls);
                break;

            case "chart":
            case "control":
                JFX_CONTROLS_CLASSES.push(cls);
                break;

            case "web":
                JFX_WEB_CLASSES.push(cls);
                break;

            case "media":
                JFX_MEDIA_CLASSES.push(cls);
                break;

            default:
                JFX_GRAPHICS_CLASSES.push(cls);
                break;
            }
            break;

        case "beans":
        case "collections":
        case "events":
        case "util":
            JFX_BASE_CLASSES.push(cls);
            break;

        case "animation":
        case "application":
        case "concurrent":
        case "css":
        case "geometry":
            JFX_GRAPHICS_CLASSES.push(cls);
            break;

        case "fxml":
            JFX_FXML_CLASSES.push(cls);
            break;

        case "embed":
            if (cls[2] == "swing") {
                JFX_SWING_CLASSES.push(cls);
            } else {
                JFX_SWT_CLASSES.push(cls);
            }
            break;
        }
    }
})();

LOAD_FX_CLASSES(JFX_BASE_CLASSES);
