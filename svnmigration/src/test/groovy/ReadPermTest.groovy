
public class ReadPermTest extends GroovyTestCase {
    
    public void testReadLine(){
        def line="reponame_RW=user1,user2,user3,"
        
        ReadPerm readPerm = new ReadPerm();
        def lineAsMap = readPerm.parseLine(line);
        
        assert (lineAsMap  == ['reponame', 'WRITE', ['user1', 'user2', 'user3']])
        
    }
    public void testCompose(){
        ReadPerm readPerm = new ReadPerm();
        def result = readPerm.composeMap(['reponame', 'WRITE', ['user1', 'user2', 'user3']]);
        
       
        assertNotNull( result )
        assertEquals (['reponame':['user1':'WRITE', 'user2':'WRITE', 'user3':'WRITE']],result)

    }
}