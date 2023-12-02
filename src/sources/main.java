package sources;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import javax.tools.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ServiceLoader;

public class main {
    public static void main(String[] args) throws IOException {
    	//demo.javaX is the file that is read in.
        CharStream s = CharStreams.fromFileName("demo.javaX");
        Java20Lexer lexer = new Java20Lexer(s);
        TokenStream t = new CommonTokenStream(lexer);
        int i = 1;

        StringBuilder convCode = new StringBuilder();

        while (true) {
            if (t.LA(i) == -1) {
                break;
            }
            
            //Here we check for all the different keywords/operators to be replaced. This is later compiled.
            switch (t.LA(i)) {
	            case Java20Lexer.ABSTRACT:
	                convCode.append("abstract ");
	                break;
	            case Java20Lexer.BOOLEAN:
	                convCode.append("boolean ");
	                break;
	            case Java20Lexer.DOUBLE:
	                convCode.append("double ");
	                break;
	            case Java20Lexer.CLASS:
	                convCode.append("class ");
	                break;
	            case Java20Lexer.EXTENDS:
	                convCode.append("extends ");
	                break;
	            case Java20Lexer.IMPORT:
	                convCode.append("import ");
	                break;
	            case Java20Lexer.PRIVATE:
	                convCode.append("private ");
	                break;
	            case Java20Lexer.LONG:
	                convCode.append("long ");
	                break;
	            case Java20Lexer.NEW:
	                convCode.append("new ");
	                break;
	            case Java20Lexer.PUBLIC:
	                convCode.append("public ");
	                break;
	            case Java20Lexer.STATIC:
	                convCode.append("static ");
	                break;
	            case Java20Lexer.RETURN:
	                convCode.append("return ");
	                break;
	            case Java20Lexer.THIS:
	                convCode.append("this ");
	                break;
	            case Java20Lexer.TRY:
	                convCode.append("try ");
	                break;
	            case Java20Lexer.CATCH:
	                convCode.append("catch ");
	                break;
	            case Java20Lexer.VOID:
	                convCode.append("void ");
	                break;
	            case Java20Lexer.ASSIGN:
	                convCode.append("= ");
	                break;
	            case Java20Lexer.EQUAL:
	                convCode.append("== ");
	                break; 
	            case Java20Lexer.SUB:
	                convCode.append("- ");
	                break;
	            case Java20Lexer.ADD:
	                convCode.append("+ ");
	                break;                
	            case Java20Lexer.BANG:
	                convCode.append("! ");
	                break;
	            case Java20Lexer.THROW:
	                convCode.append("throw ");
	                break;
	            case Java20Lexer.NullLiteral:
	                convCode.append("null ");
	                break;          
	            case Java20Lexer.BREAK:
	                convCode.append("break ");
	                break;   	
	            case Java20Lexer.FLOAT:
	                convCode.append("float ");
	                break;   	         
		            default :
		            	 convCode.append(t.LT(i).getText()).append(" ");
            }
           
                
                
            i++;
        }

        File root = Files.createTempDirectory("java").toFile();
        File sourceFile = new File(root, "test" + File.separator + "demo.java");
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), convCode.toString().getBytes(StandardCharsets.UTF_8));

        JavaCompiler compiler = getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());
        
        String name = "demo.java";
        
        runCompiledProgram(Paths.get(sourceFile.getParent()), name);
    }

    //This method compiles our new code.
    private static JavaCompiler getSystemJavaCompiler() {
        ServiceLoader<JavaCompiler> serviceLoader = ServiceLoader.load(JavaCompiler.class);
        return serviceLoader.iterator().next();
    }
    
    //This method runs our new compiled code.
    private static void runCompiledProgram(Path classpath, String className) throws IOException {
        String classpathString = classpath.toAbsolutePath().toString();

        ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", classpathString, className);
        processBuilder.directory(classpath.toFile());
        processBuilder.inheritIO();

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("Program exited with code: " + exitCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    
    
}
    
    
   