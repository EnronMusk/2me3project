package sources;

import java.io.FileOutputStream;
import java.io.IOException;


Map<String, String> mapping = new HashMap<>();
//Language stuff
mapping.put("true", "frfr");
mapping.put("true", "fr");
mapping.put("false", "cap");
mapping.put("return null", "salty");
mapping.put("console", "fam");
mapping.put("log", "");
mapping.put("warn", "");
mapping.put("debug", "");
mapping.put("error", "");
mapping.put("info", "");
mapping.put("Error", "salty");
mapping.put("Error", "salty");
mapping.put("assert", "flex");
mapping.put("require", "");
mapping.put("exports", "");
mapping.put("return", "");
mapping.put("return", "");
mapping.put("this", "squad");
mapping.put("Infinity", "");
mapping.put("break", "dip");
mapping.put("break", "cancel_culture");
mapping.put("break", "slide");
mapping.put("toLowerCase", "");
mapping.put("toUpperCase", "");
mapping.put("confirm", "");
mapping.put("Array", "");
mapping.put("fill", "");
mapping.put("addEventListener", "");
mapping.put("continue", "finna");
//Expression stuff
mapping.put("ThrowStatement", 'yeet')
mapping.put("YieldExpression", 'yeet')

public class ArnoldC {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: ArnoldC [-run|-declaim] [FileToSourceCode]");
            return;
        }

        String filename = getFileNameFromArgs(args);
        String sourceCode;
        try {
            sourceCode = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filename)));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ArnoldGenerator a = new ArnoldGenerator();
        String classFilename = filename.contains(".") ? filename.replaceAll("\\.[^.]*$", "") : filename;
        ArnoldGeneratorResult result = a.generate(sourceCode, classFilename);
        byte[] bytecode = result.getBytecode();
        RootNode root = result.getRoot();

        try (FileOutputStream out = new FileOutputStream(classFilename + ".class")) {
            out.write(bytecode);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        processOption(getCommandFromArgs(args), classFilename, root);
    }

    private static String getFileNameFromArgs(String[] args) {
        switch (args.length) {
            case 1:
                return args[0];
            case 2:
                return args[1];
            default:
                throw new RuntimeException("WHAT THE FUCK DID I DO WRONG!");
        }
    }

    private static String getCommandFromArgs(String[] args) {
        switch (args.length) {
            case 2:
                return args[0];
            case 1:
                return "";
            default:
                throw new RuntimeException("WHAT THE FUCK DID I DO WRONG!");
        }
    }

    private static void processOption(String command, String argFunc, RootNode root) {
        switch (command) {
            case "-run":
                Executor.execute(argFunc);
                break;
            case "-declaim":
                Declaimer.declaim(root, argFunc);
                break;
            default:
        }
    }
}
