package svnmigration;

import java.text.ParseException
import svnmigration.*

public class ReadPermTest extends GroovyTestCase {
    
    public void testReadLine(){
        def line="reponame_RW=user1,user2,user3,"
        
        ReadPerm readPerm = new ReadPerm();
        def lineAsMap = readPerm.parseLine(line);
        
        assert (lineAsMap  == ['reponame', 'WRITE', ['user1', 'user2', 'user3']])
        
    }
    public void te_stSubstiteAlias(){
        def line1="reponame_RW=user1,user2,user3,"
        def line2="reponame_R=@reponame_RW"
        
        
        ReadPerm readPerm = new ReadPerm();
        def lineAsMap = readPerm.parseLine(line);
        
        assert (lineAsMap  == ['reponame', 'WRITE', ['user1', 'user2', 'user3']])
        
    }
    
    public void testReadAlias(){
        
        String content="reponame_RW=user1,user2,user3"+System.getProperty("line.separator")+"reponame_R=@reponame_RW,"
        
        
        ReadPerm readPerm = new ReadPerm();
        def aliasMap = readPerm.readAlias(content);
        
        assert (aliasMap['reponame_RW']  ==  ['user1', 'user2', 'user3'])
        
        
    }
    
    public void testReadLineFail1(){
        // : instead of =
        def line="reponame_RW:user1,user2,user3,"
        
        try{ 
            ReadPerm readPerm = new ReadPerm();
            def lineAsMap = readPerm.parseLine(line);
        }catch(ParseException pe){
            assertTrue(pe.getMessage().indexOf("Equal")>-1)
            return
        }
        fail("A ParseException should have been thrown")
        
    }
    
    public void testReadLineFail2(){
        //% instead of _
        def line="reponame%RW=user1,user2,user3,"
        
        try{ 
            ReadPerm readPerm = new ReadPerm();
            def lineAsMap = readPerm.parseLine(line);
        }catch(ParseException pe){
            assertTrue(pe.getMessage().indexOf("Underscore")>-1)
            return
        }
        fail("A ParseException should have been thrown")
        
    }

    public void testReadLineFail3(){
        //; instead of ,
        def line="reponame%RW=user1;user2;user3;"
        
        try{ 
            ReadPerm readPerm = new ReadPerm();
            def lineAsMap = readPerm.parseLine(line);
        }catch(ParseException pe){
            assertTrue(pe.getMessage().indexOf("Underscore")>-1)
            return
        }
        fail("A ParseException should have been thrown")
        
    }

    
    public void testCompose(){
        ReadPerm readPerm = new ReadPerm();
        def result = readPerm.composeMap(['reponame', 'WRITE', ['user1', 'user2', 'user3']]);
        
       
        assertNotNull( result )
        assertEquals (['reponame':['user1':'WRITE', 'user2':'WRITE', 'user3':'WRITE']],result)

    }
}