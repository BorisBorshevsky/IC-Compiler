package IC;

import java.io.File;

/**
 * Created by user on 3/28/2015.
 */
public class Options {
    public String getLibicPath() {
        return libicPath;
    }

    public String getICFile() {
        return icFile;
    }

    public boolean isPrintAST() {
        return printAST;
    }

    private String libicPath;
    private String icFile;
    private boolean printAST;
    private Options() {
        this.libicPath = "libic.sig";
        this.printAST = false;
        this.icFile = null;
    }
    private static void handleWrongSyntax() {
        System.out.println("Can't run compiler.");
        System.out.println("Usage:\n\tjava IC.Compiler <file.ic> [ -L</path/to/libic.sig> ] [ -print-ast ]");
        System.exit(1);
    }
    public static Options parseCommandLineArgs(String[] args) {
        Options options = new Options();
        if (args.length == 0) {
            handleWrongSyntax();
        }

        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if (arg.startsWith("-L")) {
                options.libicPath = arg.substring(2);
            } else if (arg.equals("-print-ast")) {
                options.printAST = true;
            } else if (!arg.startsWith("-") && options.icFile == null) {
                options.icFile = arg;
            } else {
                System.out.println("Unrecognized flag: " + arg);
                handleWrongSyntax();
            }
        }
        if (options.icFile == null) {
            handleWrongSyntax();
        }
        options.makeSureValid();
        return options;
    }
    private void makeSureValid() {
        File f = new File(libicPath);
        boolean valid = true;
        if (!f.exists()) {
            System.out.println("Can't find library signature file at path: " + libicPath);
            valid = false;
        }
        f = new File(icFile);
        if (!f.exists()) {
            System.out.println("Can't find source file at path: " + icFile);
            valid = false;
        }
        if (!valid) {
            handleWrongSyntax();
        }
    }
}