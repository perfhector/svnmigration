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
    public void te_stSubstiteAlias2(){
        def content="""reponame_RW=user1,user2,user3,
reponame_R=@reponame_RW
"""

def f=File.createTempFile("temp",".tmp")
f.write(content)
def f2=new File(f.absolutePath)

        
        ReadPerm readPerm = new ReadPerm();
        readPerm.setPermFileContent(f2.text)
        def lineAsMap = readPerm.parse();
        
        
        println "---------------->"+lineAsMap
        assert (lineAsMap  == ['reponame', 'WRITE', ['user1', 'user2', 'user3']])
        
    }
    
    public void testSustituteAlias(){
       def userList = ["user1","user2","@useralias"]
       def aliasMap = ["useralias" : ["user3", "user4"]]
       ReadPerm readPerm = new ReadPerm();
       def result = readPerm.substituteAlias(userList, aliasMap);
       assert(result== ["user1","user2","user3","user4"])
    }
    
    public void testReadAlias(){
        
        String content="reponame_RW=user1,user2,user3"+System.getProperty("line.separator")+"reponame_R=@reponame_RW,"
        
        
        ReadPerm readPerm = new ReadPerm();
        def aliasMap = readPerm.readAlias(content);
        
        assert (aliasMap['reponame_RW']  ==  ['user1', 'user2', 'user3'])
        println aliasMap
    
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

    
    public void testComposeMap(){
        ReadPerm readPerm = new ReadPerm();
        def result = readPerm.composeMap(['reponame', 'WRITE', ['user1', 'user2', 'user3']]);
        assertNotNull( result )
        assertEquals (['reponame':['user1':'WRITE', 'user2':'WRITE', 'user3':'WRITE']],result)

    }
}