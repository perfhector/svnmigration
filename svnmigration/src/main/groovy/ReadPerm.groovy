
import java.text.ParseException

public class ReadPerm {
    
    /* correspondance */
    static Map permissionMatching = [ 'R' : 'READ', 'RW' : 'WRITE' , 'ADM' : 'OWNER']
    
    static String MESSAGE = "The input file '$path' should be with this pattern :\nreponame_RW:user1,user2,user3,\n   "
    
    
    String permFileContent;
    Map permissionMap = [:]
    String path
    
    public ReadPerm(String path){
        this.path=path
    }
    
    public void read(){
        permFileContent = new File(path).text
    }
    
    
    /**
     * parse each line, use map composition 
     *
     */
    public void parse(){
        
        permFileContent.each {
            permissionMap + (composeMap(parseLine(it)))
        }
        
    }
    /*
     * Reads a line fromm a string, ttranformm it into an arrayList
     *
     */  
    public parseLine(def line) throws ParseException {
        def equalsSign=line.indexOf('=')
        if(!equalsSign>0){
            throw ParseException("Equals Sign missing.\n " + MESSAGE)
        }
        def left=line.substring(0, equalsSign)
        def right=line.substring(equalsSign+1,line.length())
        
        /* parse left part */
        def underscoreSign = left.indexOf('_')
                if(!equalsSign>0){
            throw ParseException("Underscore Sign missing.\n " + MESSAGE)
        }

        def repoName = left.substring(0, underscoreSign)
        def permString = left.substring( underscoreSign+1, left.length())
        
        /* parse right part */
        
        def userList = right.tokenize(',')
        
        if(userList.size()){
            throw ParseException("User list missing.\n " + MESSAGE)
        }
        
        return [ repoName, permissionMatching[permString], userList ]
        
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

    /* To get the result after Parsing */
    public Map getPermissionMap(){
        return permissionMap;
    }
    
}
