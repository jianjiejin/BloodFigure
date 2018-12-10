
import com.google.gson.Gson;
import domain.result;
import org.antlr.v4.runtime.ANTLRInputStream;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;



public class mytest {


    public static void main(String[] args) throws IOException {

        String inputFile="/Users/jianjie/Desktop/Demo_Insert/src/test/java/test.txt";
        if(args.length>0) inputFile = args[0];
        InputStream is = System.in;
        if(inputFile !=null)is= new FileInputStream(inputFile);


        ANTLRInputStream input = new ANTLRInputStream(is);
        HplsqlLexer lexer = new HplsqlLexer( input);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        HplsqlParser parser = new HplsqlParser(tokenStream);


        /*
            自定义visitor全部遍历
         */
        ParseTree tree = parser.program();
        myvisitor visitor = new myvisitor();
        visitor.visit(tree);
        result re = visitor.getRe();

        /*
            Json输出
         */

        Gson gson = new Gson();
        System.out.println(gson.toJson(re).toString());


    }
}
