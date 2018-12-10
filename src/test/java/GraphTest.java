import domain.GraphViz;
import domain.TableFigureResult;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class GraphTest {

    public static void createDotGraph(String dotFormat,String fileName)
    {
        GraphViz gv=new GraphViz();
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        // String type = "gif";
        String type = "pdf";
        //gv.increaseDpi();
        gv.decreaseDpi();
        gv.decreaseDpi();
        File out = new File(fileName+"."+ type);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
    }


    public static void main(String[] args) throws Exception {

        StringBuilder sb = new StringBuilder();


        String inputFile="/Users/jianjie/Desktop/Demo_Insert/src/test/java/TableFigureTest.txt";
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
        TableFigureVisitor visitor = new TableFigureVisitor();
        visitor.visit(tree);
        TableFigureResult tfr = visitor.getTFR();
        String s = sb.append("\"").append(tfr.getFromTables().toString()).append(" ").append("->").append(" ").append(tfr.getToTable()).append("\"").toString();
        System.out.println(s);
        String dotFormat=s;

        createDotGraph(dotFormat, "DotGraph");
    }
}
