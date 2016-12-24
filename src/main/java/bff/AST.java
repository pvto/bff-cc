package bff;

public class AST {
    // contract: don't touch my members!
    public Symbol SYMBOL;
    public AST[] chid;
    public $ fn;  public AST fn$($ fn) { this.fn = fn; return this; }
    public AST(Symbol SYMBOL, AST[] chid)
    {
        this.SYMBOL = SYMBOL;
        this.chid = chid;  }
    public AST appendChid(AST[] chid)
    {
        this.chid = Arf.concat(this.chid, chid);
        return this;  }
}
