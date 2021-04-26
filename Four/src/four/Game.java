package four;

public class Game {
    private final Player[][] table;
    
    private final int n;
    private final int m;
    
    private Boolean full=false;
    
    private Player winner=Player.Neutral;
    private Player thisTurn;
    
    public Game(int n, int m){
        this.table = new Player[m][n];
        this.n=n;
        this.m=m;
        
        this.thisTurn=Player.Piros;
        
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                table[i][j]=Player.Neutral;
            }
        }
    }
    
    public Boolean isFull(){
        return full;
    }
    
    public Player getTurn(){
        return thisTurn;
    }
    
    public Player step(){
        Player turn=thisTurn;
        if(thisTurn==Player.Kék){
            thisTurn=Player.Piros;
        }else{
            thisTurn=Player.Kék;
        }
        return turn;
    }
    
    public Player findWinner(int k){
        Boolean isfull=true;
        int db=0;
        Player lastTurn;
        if(thisTurn==Player.Kék){
            lastTurn=Player.Piros;
        }else{
            lastTurn=Player.Kék;
        }
        for(int i=0; i<n; i++){
            if(table[k][i]==lastTurn){
                db++;
            }else{
                db=0;
            }
            if(db==4){
                return lastTurn;
            }
        }
        db=0;
        int start_col,count;
        for(int line=0; line<=m+n-1; line++){
            start_col = Math.max(0, line-m);
            count=Math.min(line, Math.min((n - start_col),m));
            for(int j=0; j<count; j++){
                if(table[Math.min(m,line)-j-1][start_col+j]==Player.Neutral){
                    isfull=false;
                }
                if(table[Math.min(m,line)-j-1][start_col+j]==lastTurn){
                    db++;
                }else{
                    db=0;
                }
                if(db==4){
                    return lastTurn;
                }
            }
            db=0;
        }
        this.full=isfull;
        for(int line=m+n-1; line>=0; line--){
            start_col = Math.max(0, line-m);
            count=Math.min(line, Math.min((n - start_col),m));
            for(int j=count-1; j>=0; j--){
                if(table[Math.min(m,line)-j-1][n-(start_col+j)-1]==lastTurn){
                    db++;
                }else{
                    db=0;
                }
                if(db==4){
                    return lastTurn;
                }
            }
        db=0;
        }        
        return Player.Neutral;
    }
    
    public int freeIndex(int j){
        for(int i=0; i<m; i++){
            if(table[m-i-1][j]==Player.Neutral){
                return m-i-1;
            }
        }
        return -1;
    }
    
    public void setTable(int i, int j, Player player){
        table[i][j]=player;
    }
    
}
