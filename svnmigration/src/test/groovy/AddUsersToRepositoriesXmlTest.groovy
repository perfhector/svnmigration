/**
 *
 * @author Cedric Levasseur <celevasseur@fr.imshealth.com>
 */

import groovy.xml.*

class AddUsersToRepositoriesXmlTest extends GroovyTestCase {
    
    public void testHello(){
        println("test")
        assertTrue(true)
    }

    public void testWriteXML(){

        //def reposMap =  ['repo1':['false','user','READ']]
        def reposMap =['repo1':['user1':'WRITE', 'user2':'WRITE', 'user3':'WRITE']]
        
        def xmlContent="<?xml version=\"1.0\" ?><repositorydb><repositories><repository><name>repo1</name><permissions><permission><groupPermission>false</groupPermission></permission><name>moi</name><type>OWNER</type></permissions></repository></repositories></repositorydb>"
        
        AddUsersToRepositoriesXml clazz  = new AddUsersToRepositoriesXml();
        def result = clazz.writeXML(reposMap, xmlContent);
        
        
        println result

                
        
        assertTrue(result.indexOf("moi")>0)
        assertTrue(result.indexOf("user1" )>0)
        assertTrue(result.indexOf("user2" )>0)
        assertTrue(result.indexOf("user3" )>0)

    }
    
    public void testListRepoFromXml(){
        
        def xmlContent="<?xml version=\"1.0\" ?><repositorydb><repositories><repository><name>repo1</name><permissions><permission><groupPermission>false</groupPermission></permission><name>moi</name><type>OWNER</type></permissions></repository></repositories></repositorydb>"
        AddUsersToRepositoriesXml clazz  = new AddUsersToRepositoriesXml();
        clazz.listRepoFromXML(xmlContent);
        assertTrue(true)
    }
}
