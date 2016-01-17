package svnmigration;


import java.text.ParseException


public class ReadPerm {
    
    /* correspondance */
    static Map permissionMatching = [ 'R' : 'READ', 'RW' : 'WRITE' , 'ADM' : 'OWNER']
    
    static String MESSAGE = "The input file '%1' should be with this pattern :\nreponame_RW=user1,user2,user3,\n   "
    
    
    def permFileContent;
    Map permissionMap = [:]
    Map aliasMap = [:]
    String path


    
    public void read(){
        assert(path!="")
        permFileContent = new File(path).text
    }
    
    
    
    public Map readAlias(String content){
        
        def aliasMap=[:]
        
        content.each { line->
            def equalsSign=line.indexOf('=')
            if(equalsSign<=0){
                throw new ParseException("Equals Sign missing.\n " + MESSAGE.replaceAll("%1",path?:''),line.length())
            }
            def left=line.substring(0, equalsSign)
            def right=line.substring(equalsSign+1,line.length())

           
            /* parse right part */

            def userList = right.tokenize(',')

            if(userList.size()<1){
                throw new ParseException("User list missing.\n " + MESSAGE.replaceAll("%1",path?:''),line.length())
            }

            aliasMap[left]=userList

        }
        return aliasMap
    }
    
    
    
    
    /**
     * parse each line, use map composition 
     *
     */
    public void parse(){
        
        aliasMap=readAlias(permFileContent)
        println "aliasMap=$aliasMap"
        permFileContent.each {
            permissionMap + (composeMap(parseLine(it)))
        }
        println "Permissions to insert = "+permissionMap
        
    }
    /*
     * Reads a line fromm a string, ttranformm it into an arrayList
     *
     */  
    public parseLine(def line) throws ParseException {
        def equalsSign=line.indexOf('=')
        if(equalsSign<=0){
            throw new ParseException(line+"Equals Sign missing.\n " + MESSAGE.replaceAll("%1",path?:''),line.length())
        }
        def left=line.substring(0, equalsSign)
        def right=line.substring(equalsSign+1,line.length())
        
        /* parse left part */
        def underscoreSign = left.indexOf('_')
        if(underscoreSign<=0){
            throw new ParseException("Underscore Sign missing.\n " + MESSAGE.replaceAll("%1",path?:''),line.length())
        }

        def repoName = left.substring(0, underscoreSign)
        def permString = left.substring( underscoreSign+1, left.length())
        
        /* parse right part */
        
        def userList = right.tokenize(',')
        
        if(userList.size()<1){
            throw new ParseException("User list missing.\n " + MESSAGE.replaceAll("%1",path?:''),line.length())
        }
        
        def userList2 = substituteAlias(userList,aliasMap)
        
        return [ repoName, permissionMatching[permString], userList2 ]
        
    }
    /**
     * Convert an arrayList key/value and return a map
     *
     */
    public Map composeMap(ArrayList line){
        
        Map result = [:]
        
        def repoName = line[0]
        def permissionMatching = [:]
        line[2].each { user ->
            permissionMatching[user]=line[1]
        }
        result[repoName]=permissionMatching
        
        return result
    }
    
    public List substituteAlias(List userList,Map aliasMap) {
        def result=[]
        userList.each(){ user -> 
            if(user.startsWith("@")){
                result.addAll(aliasMap[user.replaceAll('@','')])
            }else{
                result.add(user)
            }
        }
        
        return result
    }

    /* To get the result after Parsing */
    public Map getPermissionMap(){
        return permissionMap;
    }
    
    public void setPath(String path){
        this.path=path
    }
    public void setPermFileContent(def permFileContent){
        this.permFileContent=permFileContent
    }
    
}
