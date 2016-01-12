
/**
 *
 * @author Cedric Levasseur <celevasseur@fr.imshealth.com>
 */

import groovy.xml.*
import svnmigration.*

class AddUsersToRepositoriesXmlTest extends GroovyTestCase {
    
    public void testHello(){
        println("test")
        assertTrue(true)
    }

    public void testWriteXML(){

        //def reposMap =  ['repo1':['false','user','READ']]
        def reposMap =['repo1':['user1':'WRITE', 'user2':'WRITE', 'user3':'WRITE']]
        
        def xmlContent="<?xml version=\"1.0\" ?><repository-db><repositories><repository><name>repo1</name><permissions><permission><groupPermission>false</groupPermission></permission><name>moi</name><type>OWNER</type></permissions></repository><repository><name>repo2</name><permissions><permission><groupPermission>false</groupPermission></permission><name>moi</name><type>OWNER</type></permissions></repository></repositories></repository-db>"
        
        AddUsersToRepositoriesXml clazz  = new AddUsersToRepositoriesXml();
        def result = clazz.writeXML(reposMap, xmlContent);

        println result
       
        def imoi=result.indexOf("moi")
        def i1=result.indexOf("user1")
        def i2=result.indexOf("user2")
        def i3=result.indexOf("user3")
        def ir2=result.indexOf("repo2")
        assertTrue(imoi>0)
        assertTrue(i1>0)
        assertTrue(i2>0)
        assertTrue(i3>0)
        assertTrue(ir2>0)
        assertTrue(i1<ir2)
        assertTrue(i2<ir2)
        assertTrue(i3<ir2)
    }
    
    public void testListRepoFromXml(){
        
        def xmlContent="<?xml version=\"1.0\" ?><repositorydb><repositories><repository><name>repo1</name><permissions><permission><groupPermission>false</groupPermission></permission><name>moi</name><type>OWNER</type></permissions></repository></repositories></repositorydb>"
        AddUsersToRepositoriesXml clazz  = new AddUsersToRepositoriesXml();
        clazz.listRepoFromXML(xmlContent);
        assertTrue(true)
    }
}
