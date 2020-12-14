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
 */

/**
 * Parse function returns a JSON object representing ECMAScript code passed.
 * name is optional name for the code source and location param tells whether to
 * include location information for AST nodes or not.
 *
 * Example:
 *
 *    load("nashorn:parser.js");
 *    try {
 *        var json = parse("print('hello')");
 *        print(JSON.stringify(json));
 *    } catch (e) {
 *        print(e);
 *    }
 */
function parse(/*code, [name], [location]*/) {
    var code, name = "<unknown>", location = false;
    switch (arguments.length) {
        case 3:
            location = arguments[2];
        case 2:
            name = arguments[1];
        case 1:
            code = arguments[0];
    }

    var jsonStr = Packages.jdk.nashorn.api.scripting.ScriptUtils.parse(code, name, location);
    return JSON.parse(jsonStr,
        function (prop, value) {
            if (typeof(value) == 'string' && prop == "value") {
                // handle regexps and strings - both are encoded as strings but strings
                // do not start with '/'. If regexp, then eval it to make RegExp object
                return value.startsWith('/')? eval(value) : value.substring(1);
            } else {
                // anything else is returned "as is"
                return value;
            }
        });
}

